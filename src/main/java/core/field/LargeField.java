package core.field;

import core.mino.Mino;
import core.mino.piece.Piece;

// TODO: 実装する
public class LargeField implements Field {
    @Override
    public int getMaxFieldHeight() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBlock(int x, int y) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeBlock(int x, int y) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void put(Mino mino, int x, int y) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void put(Piece piece) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean canPut(Piece piece) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Mino mino, int x, int y) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Piece piece) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getYOnHarddrop(Mino mino, int x, int y) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean canReachOnHarddrop(Mino mino, int x, int y) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean canReachOnHarddrop(Piece piece) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty(int x, int y) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean existsAbove(int y) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isPerfect() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isFilledInColumn(int x, int maxY) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWallBetweenLeft(int x, int maxY) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean canPut(Mino mino, int x, int y) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isOnGround(Mino mino, int x, int y) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getBlockCountBelowOnX(int x, int maxY) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getBlockCountOnY(int y) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getNumOfAllBlocks() {
        throw new UnsupportedOperationException();
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
    public int getBoardCount() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getBoard(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Field freeze(int maxHeight) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void merge(Field field) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void reduce(Field field) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean canMerge(Field field) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getUpperYWith4Blocks() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLowerY() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void slideLeft(int slide) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void slideRight(int slide) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Field child) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int compareTo(Field o) {
        throw new UnsupportedOperationException();
    }
}
