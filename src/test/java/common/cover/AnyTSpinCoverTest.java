package common.cover;

import common.cover.reachable.ReachableForCoverWrapper;
import common.datastore.*;
import common.parser.BlockInterpreter;
import common.parser.OperationTransform;
import core.action.reachable.SRSAnd180Reachable;
import core.action.reachable.SoftdropTOnlyReachable;
import core.field.Field;
import core.field.FieldFactory;
import core.mino.Mino;
import core.mino.MinoFactory;
import core.mino.MinoShifter;
import core.mino.Piece;
import core.srs.MinoRotation;
import core.srs.Rotate;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class AnyTSpinCoverTest {
    private final MinoFactory minoFactory = new MinoFactory();
    private final MinoShifter minoShifter = new MinoShifter();
    private final MinoRotation minoRotation = MinoRotation.create();

    @Test
    void cansBuild1() {
        int height = 4;
        Field field = FieldFactory.createField("" +
                        "__________" +
                        "__________" +
                        "______XXXX" +
                        "___XXXXXXX"
                , height);
        List<Operation> operationList = Arrays.asList(
                new SimpleOperation(Piece.L, Rotate.Right, 0, 1),
                new SimpleOperation(Piece.Z, Rotate.Spawn, 4, 1),
                new SimpleOperation(Piece.T, Rotate.Reverse, 2, 1)
        );
        List<MinoOperationWithKey> operationsWithKey = toMinoOperationWithKey(operationList, field, height);
        ReachableForCoverWrapper reachable = new ReachableForCoverWrapper(new SoftdropTOnlyReachable(minoFactory, minoShifter, minoRotation, height));

        AnyTSpinCover cover = new AnyTSpinCover(false);

        {
            List<Piece> pieces = toPieceList("LT");

            assertThat(
                    cover.canBuild(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isFalse();
            assertThat(
                    cover.canBuildWithHold(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isFalse();
        }

        {
            List<Piece> pieces = toPieceList("LZT");

            assertThat(
                    cover.canBuild(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isTrue();
            assertThat(
                    cover.canBuildWithHold(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isTrue();
        }

        {
            List<Piece> pieces = toPieceList("TLZ");

            assertThat(
                    cover.canBuild(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isFalse();
            assertThat(
                    cover.canBuildWithHold(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isTrue();
        }

        {
            List<Piece> pieces = toPieceList("OLTZ");

            assertThat(
                    cover.canBuild(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isFalse();
            assertThat(
                    cover.canBuildWithHold(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isFalse();
        }

        {
            List<Piece> pieces = toPieceList("LZTOIJS");

            assertThat(
                    cover.canBuild(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isTrue();
            assertThat(
                    cover.canBuildWithHold(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isTrue();
        }
    }

    @Test
    void cansBuild2() {
        int height = 4;
        Field field = FieldFactory.createField("" +
                        "__________" +
                        "__________" +
                        "______XXXX" +
                        "XXX_XXXXXX"
                , height);
        List<Operation> operationList = Arrays.asList(
                new SimpleOperation(Piece.L, Rotate.Reverse, 6, 2),
                new SimpleOperation(Piece.S, Rotate.Spawn, 1, 1),
                new SimpleOperation(Piece.T, Rotate.Reverse, 3, 1)
        );
        List<MinoOperationWithKey> operationsWithKey = toMinoOperationWithKey(operationList, field, height);
        ReachableForCoverWrapper reachable = new ReachableForCoverWrapper(new SoftdropTOnlyReachable(minoFactory, minoShifter, minoRotation, height));

        AnyTSpinCover cover = new AnyTSpinCover(false);

        {
            List<Piece> pieces = toPieceList("LST");

            assertThat(
                    cover.canBuild(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isTrue();
            assertThat(
                    cover.canBuildWithHold(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isTrue();
        }

        {
            List<Piece> pieces = toPieceList("STL");

            assertThat(
                    cover.canBuild(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isTrue();
            assertThat(
                    cover.canBuildWithHold(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isTrue();
        }

        {
            List<Piece> pieces = toPieceList("OSTL");

            assertThat(
                    cover.canBuild(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isFalse();
            assertThat(
                    cover.canBuildWithHold(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isTrue();
        }
    }

    @Test
    void cansBuildMini() {
        int height = 4;
        Field field = FieldFactory.createField("" +
                        "__________" +
                        "__________" +
                        "__________" +
                        "_XXXXXXXXX"
                , height);
        List<Operation> operationList = Arrays.asList(
                new SimpleOperation(Piece.O, Rotate.Spawn, 2, 1),
                new SimpleOperation(Piece.T, Rotate.Right, 0, 1)
        );
        List<MinoOperationWithKey> operationsWithKey = toMinoOperationWithKey(operationList, field, height);
        ReachableForCoverWrapper reachable = new ReachableForCoverWrapper(new SoftdropTOnlyReachable(minoFactory, minoShifter, minoRotation, height));

        AnyTSpinCover cover = new AnyTSpinCover(false);

        {
            List<Piece> pieces = toPieceList("TO");

            assertThat(
                    cover.canBuild(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isTrue();
            assertThat(
                    cover.canBuildWithHold(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isTrue();
        }

        {
            List<Piece> pieces = toPieceList("OT");

            assertThat(
                    cover.canBuild(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isFalse();
            assertThat(
                    cover.canBuildWithHold(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isTrue();
        }
    }

    @Test
    void cansBuildUse180() {
        int height = 5;
        Field field = FieldFactory.createField("" +
                        "__________" +
                        "_______XXX" +
                        "XXX___XXXX" +
                        "XXX____XXX" +
                        "XXX___XXXX"
                , height);
        List<Operation> operationList = Arrays.asList(
                new SimpleOperation(Piece.O, Rotate.Spawn, 3, 0),
                new SimpleOperation(Piece.J, Rotate.Right, 3, 3),
                new SimpleOperation(Piece.T, Rotate.Right, 5, 1)
        );
        List<MinoOperationWithKey> operationsWithKey = toMinoOperationWithKey(operationList, field, height);
        ReachableForCoverWrapper reachable = new ReachableForCoverWrapper(new SRSAnd180Reachable(minoFactory, minoShifter, minoRotation, height));

        AnyTSpinCover cover = new AnyTSpinCover(true);

        {
            List<Piece> pieces = toPieceList("TIJO");

            assertThat(
                    cover.canBuild(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isFalse();
            assertThat(
                    cover.canBuildWithHold(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isFalse();
        }
        {
            List<Piece> pieces = toPieceList("TOJ");

            assertThat(
                    cover.canBuild(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isFalse();
            assertThat(
                    cover.canBuildWithHold(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isTrue();
        }
        {
            List<Piece> pieces = toPieceList("OTJ");

            assertThat(
                    cover.canBuild(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isTrue();
            assertThat(
                    cover.canBuildWithHold(field, operationsWithKey.stream(), pieces, height, reachable, operationsWithKey.size())
            ).isTrue();
        }
    }

    private List<Piece> toPieceList(String str) {
        return BlockInterpreter.parse(str).collect(Collectors.toList());
    }

    private List<MinoOperationWithKey> toMinoOperationWithKey(List<Operation> operationList, Field field, int height) {
        return OperationTransform.parseToOperationWithKeys(
                field, new Operations(operationList), minoFactory, height
        ).stream().map(m -> {
            Mino mino = minoFactory.create(m.getPiece(), m.getRotate());
            return new MinimalOperationWithKey(mino, m.getX(), m.getY(), m.getNeedDeletedKey());
        }).collect(Collectors.toList());
    }
}