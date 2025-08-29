package chess.pieces;

import chess.Board;
import chess.Color;
import chess.Move;
import chess.Square;
import chess.SquareOffset;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pawn extends Piece {
    public static final char SYMBOL = 'P';

    public Pawn(Color color) {
        super(color, SYMBOL);
    }

    // TODO: review this code since AI wrote it
    @Override
    public boolean canMakeMove(Board board, Move move) {
        Set<SquareOffset> offsets = new HashSet<>();

        offsets.addAll(addForwardMoves(board, move));
        offsets.addAll(addCaptureMoves(board, move));
        offsets.addAll(addEnPassantMoves(board, move));

        return canMoveWithOffsets(board, move, offsets);
    }

    private Set<SquareOffset> addForwardMoves(Board board, Move move) {
        Set<SquareOffset> offsets = new HashSet<>();
        int dir = (color == Color.WHITE ? 1 : -1);

        // forward one
        SquareOffset one = new SquareOffset(0, dir);
        move.getFrom().offsetBy(one).ifPresent(sq -> {
            if (board.getPieceAt(sq).isEmpty()) {
                offsets.add(one);

                // forward two (only if pawn hasn’t moved)
                if (!hasMoved(board, move.getFrom())) {
                    SquareOffset two = new SquareOffset(0, 2 * dir);
                    move.getFrom().offsetBy(two).ifPresent(sq2 -> {
                        if (board.getPieceAt(sq2).isEmpty()) {
                            offsets.add(two);
                        }
                    });
                }
            }
        });

        return offsets;
    }

    private Set<SquareOffset> addCaptureMoves(Board board, Move move) {
        Set<SquareOffset> offsets = new HashSet<>();
        int dir = (color == Color.WHITE ? 1 : -1);

        for (int fileOffset : List.of(-1, 1)) {
            SquareOffset captureOffset = new SquareOffset(fileOffset, dir);
            move.getFrom().offsetBy(captureOffset).flatMap(board::getPieceAt).ifPresent(piece -> {
                if (piece.color != color) {
                    offsets.add(captureOffset);
                }
            });
        }

        return offsets;
    }

    private Set<SquareOffset> addEnPassantMoves(Board board, Move move) {
        Set<SquareOffset> offsets = new HashSet<>();

        board.getLastMove().ifPresent(lastMove -> {
            Piece lastPiece = board.getPieceAt(lastMove.getTo()).orElse(null);
            if (!(lastPiece instanceof Pawn) || lastPiece.color == color) return;

            int twoStep = (lastPiece.color == Color.WHITE ? 2 : -2);
            if (Math.abs(lastMove.getTo().rank() - lastMove.getFrom().rank()) != Math.abs(twoStep)) return;

            int dir = (color == Color.WHITE ? 1 : -1);
            for (int fileOffset : List.of(-1, 1)) {
                SquareOffset captureOffset = new SquareOffset(fileOffset, dir);
                move.getFrom().offsetBy(captureOffset).ifPresent(target -> {
                    if (target.file() == lastMove.getTo().file()
                            && target.rank() == lastMove.getTo().rank() - dir) {
                        offsets.add(captureOffset);
                    }
                });
            }
        });

        return offsets;
    }

    private boolean hasMoved(Board board, Square pawnSquare) {
        for (Move pastMove : board.getMoveHistory()) {
            if (!pastMove.getFrom().equals(pawnSquare) && !pastMove.getTo().equals(pawnSquare)) {
                continue;
            }

            Piece moved = board.getPieceAt(pastMove.getTo()).orElse(null);
            if (moved instanceof Pawn && moved.color == color) {
                return true;
            }
        }
        return false;
    }
}
