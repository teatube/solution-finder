package core.field;

import common.comparator.FieldComparator;
import core.mino.Mino;
import core.neighbor.OriginalPiece;

/**
 * フィールドの高さ height <= 24 であること
 * マルチスレッド非対応
 */
public class LargeField implements Field {
    private static final int FIELD_WIDTH = 10;
    private static final int MAX_FIELD_HEIGHT = 24;
    private static final int FIELD_ROW_MID_LOW_BOARDER_Y = 6;
    private static final int FIELD_ROW_MID_HIGH_BOARDER_Y = 12;
    private static final int FIELD_ROW_HIGH_BOARDER_Y = 18;
    private static final long VALID_BOARD_RANGE = 0xfffffffffffffffL;

    // x,y: 最下位 (0,0), (1,0),  ... , (9,0), (0,1), ... 最上位 // フィールド範囲外は必ず0であること
    private long xBoardLow = 0;
    private long xBoardMidLow = 0;
    private long xBoardMidHigh = 0;
    private long xBoardHigh = 0;

    public LargeField() {
    }

    private LargeField(LargeField src) {
        this.xBoardLow = src.xBoardLow;
        this.xBoardMidLow = src.xBoardMidLow;
        this.xBoardMidHigh = src.xBoardMidHigh;
        this.xBoardHigh = src.xBoardHigh;
    }

    public LargeField(long xBoardLow, long xBoardMidLow, long xBoardMidHigh, long xBoardHigh) {
        this.xBoardLow = xBoardLow;
        this.xBoardMidLow = xBoardMidLow;
        this.xBoardMidHigh = xBoardMidHigh;
        this.xBoardHigh = xBoardHigh;
    }

    long getXBoardLow() {
        return xBoardLow;
    }

    long getXBoardMidLow() {
        return xBoardMidLow;
    }

    long getXBoardMidHigh() {
        return xBoardMidHigh;
    }

    long getXBoardHigh() {
        return xBoardHigh;
    }

    @Override
    public int getMaxFieldHeight() {
        return MAX_FIELD_HEIGHT;
    }

    @Override
    public void setBlock(int x, int y) {
        switch (select(y)) {
            case Low:
                xBoardLow |= getXMask(x, y);
                return;
            case MidLow:
                xBoardMidLow |= getXMask(x, y - FIELD_ROW_MID_LOW_BOARDER_Y);
                return;
            case MidHigh:
                xBoardMidHigh |= getXMask(x, y - FIELD_ROW_MID_HIGH_BOARDER_Y);
                return;
            case High:
                xBoardHigh |= getXMask(x, y - FIELD_ROW_HIGH_BOARDER_Y);
                return;
        }
        throw new IllegalStateException("Unreachable");
    }

    private Position select(int y) {
        if (y < FIELD_ROW_MID_HIGH_BOARDER_Y) {
            if (y < FIELD_ROW_MID_LOW_BOARDER_Y)
                return Position.Low;
            else
                return Position.MidLow;
        } else {
            if (y < FIELD_ROW_HIGH_BOARDER_Y)
                return Position.MidHigh;
            else
                return Position.High;
        }
    }

    private long getXMask(int x, int y) {
        return 1L << x + y * FIELD_WIDTH;
    }

    @Override
    public void removeBlock(int x, int y) {
        switch (select(y)) {
            case Low:
                xBoardLow &= ~getXMask(x, y);
                return;
            case MidLow:
                xBoardMidLow &= ~getXMask(x, y - FIELD_ROW_MID_LOW_BOARDER_Y);
                return;
            case MidHigh:
                xBoardMidHigh &= ~getXMask(x, y - FIELD_ROW_MID_HIGH_BOARDER_Y);
                return;
            case High:
                xBoardHigh &= ~getXMask(x, y - FIELD_ROW_HIGH_BOARDER_Y);
                return;
        }
        throw new IllegalStateException("Unreachable");
    }

