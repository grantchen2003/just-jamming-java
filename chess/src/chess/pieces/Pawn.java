package chess.pieces;

import chess.Color;

public class Pawn extends Piece {
    public static final char SYMBOL = 'P';

    public Pawn(Color color) {
        super(color, SYMBOL);
    }
}
