import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Task1 {
    public static void main(String[] args) throws Exception {
        var numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        update(numbers, number -> number + 1);
        System.out.println(numbers);

        update(numbers, number -> number * 2);
        System.out.println(numbers);

        update(numbers, number -> number - 2);
        System.out.println(numbers);

        var sum = 0;
        for (var i = 0; i < numbers.size(); i++) {
            sum += numbers.get(i);
        }
        final var average = sum / numbers.size();
        update(numbers, number -> number - average);
        System.out.println(numbers);

        // quadrate
        update(numbers, number -> number * number);
        System.out.println(numbers);
    }

    public static void update(List<Integer> numbers, Function<Integer, Integer> updater) {
        for (var i = 0; i < numbers.size(); i++) {
            numbers.set(i, updater.apply(numbers.get(i)));
        }
    }
}
