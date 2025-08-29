package chess;

import java.util.Objects;
import java.util.Optional;

public class Square {
    private final char file;
    private final int rank;

    public Square(String square) {
        if (square.length() != 2) {
            throw new IllegalArgumentException("");
        }

        final char file = square.charAt(0);
        if (!isValidFile(file)) {
            throw new IllegalArgumentException(String.format("'%c' is not a valid file", file));
        }

        final int rank = Character.getNumericValue(square.charAt(1));
        if (!isValidRank(rank)) {
            throw new IllegalArgumentException(String.format("'%d' is not a valid rank", rank));
        }

        this.file = file;
        this.rank = rank;
    }

    public static Square copyOf(Square other) {
        return new Square(other.toString());
    }

    public static Optional<Square> offset(Square square, int fileOffset, int rankOffset) {
        final char newFile = (char) (square.getFile() + fileOffset);
        final int newRank = square.getRank() + rankOffset;
        return isValidFile(newFile) && isValidRank(newRank)
                ? Optional.of(new Square(String.format("%c%d", newFile, newRank)))
                : Optional.empty();
    }

    public char getFile() {
        return file;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return String.format("%c%d", file, rank);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // same reference
        if (obj == null || getClass() != obj.getClass()) return false;
        Square other = (Square) obj;
        return file == other.file && rank == other.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, rank);
    }

    private static boolean isValidFile(char file) {
        return 'a' <= file && file <= 'h';
    }

    private static boolean isValidRank(int rank) {
        return 1 <= rank && rank <= 8;
    }
}