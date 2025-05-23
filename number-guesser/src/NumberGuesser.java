import java.util.Scanner;

public class NumberGuesser {
    public static void main(String[] args) {
        int low = 1, high = 100;
        final int target = (int)(Math.random() * (high - low + 1) + low);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.printf("Guess an integer between %d and %d inclusive: ", low, high);

            int guess;

            String input = scanner.nextLine();
            try {
                guess = Integer.parseInt(input);
            } catch (NumberFormatException _) {
                System.out.printf("%s is not a valid integer\n", input);
                continue;
            }

            if (guess < low || high < guess) {
                System.out.printf("%d is not in the range %d and %d inclusive\n", guess, low, high);
                continue;
            }

            if (guess == target) {
                System.out.println("You win!");
                break;

            } else if (guess < target) {
                low = guess + 1;

            } else {
                high = guess - 1;

            }
        }

        scanner.close();
    }
}
