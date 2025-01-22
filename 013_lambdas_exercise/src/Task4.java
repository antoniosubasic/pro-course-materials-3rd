import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Task4 {
    public static void main(String[] args) throws Exception {
        var numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        System.out.println(processNumbers(numbers, n -> true, n -> n));
        System.out.println(processNumbers(numbers, n -> n % 2 != 0, n -> n));
        System.out.println(processNumbers(numbers, n -> n % 2 != 0, n -> n * 2));

        // sum of squared even numbers
        System.out.println(processNumbers(numbers, n -> n % 2 == 0, n -> n * n));

        // sum of squared odd numbers
        System.out.println(processNumbers(numbers, n -> n % 2 != 0, n -> n * n));

        // sum of squared odd numbers multiplied by 2
        System.out.println(processNumbers(numbers, n -> n % 2 != 0, n -> n * n * 2));
    }

    public static Integer processNumbers(
            List<Integer> numbers,
            Predicate<Integer> filter,
            Function<Integer, Integer> transformer) {
        var sum = 0;

        for (var number : numbers) {
            if (filter.test(number)) {
                sum += transformer.apply(number);
            }
        }

        return sum;
    }
}