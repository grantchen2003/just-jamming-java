package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record Square(char file, int rank) {
    public Square {
        if (!isValidFile(file)) {
            throw new IllegalArgumentException(String.format("'%c' is not a valid file", file));
        }

        if (!isValidRank(rank)) {
            throw new IllegalArgumentException(String.format("'%d' is not a valid rank", rank));
        }
    }

    public Square(String square) {
        if (square == null) {
            throw new IllegalArgumentException("Square string cannot be null");
        }

        if (square.length() != 2) {
            throw new IllegalArgumentException("Square string must be 2 characters long");
        }

        final char file = square.charAt(0);
        final int rank = Character.digit(square.charAt(1), 10);

        this(file, rank);
    }

    public static Square copyOf(Square other) {
        return new Square(other.file, other.rank);
    }

    public static Optional<List<Square>> getDiagonalPath(Square from, Square to) {
        final SquareOffset squareOffset = SquareOffset.getOffset(from, to);
        return squareOffset.isDiagonalOffset() ? getStraightPath(from, to) : Optional.empty();
    }

    public static Optional<List<Square>> getFilePath(Square from, Square to) {
        final SquareOffset squareOffset = SquareOffset.getOffset(from, to);
        return squareOffset.isOnlyFileOffset() ? getStraightPath(from, to) : Optional.empty();
    }

    public static Optional<List<Square>> getRankPath(Square from, Square to) {
        final SquareOffset squareOffset = SquareOffset.getOffset(from, to);
        return squareOffset.isOnlyRankOffset() ? getStraightPath(from, to) : Optional.empty();
    }

    private static Optional<List<Square>> getStraightPath(Square from, Square to) {
        final SquareOffset squareOffset = SquareOffset.getOffset(from, to);

        if (!squareOffset.isDiagonalOffset() && !squareOffset.isOnlyFileOffset() && !squareOffset.isOnlyRankOffset()) {
            return Optional.empty();
        }

        final int unitFileOffset = Integer.signum(squareOffset.fileOffset());
        final int unitRankOffset = Integer.signum(squareOffset.rankOffset());
        final int pathLength = Math.max(Math.abs(squareOffset.fileOffset()), Math.abs(squareOffset.rankOffset()));

        final List<Square> path = new ArrayList<>();
        for (int i = 0; i <= pathLength; i++) {
            final int fileOffset = i * unitFileOffset;
            final int rankOffset = i * unitRankOffset;

            final Optional<Square> squareOptional = from.offsetBy(new SquareOffset(fileOffset, rankOffset));
            if (squareOptional.isEmpty()) {
                return Optional.empty();
            }

            path.add(squareOptional.get());
        }

        return Optional.of(path);
    }

    public Optional<Square> offsetBy(SquareOffset squareOffset) {
        return offsetBy(squareOffset.fileOffset(), squareOffset.rankOffset());
    }

    public Optional<Square> offsetBy(int fileOffset, int rankOffset) {
        final char newFile = (char) (file+ fileOffset);
        final int newRank = rank + rankOffset;

        return isValidFile(newFile) && isValidRank(newRank)
                ? Optional.of(new Square(newFile, newRank))
                : Optional.empty();
    }

    @Override
    public String toString() {
        return String.format("%c%d", file, rank);
    }

    private static boolean isValidFile(char file) {
        return 'a' <= file && file <= 'h';
    }

    private static boolean isValidRank(int rank) {
        return 1 <= rank && rank <= 8;
    }
}