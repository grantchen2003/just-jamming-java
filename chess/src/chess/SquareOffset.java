package chess;

public record SquareOffset(int fileOffset, int rankOffset) {
    public static SquareOffset getOffset(Square from, Square to) {
        int fileOffset = to.file() - from.file();
        int rankOffset = to.rank() - from.rank();
        return new SquareOffset(fileOffset, rankOffset);
    }

    public boolean isDiagonalOffset() {
        return !isZeroOffset() && Math.abs(fileOffset) == Math.abs(rankOffset);
    }

    public boolean isOnlyFileOffset() {
        return fileOffset != 0 && rankOffset == 0;
    }

    public boolean isOnlyRankOffset() {
        return fileOffset == 0 && rankOffset != 0;
    }

    private boolean isZeroOffset() {
        return fileOffset == 0 && rankOffset == 0;
    }
}