package chess.pieces;

import chess.Board;
import chess.Color;
import chess.Move;
import chess.Square;

import java.util.List;

public class Bishop extends Piece{
    public static final char SYMBOL = 'B';

    public Bishop(Color color) {
        super(color, SYMBOL);
    }

    @Override
    public List<Move> getLegalMoves(Board board, Square from) {
        return List.of();
    }
}
