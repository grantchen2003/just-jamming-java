package chess.pieces;

import chess.Board;
import chess.Color;
import chess.Move;
import chess.Square;

import java.util.List;

public class Queen extends Piece{
public static final char SYMBOL = 'Q';

    public Queen(Color color) {
        super(color, SYMBOL);
    }

    @Override
    public List<Move> getLegalMoves(Board board, Square from) {
        return List.of();
    }
}
