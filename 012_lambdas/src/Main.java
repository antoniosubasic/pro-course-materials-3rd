import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println(numbers);

        // local class
        class AddOne implements MyFunction {
            @Override
            public Integer apply(Integer number) {
                return number + 1;
            }
        }
        var addOne = new AddOne();
        update(numbers, addOne);
        System.out.println(numbers);

        // anonymous class
        var addTwo = new MyFunction() {
            @Override
            public Integer apply(Integer number) {
                return number + 2;
            }
        };
        update(numbers, addTwo);
        System.out.println(numbers);

        // lambda expression
        update(numbers, number -> number + 3);
        System.out.println(numbers);
    }

    public static void update(List<Integer> numbers, MyFunction function) {
        for (var i = 0; i < numbers.size(); i++) {
            var oldVal = numbers.get(i);
            var newVal = function.apply(oldVal);
            numbers.set(i, newVal);
        }
    }
}

interface MyFunction {
    Integer apply(Integer number);
}
