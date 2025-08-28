package chess.pieces;

import chess.Color;

public abstract class Piece implements Cloneable{
    private final Color color;
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

    @Override
    public Piece clone() {
        try {
            return (Piece) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Clone not supported", e);
        }
    }
}