    @Override
    public void put(Mino mino, int x, int y) {
        switch (select(y)) {
            case Low: {
                int y2 = y;

                xBoardLow |= mino.getMask(x, y);

                // MidLowの更新が必要
                if (6 <= y2 + mino.getMaxY())
                    xBoardMidLow |= mino.getMask(x, y2 - 6);

                return;
            }
            case MidLow: {
                int y2 = y - FIELD_ROW_MID_LOW_BOARDER_Y;

                xBoardMidLow |= mino.getMask(x, y2);

                // Lowの更新が必要
                if (y2 + mino.getMinY() < 0)
                    xBoardLow |= mino.getMask(x, y2 + 6);

                // MidHighの更新が必要
                if (6 <= y2 + mino.getMaxY())
                    xBoardMidHigh |= mino.getMask(x, y2 - 6);

                return;
            }
            case MidHigh: {
                int y2 = y - FIELD_ROW_MID_HIGH_BOARDER_Y;

                xBoardMidHigh |= mino.getMask(x, y2);

                // MidLowの更新が必要
                if (y2 + mino.getMinY() < 0)
                    xBoardMidLow |= mino.getMask(x, y2 + 6);

                // Highの更新が必要
                if (6 <= y2 + mino.getMaxY())
                    xBoardHigh |= mino.getMask(x, y2 - 6);

                return;
            }
            case High: {
                int y2 = y - FIELD_ROW_HIGH_BOARDER_Y;

                xBoardHigh |= mino.getMask(x, y2);

                // MidHighの更新が必要
                if (y2 + mino.getMinY() < 0)
                    xBoardMidHigh |= mino.getMask(x, y2 + 6);

                return;
            }
        }
        throw new IllegalStateException("Unreachable");
    }

    @Override
    public void put(OriginalPiece piece) {
        merge(piece.getMinoField());
    }

    @Override
    public boolean canPut(Mino mino, int x, int y) {
        switch (select(y)) {
            case Low: {
                int y2 = y;

                if (6 <= y2 + mino.getMaxY()) {
                    // Low & MidLow
                    return (xBoardLow & mino.getMask(x, y2)) == 0L & (xBoardMidLow & mino.getMask(x, y2 - 6)) == 0L;
                }

                // Low
                return (xBoardLow & mino.getMask(x, y2)) == 0L;
            }
            case MidLow: {
                int y2 = y - FIELD_ROW_MID_LOW_BOARDER_Y;

                if (6 <= y2 + mino.getMaxY()) {
                    // MidLow & MidHigh
                    return (xBoardMidLow & mino.getMask(x, y2)) == 0L & (xBoardMidHigh & mino.getMask(x, y2 - 6)) == 0L;
                } else if (y2 + mino.getMinY() < 0) {
                    // MidLow & Low
                    return (xBoardMidLow & mino.getMask(x, y2)) == 0L & (xBoardLow & mino.getMask(x, y2 + 6)) == 0L;
                }

                // MidLow
                return (xBoardMidLow & mino.getMask(x, y2)) == 0L;
            }
            case MidHigh: {
                int y2 = y - FIELD_ROW_MID_HIGH_BOARDER_Y;

                if (6 <= y2 + mino.getMaxY()) {
                    // MidHigh & High
                    return (xBoardMidHigh & mino.getMask(x, y2)) == 0L & (xBoardHigh & mino.getMask(x, y2 - 6)) == 0L;
                } else if (y2 + mino.getMinY() < 0) {
                    // MidHigh & MidLow
                    return (xBoardMidHigh & mino.getMask(x, y2)) == 0L & (xBoardMidLow & mino.getMask(x, y2 + 6)) == 0L;
                }

                // MidHigh
                return (xBoardMidHigh & mino.getMask(x, y2)) == 0L;
            }
            case High: {
                int y2 = y - FIELD_ROW_HIGH_BOARDER_Y;

                if (y2 + mino.getMinY() < 0) {
                    // High & MidHigh
                    return (xBoardHigh & mino.getMask(x, y2)) == 0L & (xBoardMidHigh & mino.getMask(x, y2 + 6)) == 0L;
                }

                // High
                return (xBoardHigh & mino.getMask(x, y2)) == 0L;
            }
        }
        throw new IllegalStateException("Unreachable");
    }

