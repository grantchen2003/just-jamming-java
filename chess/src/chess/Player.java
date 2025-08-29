package chess;

import java.util.Scanner;

public record Player(Color color) {
    private static final Scanner scanner = new Scanner(System.in);

    public Move getMove() {
        System.out.printf("%s, enter a move notation: ", color);
        final String moveUciNotation = scanner.nextLine();
        return new Move(color, moveUciNotation);
    }
}