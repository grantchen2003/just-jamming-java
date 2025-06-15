import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Hangman {
    private static final String SECRET_WORD = "beethoven";
    private static final int MAX_ATTEMPTS = (int)SECRET_WORD.chars().distinct().count() + 2;

    private static int attempts = 0;
    private static final Set<Character> guessedLetters = new HashSet<>();
    private static String revealed = "_".repeat(SECRET_WORD.length());

    public static void main(String[] args) {
        while (attempts <= MAX_ATTEMPTS) {
            final char guess = getUserGuess();

            if (guessedLetters.contains(guess)) {
                notifyUserOfPreviouslyGuessedLetter(guess);
                continue;
            }

            attempts++;
            guessedLetters.add(guess);

            updateRevealed(guess);

            if (revealed.equals(SECRET_WORD)) {
                notifyUserOfWin();
                return;
            }

            notifyUserOfStatus();
        }

        notifyUserOfLoss();
    }

    private static char getUserGuess() {
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Please enter a single letter: ");
            final String input = scanner.nextLine().toLowerCase();

            if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
                return input.charAt(0);
            }

            System.out.println("Invalid input. Please enter exactly one letter.");
        }
    }

    private static void notifyUserOfPreviouslyGuessedLetter(char c) {
        System.out.printf("You are guessed the letter %c\n", c);
    }

    private static void notifyUserOfStatus() {
        System.out.printf("Guess %d of %d: %s\n", attempts, MAX_ATTEMPTS, revealed);
    }

    private static void notifyUserOfWin() {
        System.out.println("You won");
    }

    private static void notifyUserOfLoss() {
        System.out.printf("You lost, the secret word word was %s\n", SECRET_WORD);
    }

    private static void updateRevealed(char guess) {
        for (int i = 0; i < SECRET_WORD.length(); i++) {
            if (SECRET_WORD.charAt(i) == guess) {
                revealed = replaceCharAt(revealed, i, guess);
            }
        }
    }

    private static String replaceCharAt(String s, int index, char newChar) {
        return s.substring(0, index) + newChar + s.substring(index + 1);
    }
}