    @Override
    public boolean canPut(OriginalPiece piece) {
        return canMerge(piece.getMinoField());
    }

    @Override
    public void remove(Mino mino, int x, int y) {
        switch (select(y)) {
            case Low: {
                int y2 = y;

                xBoardLow &= ~mino.getMask(x, y);

                // MidLowの更新が必要
                if (6 <= y2 + mino.getMaxY())
                    xBoardMidLow &= ~mino.getMask(x, y2 - 6);

                return;
            }
            case MidLow: {
                int y2 = y - FIELD_ROW_MID_LOW_BOARDER_Y;

                xBoardMidLow &= ~mino.getMask(x, y2);

                // Lowの更新が必要
                if (y2 + mino.getMinY() < 0)
                    xBoardLow &= ~mino.getMask(x, y2 + 6);

                // MidHighの更新が必要
                if (6 <= y2 + mino.getMaxY())
                    xBoardMidHigh &= ~mino.getMask(x, y2 - 6);

                return;
            }
            case MidHigh: {
                int y2 = y - FIELD_ROW_MID_HIGH_BOARDER_Y;

                xBoardMidHigh &= ~mino.getMask(x, y2);

                // MidLowの更新が必要
                if (y2 + mino.getMinY() < 0)
                    xBoardMidLow &= ~mino.getMask(x, y2 + 6);

                // Highの更新が必要
                if (6 <= y2 + mino.getMaxY())
                    xBoardHigh &= ~mino.getMask(x, y2 - 6);

                return;
            }
            case High: {
                int y2 = y - FIELD_ROW_HIGH_BOARDER_Y;

                xBoardHigh &= ~mino.getMask(x, y2);

                // MidHighの更新が必要
                if (y2 + mino.getMinY() < 0)
                    xBoardMidHigh &= ~mino.getMask(x, y2 + 6);

                return;
            }
        }
        throw new IllegalStateException("Unreachable");
    }

    @Override
    public void remove(OriginalPiece piece) {
        reduce(piece.getMinoField());
    }

    @Override
    public int getYOnHarddrop(Mino mino, int x, int startY) {
        int min = -mino.getMinY();
        for (int y = startY - 1; min <= y; y--)
            if (!canPut(mino, x, y))
                return y + 1;
        return min;
    }

    @Override
    public boolean canReachOnHarddrop(Mino mino, int x, int y) {
        int max = MAX_FIELD_HEIGHT - mino.getMinY();
        for (int yIndex = y + 1; yIndex < max; yIndex++)
            if (!canPut(mino, x, yIndex))
                return false;
        return true;
    }

    @Override
    public boolean canReachOnHarddrop(OriginalPiece piece) {
        Field collider = piece.getHarddropCollider();
        return canMerge(collider);
    }

    @Override
    public boolean isEmpty(int x, int y) {
        switch (select(y)) {
            case Low:
                return (xBoardLow & getXMask(x, y)) == 0L;
            case MidLow:
                return (xBoardMidLow & getXMask(x, y - FIELD_ROW_MID_LOW_BOARDER_Y)) == 0L;
            case MidHigh:
                return (xBoardMidHigh & getXMask(x, y - FIELD_ROW_MID_HIGH_BOARDER_Y)) == 0L;
            case High:
                return (xBoardHigh & getXMask(x, y - FIELD_ROW_HIGH_BOARDER_Y)) == 0L;
        }
        throw new IllegalStateException("Unreachable");
    }

