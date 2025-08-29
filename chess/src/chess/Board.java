package chess;

import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Board {
    final private int NUM_RANKS = 8;
    final private int NUM_FILES = 8;

    final private List<Move> moveHistory;
    final private Piece[][] pieces;

    public Board() {
        moveHistory = new ArrayList<>();
        pieces = new Piece[NUM_RANKS][NUM_FILES];

        clear();
        setUpPieces();
    }

    private Board(List<Move> moveHistory,Piece[][] pieces) {
        this.moveHistory = getCopyOf(moveHistory);
        this.pieces = getCopyOf(pieces);
    }

    public static Board copyOf(Board other) {
        return new Board(other.moveHistory, other.pieces);
    }

    // TODO: NEED TO IMPLEMENT
    public boolean checkmateIsPresent() {
        return false;
    }

    public void makeMove(Move move) throws IllegalMoveException {
        final MoveValidationResult moveValidationResult = validateMove(move);
        if (!moveValidationResult.isLegal()) {
            throw new IllegalMoveException(moveValidationResult.getReason());
        }

        final Optional<Piece> pieceOptional = removePieceAt(move.getFrom());
        if (pieceOptional.isEmpty()) {
            throw new IllegalStateException("Expected piece at " + move.getFrom());
        }

        final Optional<Piece> promotionPiece = move.getPromotionPiece();
        if (promotionPiece.isPresent()) {
            setPieceAt(move.getTo(), promotionPiece.get());
        } else {
            setPieceAt(move.getTo(), pieceOptional.get());
        }

        moveHistory.add(Move.copyOf(move));
    }

    public List<Move> getMoveHistory() {
        return getCopyOf(moveHistory);
    }

    private List<Move> getCopyOf(List<Move> moveHistory) {
        return moveHistory.stream().map(Move::copyOf).collect(Collectors.toCollection(ArrayList::new));
    }

    private Piece[][] getCopyOf(Piece[][] pieces) {
        final int numRanks = pieces.length;
        final int numFiles = pieces[0].length;

        Piece[][] copy = new Piece[numRanks][numFiles];

        for (int rankIndex = 0; rankIndex < numRanks; rankIndex++) {
            for (int fileIndex = 0; fileIndex < numFiles; fileIndex++) {
                final Piece piece = pieces[rankIndex][fileIndex];
                copy[rankIndex][fileIndex] = Piece.copyOf(piece);
            }
        }

        return copy;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int rankIndex = NUM_RANKS - 1; rankIndex >= 0; rankIndex--) {
            for (int fileIndex = 0; fileIndex < NUM_FILES; fileIndex++) {
                final Piece piece = pieces[rankIndex][fileIndex];
                final char symbol = piece == null ? '_' : piece.getSymbol();
                output.append(symbol);
                if (fileIndex < NUM_FILES - 1) {
                    output.append(" ");
                }
            }
            output.append("\n");
        }
        return output.toString();
    }

    private void setUpPieces() {
        setPieceAt(new Square("a8"), new Rook(Color.BLACK));
        setPieceAt(new Square("b8"), new Knight(Color.BLACK));
        setPieceAt(new Square("c8"), new Bishop(Color.BLACK));
        setPieceAt(new Square("d8"), new Queen(Color.BLACK));
        setPieceAt(new Square("e8"), new King(Color.BLACK));
        setPieceAt(new Square("f8"), new Bishop(Color.BLACK));
        setPieceAt(new Square("g8"), new Knight(Color.BLACK));
        setPieceAt(new Square("h8"), new Rook(Color.BLACK));

        for (int i = 0; i < NUM_FILES; i++) {
            setPieceAt(new Square((char)('a' + i) + "7"), new Pawn(Color.BLACK));
        }

        for (int i = 0; i < NUM_FILES; i++) {
            setPieceAt(new Square((char)('a' + i) + "2"), new Pawn(Color.WHITE));
        }

        setPieceAt(new Square("a1"), new Rook(Color.WHITE));
        setPieceAt(new Square("b1"), new Knight(Color.WHITE));
        setPieceAt(new Square("c1"), new Bishop(Color.WHITE));
        setPieceAt(new Square("d1"), new Queen(Color.WHITE));
        setPieceAt(new Square("e1"), new King(Color.WHITE));
        setPieceAt(new Square("f1"), new Bishop(Color.WHITE));
        setPieceAt(new Square("g1"), new Knight(Color.WHITE));
        setPieceAt(new Square("h1"), new Rook(Color.WHITE));
    }

    public boolean kingIsUnderAttack(Color kingColor) {
        return false;
    }

    private void clear() {
        for (int rankIndex = 0; rankIndex < NUM_RANKS; rankIndex++) {
            for (int fileIndex = 0; fileIndex < NUM_FILES; fileIndex++) {
                setPieceAt(rankIndex, fileIndex, null);
            }
        }
    }

    public Optional<Piece> removePieceAt(Square square) {
        final Optional<Piece> piece = getPieceAt(square);
        setPieceAt(square, null);
        return piece;
    }

    public Optional<Piece> getPieceAt(Square square) {
        final int rankIndex = getRankIndex(square.getRank());
        final int fileIndex = getFileIndex(square.getFile());
        return Optional.ofNullable(Piece.copyOf(pieces[rankIndex][fileIndex]));
    }

    public void setPieceAt(Square square, Piece piece) {
        final int rankIndex = getRankIndex(square.getRank());
        final int fileIndex = getFileIndex(square.getFile());
        setPieceAt(rankIndex, fileIndex, piece);
    }

    private void setPieceAt(int rankIndex, int fileIndex, Piece piece) {
        pieces[rankIndex][fileIndex] = piece != null ? Piece.copyOf(piece) : null;
    }

    private int getRankIndex(int rank) {
        return rank - 1;
    }

    private int getFileIndex(char file) {
        return (int)(file) - 'a';
    }

    private MoveValidationResult validateMove(Move move) {
        final Square from = move.getFrom();

        final Optional<Piece> pieceOptional = getPieceAt(from);

        if (pieceOptional.isEmpty()) {
            return MoveValidationResult.illegal("No piece is at " + from);
        }

        final Piece piece = pieceOptional.get();

        if (piece.getColor() != move.getPlayerColor()) {
            return MoveValidationResult.illegal("Cannot move piece of different color");
        }

        if (!piece.getLegalMoves(copyOf(this), from).contains(move)) {
            return MoveValidationResult.illegal("Illegal move");
        }

        return MoveValidationResult.legal();
    }
}
