package chess;

import chess.pieces.Piece;

import java.util.Objects;
import java.util.Optional;

public class Move {
    private final Color playerColor;
    private final Square from;
    private final Square to;
    private final Piece promotionPiece;

    Move(Color playerColor, String uciNotation) throws InvalidUCINotationException {
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

    public Color getPlayerColor() {
        return playerColor;
    }

    public Square getFrom() {
        return from;
    }

    public Square getTo() {
        return to;
    }

    public Optional<Piece> getPromotionPiece() {
        return Optional.ofNullable(promotionPiece);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Move other = (Move) obj;
        return playerColor == other.playerColor &&
                from.equals(other.from) &&
                to.equals(other.to) &&
                promotionPiece.equals(other.promotionPiece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerColor, from, to, promotionPiece);
    }
}