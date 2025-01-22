import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Task2 {
    public static void main(String[] args) throws Exception {
        var numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        System.out.println(filterNumbers(numbers, n -> n % 2 == 0));
        System.out.println(filterNumbers(numbers, n -> n % 2 != 0));
        System.out.println(filterNumbers(numbers, n -> n % 4 == 0));
        System.out.println(filterNumbers(numbers, n -> isPrime(n)));
        System.out.println(filterNumbers(numbers, n -> n % 3 == 0 && n % 5 == 0));

        // all numbers greater than 5
        System.out.println(filterNumbers(numbers, n -> n > 5));

        // all numbers whose square is less than or equal to 25
        System.out.println(filterNumbers(numbers, n -> n * n <= 25));

        // all even numbers that are greater than 4
        System.out.println(filterNumbers(numbers, n -> n % 2 == 0 && n > 5));
    }

    public static List<Integer> filterNumbers(List<Integer> numbers, Predicate<Integer> condition) {
        var result = new ArrayList<Integer>();

        for (var number : numbers) {
            if (condition.test(number)) {
                result.add(number);
            }
        }

        return result;
    }

    public static boolean isPrime(Integer n) {
        if (n <= 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }
}
