package chess.pieces;

import chess.Board;
import chess.Color;
import chess.Move;
import chess.SquareOffset;

import java.util.Set;

public class King extends Piece {
    public static final char SYMBOL = 'K';

    private static final Set<SquareOffset> offsets = Set.of(
            new SquareOffset(-1, 1),
            new SquareOffset(-1, 0),
            new SquareOffset(-1, -1),
            new SquareOffset(0, 1),
            new SquareOffset(0, -1),
            new SquareOffset(1, 1),
            new SquareOffset(1, 0),
            new SquareOffset(1, -1)
    );

    public King(Color color) {
        super(color, SYMBOL);
    }

    @Override
    public boolean canMakeMove(Board board, Move move) {
        return canMoveWithOffsets(board, move, offsets);
    }
}
