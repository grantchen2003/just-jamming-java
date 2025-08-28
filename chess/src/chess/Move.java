package chess;

import chess.pieces.Piece;

import java.util.Optional;

public class Move {
    private final Color color;
    private final Square from;
    private final Square to;
    private final Piece promotionPiece;

    Move(Color color, String uciNotation) throws InvalidUCINotationException {
        this.color = color;

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
                promotionPiece = Piece.from(color, promotionPieceSymbol);
            } catch (IllegalArgumentException e) {
                throw new InvalidUCINotationException(e.getMessage());
            }
        } else {
            promotionPiece = null;
        }
    }

    public Color getColor() {
        return color;
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
}