package entry.path.output;

import common.datastore.BlockCounter;
import common.datastore.OperationWithKey;
import common.datastore.pieces.LongBlocks;
import common.pattern.BlocksGenerator;
import core.field.Field;
import core.mino.Block;
import core.mino.Mino;
import entry.path.PathEntryPoint;
import entry.path.PathPair;
import entry.path.PathSettings;
import entry.path.ReduceBlocksGenerator;
import exceptions.FinderExecuteException;
import exceptions.FinderInitializeException;
import searcher.pack.SizedBit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UseCSVPathOutput implements PathOutput {
    private static final String FILE_EXTENSION = ".csv";

    private final PathEntryPoint pathEntryPoint;

    private final MyFile outputBaseFile;
    private final ReduceBlocksGenerator generator;
    private Exception lastException = null;

    public UseCSVPathOutput(PathEntryPoint pathEntryPoint, PathSettings pathSettings, BlocksGenerator generator, int maxDepth) throws FinderInitializeException {
        // 出力ファイルが正しく出力できるか確認
        String outputBaseFilePath = pathSettings.getOutputBaseFilePath();
        String namePath = getRemoveExtensionFromPath(outputBaseFilePath);

        // pathが空 または ディレクトリであるとき、pathを追加して、ファイルにする
        if (namePath.isEmpty() || namePath.endsWith(String.valueOf(File.separatorChar)))
            namePath += "path";

        // baseファイル
        String outputFilePath = String.format("%s%s", namePath, FILE_EXTENSION);
        MyFile.mkdirs(outputFilePath);
        MyFile base = new MyFile(outputFilePath);
        base.verify();

        // 保存
        this.pathEntryPoint = pathEntryPoint;
        this.outputBaseFile = base;
        this.generator = new ReduceBlocksGenerator(generator, maxDepth);
    }

    private String getRemoveExtensionFromPath(String path) throws FinderInitializeException {
        int pointIndex = path.lastIndexOf('.');
        int separatorIndex = path.lastIndexOf(File.separatorChar);

        // .がない or セパレータより前にあるとき
        if (pointIndex <= separatorIndex)
            return path;

        // .があるとき
        return path.substring(0, pointIndex);
    }

    @Override
    public void output(List<PathPair> pathPairs, Field field, SizedBit sizedBit) throws FinderExecuteException {
        this.lastException = null;

        outputLog("Found path = " + pathPairs.size());

        Map<BlockCounter, List<PathPair>> groupingByClockCounter = pathPairs.parallelStream()
                .collect(Collectors.groupingBy(pathPair -> {
                    List<OperationWithKey> operations = pathPair.getSampleOperations();
                    return new BlockCounter(operations.stream().map(OperationWithKey::getMino).map(Mino::getBlock));
                }));

        try (BufferedWriter writer = outputBaseFile.newBufferedWriter()) {
            generator.blockCountersParallelStream()
                    .map(blockCounter -> {
                        // 組み合わせ名を取得
                        String blockCounterName = blockCounter.getBlockStream()
                                .map(Block::getName)
                                .collect(Collectors.joining());

                        // パフェ可能な地形を抽出
                        List<PathPair> valid = groupingByClockCounter.get(blockCounter);

                        // パフェ可能な地形数
                        int possibleSize = valid.size();

                        // パフェ可能な地形のテト譜を連結
                        String fumens = valid.stream()
                                .map(PathPair::getFumen)
                                .map(code -> "http://fumen.zui.jp/?v115@" + code)
                                .collect(Collectors.joining(";"));

                        // 対応できるパターンを重複なく抽出
                        Set<LongBlocks> possiblePatternSet = valid.stream()
                                .flatMap(PathPair::blocksStreamForPattern)
                                .collect(Collectors.toSet());

                        // 対応できるパターン数
                        int possiblePatternSize = possiblePatternSet.size();

                        // パターンを連結
                        String patterns = possiblePatternSet.stream()
                                .map(LongBlocks::getBlocks)
                                .map(blocks -> blocks.stream().map(Block::getName).collect(Collectors.joining("")))
                                .collect(Collectors.joining(";"));

                        return String.format("%s,%d,%s,%d,%s%n", blockCounterName, possiblePatternSize, patterns, possibleSize, fumens);
                    })
                    .forEach(line -> {
                        try {
                            writer.write(line);
                        } catch (IOException e) {
                            this.lastException = e;
                        }
                    });
            writer.flush();
        } catch (IOException e) {
            throw new FinderExecuteException("Failed to output file", e);
        }

        if (lastException != null)
            throw new FinderExecuteException("Error to output file", lastException);
    }

    private void outputLog(String str) throws FinderExecuteException {
        pathEntryPoint.output(str);
    }
}
