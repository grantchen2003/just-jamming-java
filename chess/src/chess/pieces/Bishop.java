package chess.pieces;

import chess.Board;
import chess.Color;
import chess.Move;
import chess.Square;

import java.util.List;
import java.util.Optional;

public class Bishop extends Piece{
    public static final char SYMBOL = 'B';

    public Bishop(Color color) {
        super(color, SYMBOL);
    }

    @Override
    public boolean canMakeMove(Board board, Move move) {
        final List<Optional<List<Square>>> pathOptions = List.of(
                Square.getDiagonalPath(move.getFrom(), move.getTo())
        );

        return canMoveWithPaths(board, move, pathOptions);
    }
}
