package chess.pieces;

import chess.Board;
import chess.Color;
import chess.Move;
import chess.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Knight extends Piece{
    public static final char SYMBOL = 'N';

    public Knight(Color color) {
        super(color, SYMBOL);
    }

    @Override
    public List<Move> getLegalMoves(Board board, Square from) {
        final Optional<Piece> pieceOptional = board.getPieceAt(from);
        if (pieceOptional.isEmpty()) {
            return List.of();
        }

        final Piece piece = pieceOptional.get();
        if (piece.getColor() != color) {
            return List.of();
        }

        final List<Move> legalMoves = new ArrayList<>();

        final List<List<Integer>> offsets = List.of(
                List.of(2, 1),
                List.of(2, -1),
                List.of(-2, 1),
                List.of(-2, -1),
                List.of(1, 2),
                List.of(1, -2),
                List.of(-1, 2),
                List.of(-1, -2)
        );

        for (final List<Integer> offset : offsets) {
            final int fileOffset = offset.get(0);
            final int rankOffset = offset.get(1);

            final Optional<Square> destinationOpt = Square.offset(from, fileOffset, rankOffset);
            if (destinationOpt.isEmpty()) {
                continue;
            }

            final Square destination = destinationOpt.get();

            final Optional<Piece> pieceAtDestination = board.getPieceAt(destination);

            if (pieceAtDestination.isPresent() && pieceAtDestination.get().getColor() == color) {
                continue;
            }

            final Board boardCopy = Board.copyOf(board);
            boardCopy.removePieceAt(from);
            boardCopy.setPieceAt(destination, Piece.copyOf(piece));

            if (!board.kingIsUnderAttack(color)) {
                System.out.println(destination);
                legalMoves.add(new Move(color, from.toString() + destination.toString()));
            }
        }

        return legalMoves;
    }
}
