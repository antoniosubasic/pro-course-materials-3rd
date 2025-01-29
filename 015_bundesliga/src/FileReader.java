import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FileReader {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public static List<Match> readMatchesFromFile(Path filePath) throws Exception {
        return Files.lines(filePath)
                .skip(1)
                .map(FileReader::processLine)
                .toList();
    }

    private static Match processLine(String line) {
        var parts = line.split(",");
        return new Match(Gruppe.fromName(parts[0]),
                Integer.parseInt(parts[1]),
                LocalDate.parse(parts[2], DATE_FORMAT),
                LocalTime.parse(parts[3], TIME_FORMAT),
                parts[4],
                parts[5],
                Integer.parseInt(parts[6]),
                Integer.parseInt(parts[7]),
                Integer.parseInt(parts[8]),
                Integer.parseInt(parts[9]));
    }
}
