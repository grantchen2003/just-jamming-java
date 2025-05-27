import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SieveOfEratosthenes {
    public static void main(String[] args) {
        int n;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Enter an integer: ");
                n = scanner.nextInt();
                break;
            } catch (InputMismatchException _) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Consume invalid input token
            }
        }

        if (n < 2) {
            System.out.println("No primes");
            return;
        }

        boolean[] isPrime = new boolean[n + 1];

        // Arrays.fill(array, fromIndex, toIndex, value) fills the
        // range [fromIndex, toIndex) with the given value.
        Arrays.fill(isPrime, 2, isPrime.length, true);

        for (int i = 0; i < isPrime.length; i++) {
            if (!isPrime[i]) continue;

            for (int j = i * 2; j < isPrime.length; j += i) {
                isPrime[j] = false;
            }
        }

        for (int i = 0; i < isPrime.length; i++) {
            if (isPrime[i]) {
                System.out.println(i);
            }
        }
    }
}
