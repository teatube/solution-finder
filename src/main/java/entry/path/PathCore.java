package entry.path;

import common.buildup.BuildUpStream;
import common.datastore.OperationWithKey;
import common.datastore.pieces.Blocks;
import common.datastore.pieces.LongBlocks;
import common.order.OrderLookup;
import common.order.ReverseOrderLookUp;
import common.order.StackOrder;
import common.pattern.BlocksGenerator;
import core.field.Field;
import core.mino.Block;
import core.mino.Mino;
import entry.path.output.FumenParser;
import searcher.pack.SizedBit;
import searcher.pack.task.PackSearcher;
import searcher.pack.task.Result;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PathCore {
    private final List<Result> candidates;
    private final FumenParser fumenParser;
    private final boolean isReduced;
    private final HashSet<LongBlocks> validPieces;
    private final HashSet<LongBlocks> allPieces;
    private final ReverseOrderLookUp reverseOrderLookUp;

    PathCore(List<String> patterns, PackSearcher searcher, int maxDepth, boolean isUsingHold, FumenParser fumenParser) throws ExecutionException, InterruptedException {
        this.candidates = searcher.toList();
        this.fumenParser = fumenParser;
        BlocksGenerator blocksGenerator = new BlocksGenerator(patterns);
        this.isReduced = isReducedPieces(blocksGenerator, maxDepth, isUsingHold);
        this.allPieces = getAllPieces(blocksGenerator, maxDepth, isReduced);
        this.validPieces = getValidPieces(blocksGenerator, allPieces, maxDepth, isReduced);
        this.reverseOrderLookUp = new ReverseOrderLookUp(maxDepth, maxDepth + 1);
    }

    private boolean isReducedPieces(BlocksGenerator blocksGenerator, int maxDepth, boolean isUsingHold) {
        return isUsingHold && maxDepth < blocksGenerator.getDepth();
    }

    private HashSet<LongBlocks> getAllPieces(BlocksGenerator blocksGenerator, int maxDepth, boolean isUsingHold) {
        if (isUsingHold && maxDepth + 1 < blocksGenerator.getDepth()) {
            return toReducedHashSetWithHold(blocksGenerator.blocksStream(), maxDepth + 1);
        } else if (!isUsingHold && maxDepth < blocksGenerator.getDepth()) {
            return toReducedHashSetWithoutHold(blocksGenerator.blocksStream(), maxDepth);
        } else {
            return toDirectHashSet(blocksGenerator.blocksStream());
        }
    }

    private HashSet<LongBlocks> getValidPieces(BlocksGenerator blocksGenerator, HashSet<LongBlocks> allPieces, int maxDepth, boolean isUsingHold) {
        if (isReducedPieces(blocksGenerator, maxDepth, isUsingHold)) {
            return toReducedHashSetWithHold(blocksGenerator.blocksStream(), maxDepth);
        } else {
            return allPieces;
        }
    }

    private HashSet<LongBlocks> toReducedHashSetWithHold(Stream<? extends Blocks> blocksStream, int maxDepth) {
        return blocksStream.parallel()
                .map(Blocks::getBlocks)
                .flatMap(blocks -> OrderLookup.forwardBlocks(blocks, maxDepth).stream())
                .collect(Collectors.toCollection(HashSet::new))
                .parallelStream()
                .map(StackOrder::toList)
                .map(blocks -> blocks.subList(0, maxDepth))
                .map(LongBlocks::new)
                .collect(Collectors.toCollection(HashSet::new));
    }

    private HashSet<LongBlocks> toReducedHashSetWithoutHold(Stream<? extends Blocks> blocksStream, int maxDepth) {
        return blocksStream.parallel()
                .map(Blocks::getBlocks)
                .map(blocks -> blocks.subList(0, maxDepth))
                .map(LongBlocks::new)
                .collect(Collectors.toCollection(HashSet::new));
    }

    private HashSet<LongBlocks> toDirectHashSet(Stream<? extends Blocks> blocksStream) {
        return blocksStream.parallel()
                .map(Blocks::getBlocks)
                .map(LongBlocks::new)
                .collect(Collectors.toCollection(HashSet::new));
    }

    List<PathPair> run(Field field, SizedBit sizedBit) {
        int maxClearLine = sizedBit.getHeight();
        LockedBuildUpListUpThreadLocal threadLocal = new LockedBuildUpListUpThreadLocal(sizedBit.getHeight());
        return candidates.parallelStream()
                .map(result -> {
                    LinkedList<OperationWithKey> operations = result.getMemento().getOperationsStream(sizedBit.getWidth()).collect(Collectors.toCollection(LinkedList::new));

                    // 地形の中で組むことができるoperationsを一つ作成
                    BuildUpStream buildUpStream = threadLocal.get();
                    List<OperationWithKey> sampleOperations = buildUpStream.existsValidBuildPatternDirectly(field, operations)
                            .findFirst()
                            .orElse(Collections.emptyList());

                    // 地形の中で組むことができるものがないときはスキップ
                    if (sampleOperations.isEmpty())
                        return PathPair.EMPTY_PAIR;

                    // 地形の中で組むことができるSetを作成
                    HashSet<LongBlocks> piecesSolution = buildUpStream.existsValidBuildPatternDirectly(field, operations)
                            .map(operationWithKeys -> operationWithKeys.stream()
                                    .map(OperationWithKey::getMino)
                                    .map(Mino::getBlock)
                                    .collect(Collectors.toList())
                            )
                            .map(LongBlocks::new)
                            .collect(Collectors.toCollection(HashSet::new));

                    // 探索シーケンスの中で組むことができるSetを作成
                    HashSet<LongBlocks> piecesPattern = getPiecesPattern(piecesSolution);

                    // 探索シーケンスの中で組むことができるものがないときはスキップ
                    if (piecesPattern.isEmpty())
                        return PathPair.EMPTY_PAIR;

                    // 譜面の作成
                    String fumen = fumenParser.parse(sampleOperations, field, maxClearLine);

                    return new PathPair(result, piecesSolution, piecesPattern, fumen, new ArrayList<>(sampleOperations));
                })
                .filter(pathPair -> pathPair != PathPair.EMPTY_PAIR)
                .collect(Collectors.toList());
    }

    private HashSet<LongBlocks> getPiecesPattern(HashSet<LongBlocks> piecesSolution) {
        if (isReduced) {
            // allとvalidが異なる
            return piecesSolution.stream()
                    .filter(validPieces::contains)
                    .flatMap(blocks -> {
                        return reverseOrderLookUp.parse(blocks.getBlocks())
                                .map(stream -> stream.collect(Collectors.toCollection(ArrayList::new)))
                                .flatMap(blocksWithHold -> {
                                    int nullIndex = blocksWithHold.indexOf(null);
                                    if (nullIndex < 0)
                                        return Stream.of(new LongBlocks(blocksWithHold));

                                    Stream.Builder<LongBlocks> builder = Stream.builder();
                                    for (Block block : Block.values()) {
                                        blocksWithHold.set(nullIndex, block);
                                        builder.accept(new LongBlocks(blocksWithHold));
                                    }
                                    return builder.build();
                                });
                    })
                    .filter(allPieces::contains)
                    .collect(Collectors.toCollection(HashSet::new));
        } else {
            // allとvalidが同じ
            return piecesSolution.stream()
                    .filter(validPieces::contains)
                    .collect(Collectors.toCollection(HashSet::new));
        }
    }
}