    @Override
    public boolean existsAbove(int y) {
        if (MAX_FIELD_HEIGHT <= y) {
            return false;
        }

        switch (select(y)) {
            case Low: {
                // すべて必要
                // High & MidHigh & MidLowのチェック
                if (xBoardHigh != 0L || xBoardMidHigh != 0L || xBoardMidLow != 0L)
                    return true;

                // Lowのチェック
                long mask = VALID_BOARD_RANGE << y * FIELD_WIDTH;
                return (xBoardLow & mask) != 0L;
            }
            case MidLow: {
                // High & MidHighのチェック
                if (xBoardHigh != 0L || xBoardMidHigh != 0L)
                    return true;

                // MidLowのチェック
                long mask = VALID_BOARD_RANGE << (y - FIELD_ROW_MID_LOW_BOARDER_Y) * FIELD_WIDTH;
                return (xBoardMidLow & mask) != 0L;
            }
            case MidHigh: {
                // Highのチェック
                if (xBoardHigh != 0L)
                    return true;

                // MidHighのチェック
                long mask = VALID_BOARD_RANGE << (y - FIELD_ROW_MID_HIGH_BOARDER_Y) * FIELD_WIDTH;
                return (xBoardMidHigh & mask) != 0L;
            }
            case High: {
                // Highで完結
                long mask = VALID_BOARD_RANGE << (y - FIELD_ROW_HIGH_BOARDER_Y) * FIELD_WIDTH;
                return (xBoardHigh & mask) != 0L;
            }
        }
        throw new IllegalStateException("Unreachable");
    }

    @Override
    public boolean isPerfect() {
        return xBoardLow == 0L && xBoardMidLow == 0L && xBoardMidHigh == 0L && xBoardHigh == 0L;
    }

    @Override
    public boolean isFilledInColumn(int x, int maxY) {
        if (maxY == 0) {
            return true;
        }

        switch (select(maxY)) {
            case Low: {
                // Lowで完結
                long mask = BitOperators.getColumnOneLineBelowY(maxY) << x;
                return (~xBoardLow & mask) == 0L;
            }
            case MidLow: {
                // Lowのチェック
                long maskFull = BitOperators.getColumnOneLineBelowY(6) << x;
                if ((~xBoardLow & maskFull) != 0L)
                    return false;

                // MidLowのチェック
                long maskMidLow = BitOperators.getColumnOneLineBelowY(maxY - FIELD_ROW_MID_LOW_BOARDER_Y) << x;
                return (~xBoardMidLow & maskMidLow) == 0L;
            }
            case MidHigh: {
                // Lowのチェック
                long maskFull = BitOperators.getColumnOneLineBelowY(6) << x;
                if ((~xBoardLow & maskFull) != 0L)
                    return false;

                // MidLowのチェック
                if ((~xBoardMidLow & maskFull) != 0L)
                    return false;

                // MidHighのチェック
                long maskMidHigh = BitOperators.getColumnOneLineBelowY(maxY - FIELD_ROW_MID_HIGH_BOARDER_Y) << x;
                return (~xBoardMidHigh & maskMidHigh) == 0L;
            }
            case High: {
                // Lowのチェック
                long maskFull = BitOperators.getColumnOneLineBelowY(6) << x;
                if ((~xBoardLow & maskFull) != 0L)
                    return false;

                // MidLowのチェック
                if ((~xBoardMidLow & maskFull) != 0L)
                    return false;

                // MidHighのチェック
                if ((~xBoardMidHigh & maskFull) != 0L)
                    return false;

                // Highのチェック
                long maskHigh = BitOperators.getColumnOneLineBelowY(maxY - FIELD_ROW_HIGH_BOARDER_Y) << x;
                return (~xBoardHigh & maskHigh) == 0L;
            }
        }
        throw new IllegalStateException("Unreachable");
    }

