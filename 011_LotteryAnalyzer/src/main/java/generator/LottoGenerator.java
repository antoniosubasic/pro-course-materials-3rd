package generator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class LottoGenerator {
    public static void main(String[] args) {
        final int KIOSK_CODE_START = 100;
        final int KIOSK_COUNT = 10;
        final int SHEET_COUNT_FROM = 1000;
        final int SHEET_COUNT_TO = 9000;
        final int TIP_COUNT_MAX = 20;

        String DIRECTORY_PATH = "files";

        File dir = new File(DIRECTORY_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        Random random = new Random();

        for (int kioskCode = KIOSK_CODE_START; kioskCode <= KIOSK_CODE_START + KIOSK_COUNT; kioskCode++) {
            String fileName = DIRECTORY_PATH + "/" + kioskCode + ".txt";

            int sheetCount = random.nextInt(SHEET_COUNT_FROM, SHEET_COUNT_TO);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (int sheetNumber = 1; sheetNumber <= sheetCount; sheetNumber++) {
                    int tipCount = random.nextInt(1, TIP_COUNT_MAX + 1); // 1-20 Tipps pro Lottoschein
                    for (int tippNummer = 1; tippNummer <= tipCount; tippNummer++) {
                        String id = generateID(kioskCode, sheetNumber, tippNummer);
                        List<Integer> tipp = generateRandomLottoNumbers(random);
                        String line = formatLine(id, tipp);
                        writer.write(line);
                        writer.newLine();
                    }
                }
            } catch (IOException e) {
                System.err.println("An error occurred when writing to file '" + fileName + "'");
                e.printStackTrace();
            }
        }

        System.out.println("Lotto files generated successfully!");
    }

    private static String generateID(int lottostellenCode, int scheinNummer, int tippNummer) {
        return String.format("%03d%07d%02d", lottostellenCode, scheinNummer, tippNummer);
    }

    private static List<Integer> generateRandomLottoNumbers(Random random) {
        Set<Integer> numbers = new TreeSet<>();
        while (numbers.size() < 6) {
            numbers.add(random.nextInt(45) + 1); // Zahlen von 1 bis 45
        }
        return new ArrayList<>(numbers);
    }

    private static String formatLine(String id, List<Integer> numbers) {
        StringBuilder builder = new StringBuilder(id);
        for (int number : numbers) {
            builder.append(",").append(number);
        }
        return builder.toString();
    }
}
