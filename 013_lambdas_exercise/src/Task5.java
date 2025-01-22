import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Task5 {
    public static void main(String[] args) {
        var numbers = Arrays.asList(-3, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        System.out.println(groupByCondition(numbers, n -> n % 2 == 0));
        System.out.println(groupByCondition(numbers, n -> n < 5));
        System.out.println(groupByCondition(numbers, n -> n >= 0));
        System.out.println(groupByCondition(numbers, Task5::isPrime));

        // group numbers divisible by 3
        System.out.println(groupByCondition(numbers, n -> n % 3 == 0));

        // group numbers divisible by 3 and 5
        System.out.println(groupByCondition(numbers, n -> n % 3 == 0 && n % 5 == 0));

        // group numbers which are prime and greater than 5
        System.out.println(groupByCondition(numbers, n -> isPrime(n) && n > 5));
    }

    public static Map<Boolean, List<Integer>> groupByCondition(List<Integer> numbers, Predicate<Integer> condition) {
        Map<Boolean, List<Integer>> result = new HashMap<>();
        result.put(true, new ArrayList<>());
        result.put(false, new ArrayList<>());

        for (var number : numbers) {
            result.get(condition.test(number)).add(number);
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