    @Override
    public boolean isWallBetweenLeft(int x, int maxY) {
        if (maxY == 0) {
            return true;
        }

        switch (select(maxY)) {
            case Low: {
                // Lowで完結
                return BitOperators.isWallBetweenLeft(x, maxY, xBoardLow);
            }
            case MidLow: {
                // Lowのチェック
                if (!BitOperators.isWallBetweenLeft(x, 6, xBoardLow))
                    return false;

                // MidLowのチェック
                return BitOperators.isWallBetweenLeft(x, maxY - FIELD_ROW_MID_LOW_BOARDER_Y, xBoardMidLow);
            }
            case MidHigh: {
                // Lowのチェック
                if (!BitOperators.isWallBetweenLeft(x, 6, xBoardLow))
                    return false;

                // MidLowのチェック
                if (!BitOperators.isWallBetweenLeft(x, 6, xBoardMidLow))
                    return false;

                // MidHighのチェック
                return BitOperators.isWallBetweenLeft(x, maxY - FIELD_ROW_MID_HIGH_BOARDER_Y, xBoardMidHigh);
            }
            case High: {
                // Lowのチェック
                if (!BitOperators.isWallBetweenLeft(x, 6, xBoardLow))
                    return false;

                // MidLowのチェック
                if (!BitOperators.isWallBetweenLeft(x, 6, xBoardMidLow))
                    return false;

                // MidHighのチェック
                if (!BitOperators.isWallBetweenLeft(x, 6, xBoardMidHigh))
                    return false;

                // Highのチェック
                return BitOperators.isWallBetweenLeft(x, maxY - FIELD_ROW_HIGH_BOARDER_Y, xBoardHigh);
            }
        }
        throw new IllegalStateException("Unreachable");
    }

    @Override
    public boolean isOnGround(Mino mino, int x, int y) {
        return y <= -mino.getMinY() || !canPut(mino, x, y - 1);
    }

    @Override
    public int getBlockCountBelowOnX(int x, int maxY) {
        switch (select(maxY)) {
            case Low: {
                // Low
                long mask = BitOperators.getColumnOneLineBelowY(maxY) << x;
                return Long.bitCount(xBoardLow & mask);
            }
            case MidLow: {
                // Low + MidLow
                long fullMask = BitOperators.getColumnOneLineBelowY(6) << x;
                long mask = BitOperators.getColumnOneLineBelowY(maxY - FIELD_ROW_MID_LOW_BOARDER_Y) << x;
                return Long.bitCount(xBoardLow & fullMask)
                        + Long.bitCount(xBoardMidLow & mask);
            }
            case MidHigh: {
                // Low + MidLow + MidHigh
                long fullMask = BitOperators.getColumnOneLineBelowY(6) << x;
                long mask = BitOperators.getColumnOneLineBelowY(maxY - FIELD_ROW_MID_HIGH_BOARDER_Y) << x;
                return Long.bitCount(xBoardLow & fullMask)
                        + Long.bitCount(xBoardMidLow & fullMask)
                        + Long.bitCount(xBoardMidHigh & mask);
            }
            case High: {
                // Low + MidLow + MidHigh + High
                long fullMask = BitOperators.getColumnOneLineBelowY(6) << x;
                long mask = BitOperators.getColumnOneLineBelowY(maxY - FIELD_ROW_HIGH_BOARDER_Y) << x;
                return Long.bitCount(xBoardLow & fullMask)
                        + Long.bitCount(xBoardMidLow & fullMask)
                        + Long.bitCount(xBoardMidHigh & fullMask)
                        + Long.bitCount(xBoardHigh & mask);
            }
        }
        throw new IllegalStateException("Unreachable");
    }

