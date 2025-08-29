package chess.pieces;

import chess.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public abstract class Piece {
    protected final Color color;
    private final char symbol;

    protected Piece(Color color, char symbol) {
        this.color = color;
        this.symbol = symbol;
    }

    public static Piece copyOf(Piece other) {
        return other != null ? Piece.from(other.color, other.symbol) : null;
    }

    public static Piece from(Color color, char symbol) {
        return switch (symbol) {
            case Bishop.SYMBOL -> new Bishop(color);
            case King.SYMBOL -> new King(color);
            case Knight.SYMBOL -> new Knight(color);
            case Pawn.SYMBOL -> new Pawn(color);
            case Queen.SYMBOL -> new Queen(color);
            case Rook.SYMBOL -> new Rook(color);
            default -> throw new IllegalArgumentException(String.format("'%c' is an invalid piece symbol", symbol));
        };
    }

    public final Color getColor() {
        return color;
    }

    public final char getSymbol() {
        return symbol;
    }

    public abstract boolean canMakeMove(Board board, Move move);

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Piece other = (Piece) obj;
        return color == other.color && symbol == other.symbol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, symbol);
    }

    protected boolean canMoveWithOffsets(Board board, Move move, Set<SquareOffset> squareOffsets) {
        final SquareOffset squareOffset = SquareOffset.getOffset(move.getFrom(), move.getTo());
        if (!squareOffsets.contains(squareOffset)) {
            return false;
        }

        return isLegalAfterMove(board, move);
    }

    protected boolean canMoveWithPaths(Board board, Move move, List<Optional<List<Square>>> pathOptions) {
        final long presentPathCount = pathOptions.stream().filter(Optional::isPresent).count();
        if (presentPathCount != 1) {
            return false;
        }

        final List<Square> path = pathOptions.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No path present"));

        final List<Square> pathExcludingEndpoints = path.stream()
                .filter(s -> !s.equals(move.getFrom()) && !s.equals(move.getTo()))
                .toList();

        if (!board.squaresAreEmpty(pathExcludingEndpoints)) {
            return false;
        }

        return isLegalAfterMove(board, move);
    }

    private boolean isLegalAfterMove(Board board, Move move) {
        final Optional<Piece> pieceAtDestination = board.getPieceAt(move.getTo());
        if (pieceAtDestination.isPresent() && pieceAtDestination.get().getColor() == color) {
            return false;
        }

        final Optional<Piece> pieceToMoveOptional = board.removePieceAt(move.getFrom());
        if (pieceToMoveOptional.isEmpty()) {
            return false;
        }

        final Piece pieceToMove = pieceToMoveOptional.get();
        if (pieceToMove.color != color) {
            return false;
        }

        board.setPieceAt(move.getTo(), Piece.copyOf(pieceToMove));

        return !board.kingIsUnderAttack(color);
    }
}
