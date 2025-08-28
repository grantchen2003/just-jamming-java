package chess.pieces;

import chess.Board;
import chess.Color;
import chess.Move;
import chess.Square;

import java.util.List;
import java.util.Objects;

public abstract class Piece implements Cloneable{
    protected final Color color;
    private final char symbol;

    protected Piece(Color color, char symbol) {
        this.color = color;
        this.symbol = symbol;
    }

    public static Piece from(Color color, char symbol) {
        return switch (symbol) {
            case Bishop.SYMBOL -> new Bishop(color);
            case King.SYMBOL -> new King(color);
            case Knight.SYMBOL -> new Knight(color);
            case Pawn.SYMBOL -> new Pawn(color);
            case Queen.SYMBOL -> new Queen(color);
            case Rook.SYMBOL -> new Rook(color);
            default -> throw new IllegalArgumentException(String.format("'%c' is an invalid piece symbol", symbol));
        };
    }

    public final Color getColor() {
        return color;
    }

    public final char getSymbol() {
        return symbol;
    }

    public abstract List<Move> getLegalMoves(Board board, Square from);

    @Override
    public Piece clone() {
        try {
            return (Piece) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Clone not supported", e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Piece other = (Piece) obj;
        return color == other.color && symbol == other.symbol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, symbol);
    }
}