    @Override
    public int getBlockCountOnY(int y) {
        switch (select(y)) {
            case Low: {
                int y2 = y;
                long mask = 0x3ffL << y2 * FIELD_WIDTH;
                return Long.bitCount(xBoardLow & mask);
            }
            case MidLow: {
                int y2 = y - FIELD_ROW_MID_LOW_BOARDER_Y;
                long mask = 0x3ffL << y2 * FIELD_WIDTH;
                return Long.bitCount(xBoardMidLow & mask);
            }
            case MidHigh: {
                int y2 = y - FIELD_ROW_MID_HIGH_BOARDER_Y;
                long mask = 0x3ffL << y2 * FIELD_WIDTH;
                return Long.bitCount(xBoardMidHigh & mask);
            }
            case High: {
                int y2 = y - FIELD_ROW_HIGH_BOARDER_Y;
                long mask = 0x3ffL << y2 * FIELD_WIDTH;
                return Long.bitCount(xBoardHigh & mask);
            }
        }
        throw new IllegalStateException("Unreachable");
    }

    @Override
    public int getNumOfAllBlocks() {
        return Long.bitCount(xBoardLow)
                + Long.bitCount(xBoardMidLow)
                + Long.bitCount(xBoardMidHigh)
                + Long.bitCount(xBoardHigh);
    }

    @Override
    public int clearLine() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long clearLineReturnKey() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insertBlackLineWithKey(long deleteKey) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insertWhiteLineWithKey(long deleteKey) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void fillLine(int y) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getBoardCount() {
        return 4;
    }

    @Override
    public long getBoard(int index) {
        switch (index) {
            case 0:
                return xBoardLow;
            case 1:
                return xBoardMidLow;
            case 2:
                return xBoardMidHigh;
            case 3:
                return xBoardHigh;
            default:
                return 0L;
        }
    }

    @Override
    public Field freeze(int maxHeight) {
        assert 0 < maxHeight && maxHeight <= 24;
        if (maxHeight <= 6)
            return new SmallField(xBoardLow);
        else if (maxHeight <= 12)
            return new MiddleField(xBoardLow, xBoardMidLow);
        return new LargeField(this);
    }

    @Override
    public void merge(Field other) {
        int otherBoardCount = other.getBoardCount();
        assert 0 < otherBoardCount && otherBoardCount <= 4 : otherBoardCount;

        switch (otherBoardCount) {
            case 1: {
                xBoardLow |= other.getBoard(0);
                break;
            }
            case 2: {
                xBoardLow |= other.getBoard(0);
                xBoardMidLow |= other.getBoard(1);
                break;
            }
            default: {
                xBoardLow |= other.getBoard(0);
                xBoardMidLow |= other.getBoard(1);
                xBoardMidHigh |= other.getBoard(2);
                xBoardHigh |= other.getBoard(3);
                break;
            }
        }
    }

    @Override
    public void reduce(Field other) {
        int otherBoardCount = other.getBoardCount();
        assert 0 < otherBoardCount && otherBoardCount <= 4 : otherBoardCount;

        switch (otherBoardCount) {
            case 1: {
                xBoardLow &= ~other.getBoard(0);
                break;
            }
            case 2: {
                xBoardLow &= ~other.getBoard(0);
                xBoardMidLow &= ~other.getBoard(1);
                break;
            }
            default: {
                xBoardLow &= ~other.getBoard(0);
                xBoardMidLow &= ~other.getBoard(1);
                xBoardMidHigh &= ~other.getBoard(2);
                xBoardHigh &= ~other.getBoard(3);
                break;
            }
        }
    }

    @Override
    public boolean canMerge(Field other) {
        int otherBoardCount = other.getBoardCount();
        assert 0 < otherBoardCount && otherBoardCount <= 4 : otherBoardCount;

        switch (otherBoardCount) {
            case 1: {
                return (xBoardLow & other.getBoard(0)) == 0L;
            }
            case 2: {
                return (xBoardLow & other.getBoard(0)) == 0L
                        && (xBoardMidLow & other.getBoard(1)) == 0L;
            }
            default: {
                return (xBoardLow & other.getBoard(0)) == 0L
                        && (xBoardMidLow & other.getBoard(1)) == 0L
                        && (xBoardMidHigh & other.getBoard(2)) == 0L
                        && (xBoardHigh & other.getBoard(3)) == 0L;
            }
        }
    }

