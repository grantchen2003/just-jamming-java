package chess.pieces;

import chess.Color;

public class Bishop extends Piece{
    public static final char SYMBOL = 'B';

    public Bishop(Color color) {
        super(color, SYMBOL);
    }
}
