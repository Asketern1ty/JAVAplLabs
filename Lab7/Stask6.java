import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Scanner;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class Stask6 {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter a natural number n: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Error: please enter an integer.");
                return;
            }

            int n = scanner.nextInt();
            if (n < 2) {
                System.out.println("There are no prime numbers less than or equal to " + n + ".");
                return;
            }

            IntPredicate isPrime = num -> num >= 2 && IntStream.rangeClosed(2, (int) Math.sqrt(num))
                    .noneMatch(divisor -> num % divisor == 0);

            int[] primes = IntStream.rangeClosed(2, n)
                    .filter(isPrime)
                    .toArray();

            System.out.println("\nAll prime numbers found and their binary representation:");
            Arrays.stream(primes).forEach(p ->
                    System.out.printf("%d -> %s (ones: %d)%n",
                            p, Integer.toBinaryString(p), Integer.bitCount(p))
            );

            OptionalInt maxOnesPrime = Arrays.stream(primes)
                    .reduce((a, b) -> Integer.bitCount(b) > Integer.bitCount(a) ? b : a);

            maxOnesPrime.ifPresent(prime -> {
                System.out.println("\nResult:");
                System.out.println("Prime number with the maximum number of ones: " + prime);
                System.out.println("Binary form: " + Integer.toBinaryString(prime)
                        + " (ones: " + Integer.bitCount(prime) + ")");
            });
        }
    }
}