    @Override
    public int getUpperYWith4Blocks() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLowerY() {
        throw new UnsupportedOperationException();
    }

    // TODO: write unittest
    @Override
    public void slideLeft(int slide) {
        assert 0 <= slide;
        long mask = BitOperators.getColumnMaskRightX(slide);
        xBoardLow = (xBoardLow & mask) >> slide;
        xBoardMidLow = (xBoardMidLow & mask) >> slide;
        xBoardMidHigh = (xBoardMidHigh & mask) >> slide;
        xBoardHigh = (xBoardHigh & mask) >> slide;
    }

    // TODO: write unittest
    @Override
    public void slideRight(int slide) {
        assert 0 <= slide;
        long mask = BitOperators.getColumnMaskLeftX(FIELD_WIDTH - slide);
        xBoardLow = (xBoardLow & mask) << slide;
        xBoardMidLow = (xBoardMidLow & mask) << slide;
        xBoardMidHigh = (xBoardMidHigh & mask) << slide;
        xBoardHigh = (xBoardHigh & mask) << slide;
    }

    @Override
    public void slideDown() {
        throw new UnsupportedOperationException();
    }

    // TODO: write unittest
    @Override
    public boolean contains(Field child) {
        assert child.getBoardCount() <= 4;
        long childBoardLow = child.getBoard(0);
        long childBoardMidLow = child.getBoard(1);
        long childBoardMidHigh = child.getBoard(2);
        long childBoardHigh = child.getBoard(3);
        return (xBoardLow & childBoardLow) == childBoardLow
                && (xBoardMidLow & childBoardMidLow) == childBoardMidLow
                && (xBoardMidHigh & childBoardMidHigh) == childBoardMidHigh
                && (xBoardHigh & childBoardHigh) == childBoardHigh;
    }

    @Override
    public void inverse() {
        xBoardLow = (~xBoardLow) & VALID_BOARD_RANGE;
        xBoardMidLow = (~xBoardMidLow) & VALID_BOARD_RANGE;
        xBoardMidHigh = (~xBoardMidHigh) & VALID_BOARD_RANGE;
        xBoardHigh = (~xBoardHigh) & VALID_BOARD_RANGE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if (o instanceof LargeField) {
            LargeField that = (LargeField) o;
            return xBoardLow == that.xBoardLow
                    && xBoardMidLow == that.xBoardMidLow
                    && xBoardMidHigh == that.xBoardMidHigh
                    && xBoardHigh == that.xBoardHigh;
        }

        if (o instanceof SmallField) {
            SmallField that = (SmallField) o;
            return xBoardLow == that.getXBoard()
                    && xBoardMidLow == 0L
                    && xBoardMidHigh == 0L
                    && xBoardHigh == 0L;
        } else if (o instanceof MiddleField) {
            MiddleField that = (MiddleField) o;
            return xBoardLow == that.getXBoardLow()
                    && xBoardMidLow == that.getXBoardHigh()
                    && xBoardMidHigh == 0L
                    && xBoardHigh == 0L;
        } else if (o instanceof Field) {
            Field that = (Field) o;
            return FieldComparator.compareField(this, that) == 0;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int result = (int) (xBoardLow ^ (xBoardLow >>> 32));
        result = 31 * result + (int) (xBoardMidLow ^ (xBoardMidLow >>> 32));
        result = 31 * result + (int) (xBoardMidHigh ^ (xBoardMidHigh >>> 32));
        result = 31 * result + (int) (xBoardHigh ^ (xBoardHigh >>> 32));
        return result;
    }

    @Override
    public int compareTo(Field o) {
        return FieldComparator.compareField(this, o);
    }

    @Override
    public String toString() {
        return String.format("LargeField{low=%d, midlow=%d, midhigh=%d, high=%d}", xBoardLow, xBoardMidLow, xBoardMidHigh, xBoardHigh);
    }
}

enum Position {
    Low,
    MidLow,
    MidHigh,
    High,
}
