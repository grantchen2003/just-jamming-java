package chess;

import java.util.List;

public class Chess {
    public static void main(String[] args) {
        final List<Player> players = List.of(new Player(Color.WHITE), new Player(Color.BLACK));

        final Board board = new Board();

        for (int i = 0;; i++) {
            System.out.println(board);

            final Player currentPlayer = players.get(i % players.size());

            while (true) {
                final Move move;

                try {
                    move = currentPlayer.getMove();
                } catch (InvalidUCINotationException e) {
                    System.out.println("Invalid UCI notation: " + e.getMessage());
                    continue;
                }

                try {
                    board.makeMove(move);
                    break;
                } catch (IllegalMoveException e) {
                    System.out.println("Illegal move: " + e.getMessage());
                }
            }

            if (board.checkmateIsPresent()) {
                System.out.printf("%s won!", currentPlayer.getColor());
                System.out.println(board.getMoveHistory());
                return;
            }
        }
    }
}
