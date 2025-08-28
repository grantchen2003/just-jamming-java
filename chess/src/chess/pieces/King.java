package chess.pieces;

import chess.Color;

public class King extends Piece{
    public static final char SYMBOL = 'K';

    public King(Color color) {
        super(color, SYMBOL);
    }
}
