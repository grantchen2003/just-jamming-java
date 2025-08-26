import java.util.Scanner;

public class UserPlayer extends Player{

    UserPlayer() {
        super(getUserName());
    }

    @Override
    Move getMove() {
        while (true) {
            System.out.print("Enter a move (hit/stand): ");
            final Scanner scanner = new Scanner(System.in);
            final String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("hit")) {
                return Move.HIT;
            } else if (userInput.equalsIgnoreCase("stand")) {
                return Move.STAND;
            } else {
                System.out.println("Invalid Input");
            }
        }
    }

    private static String getUserName() {
        System.out.print("Enter a name: ");
        final Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
