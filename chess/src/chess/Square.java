package chess;

public class Square {
    private final char file;
    private final int rank;

    public Square(String square) throws IllegalArgumentException {
        if (square.length() != 2) {
            throw new IllegalArgumentException("");
        }

        final char file = square.charAt(0);
        if (!('a' <= file && file <= 'h')) {
            throw new IllegalArgumentException(String.format("'%c' is not a valid file", file));
        }

        final int rank = Character.getNumericValue(square.charAt(1));
        if (!(1 <= rank && rank <= 8)) {
            throw new IllegalArgumentException(String.format("'%d' is not a valid rank", rank));
        }

        this.file = file;
        this.rank = rank;
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
}