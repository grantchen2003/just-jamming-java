package chess.pieces;

import chess.Board;
import chess.Color;
import chess.Move;
import chess.Square;

import java.util.List;
import java.util.Optional;

public class Queen extends Piece{
    public static final char SYMBOL = 'Q';

    public Queen(Color color) {
        super(color, SYMBOL);
    }

    @Override
    public boolean canMakeMove(Board board, Move move) {
        final List<Optional<List<Square>>> pathOptions = List.of(
                Square.getDiagonalPath(move.getFrom(), move.getTo()),
                Square.getFilePath(move.getFrom(), move.getTo()),
                Square.getRankPath(move.getFrom(), move.getTo())
        );

        return canMoveWithPaths(board, move, pathOptions);
    }
}
