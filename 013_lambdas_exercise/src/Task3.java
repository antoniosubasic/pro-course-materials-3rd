import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Task3 {
    public static void main(String[] args) throws Exception {
        var strings = Arrays.asList("Hello", "World", "Java");

        System.out.println(transformStrings(strings, str -> str.toUpperCase()));
        System.out.println(transformStrings(strings, str -> str.toLowerCase()));
        System.out.println(transformStrings(strings, str -> String.format("%s!", str)));
        System.out.println(transformStrings(strings, str -> new StringBuilder(str).reverse().toString()));

        // length of each string
        System.out.println(transformStrings(strings, str -> String.valueOf(str.length())));

        // get only first character
        System.out.println(transformStrings(strings, str -> str.substring(0, 1)));

        // each string in square brackets
        System.out.println(transformStrings(strings, str -> String.format("[%s]", str)));
    }

    public static List<String> transformStrings(List<String> strings, Function<String, String> transformer) {
        var result = new ArrayList<String>();

        for (var string : strings) {
            result.add(transformer.apply(string));
        }

        return result;
    }
}
