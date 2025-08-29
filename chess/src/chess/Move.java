package chess;

import chess.pieces.Piece;

import java.util.Objects;
import java.util.Optional;

public class Move {
    private final Color playerColor;
    private final Square from;
    private final Square to;
    private final Piece promotionPiece;

    public Move(Color playerColor, String uciNotation) throws InvalidUCINotationException {
        this.playerColor = playerColor;

        if (uciNotation.length() != 4 && uciNotation.length() != 5) {
            throw new InvalidUCINotationException("UCI notation must be length 4 or 5");
        }

        try {
            from = new Square(uciNotation.substring(0, 2));
            to = new Square(uciNotation.substring(2, 4));
        } catch (IllegalArgumentException e) {
            throw new InvalidUCINotationException(e.getMessage());
        }

        if (uciNotation.length() == 5) {
            final char promotionPieceSymbol = uciNotation.charAt(uciNotation.length() - 1);
            try {
                promotionPiece = Piece.from(playerColor, promotionPieceSymbol);
            } catch (IllegalArgumentException e) {
                throw new InvalidUCINotationException(e.getMessage());
            }
        } else {
            promotionPiece = null;
        }
    }

    private Move(Color playerColor, Square from, Square to, Piece promotionPiece) {
        this.playerColor = playerColor;
        this.from = Square.copyOf(from);
        this.to = Square.copyOf(to);
        this.promotionPiece = Piece.copyOf(promotionPiece);
    }

    public static Move copyOf(Move other) {
        return new Move(other.playerColor, other.from, other.to, other.promotionPiece);
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public Square getFrom() {
        return Square.copyOf(from);
    }

    public Square getTo() {
        return Square.copyOf(to);
    }

    public Optional<Piece> getPromotionPiece() {
        return Optional.ofNullable(Piece.copyOf(promotionPiece));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Move other = (Move) obj;
        return playerColor == other.playerColor &&
                from.equals(other.from) &&
                to.equals(other.to) &&
                Objects.equals(promotionPiece, other.promotionPiece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerColor, from, to, promotionPiece);
    }
}