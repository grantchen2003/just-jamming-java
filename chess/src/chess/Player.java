package chess;

import java.util.Scanner;

public class Player {
    final private Color color;

    Player(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Move getMove() {
        System.out.printf("%s, enter a move notation: ", color);
        final Scanner scanner = new Scanner(System.in);
        final String moveNotation = scanner.nextLine();
        return new Move(color, moveNotation);
    }
}
