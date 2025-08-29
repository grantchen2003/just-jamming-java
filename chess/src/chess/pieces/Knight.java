package chess.pieces;

import chess.Board;
import chess.Color;
import chess.Move;
import chess.SquareOffset;

import java.util.Set;

public class Knight extends Piece{
    public static final char SYMBOL = 'N';

    private static final Set<SquareOffset> offsets = Set.of(
            new SquareOffset(2, 1),
            new SquareOffset(2, -1),
            new SquareOffset(-2, 1),
            new SquareOffset(-2, -1),
            new SquareOffset(1, 2),
            new SquareOffset(1, -2),
            new SquareOffset(-1, 2),
            new SquareOffset(-1, -2)
    );

    public Knight(Color color) {
        super(color, SYMBOL);
    }

    @Override
    public boolean canMakeMove(Board board, Move move) {
        return canMoveWithOffsets(board, move, offsets);
    }
}
