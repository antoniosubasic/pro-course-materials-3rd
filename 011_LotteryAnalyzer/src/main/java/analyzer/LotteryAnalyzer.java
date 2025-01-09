package analyzer;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class LotteryAnalyzer {
    private static final List<Integer> WINNING_NUMBERS = List.of(3, 8, 15, 22, 34, 42);
    private static final String DIRECTORY_PATH = "files";
    private static final int NUMBER_OF_THREADS = 4;

    public static void main(String[] args) throws Exception {
        var directoryPath = Paths.get(DIRECTORY_PATH);

        if (!Files.exists(directoryPath) || !Files.isDirectory(directoryPath)) {
            System.err.printf("directory %s does not exist.%n", DIRECTORY_PATH);
            return;
        }

        System.out.printf("Gewinnzahlen: %s%n", WINNING_NUMBERS);

        var files = getAllFiles(Path.of(DIRECTORY_PATH), ".txt");
        var executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        for (var file : files) {
            executor.submit(new LotteryAnalyzerTask(file, WINNING_NUMBERS));
        }

        executor.shutdown();
    }

    public static List<Path> getAllFiles(Path directory, String extension) throws IOException {
        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, "*" + extension)) {
            for (Path entry : stream) {
                if (Files.isRegularFile(entry)) { // Ensure it's a file, not a directory
                    result.add(entry);
                }
            }
        }

        return result;
    }
}
