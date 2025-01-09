package analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class LotteryAnalyzerTask implements Runnable {
    private final Path filePath;
    private final List<Integer> winningNumbers;

    public LotteryAnalyzerTask(Path filePath, List<Integer> winningNumbers) {
        this.filePath = filePath;
        this.winningNumbers = winningNumbers;
    }

    @Override
    public void run() {
        try {
            var tips = readLotteryTipsFromFile(filePath);

            for (var tip : tips) {
                var correctCount = getCorrectCount(tip);
                if (correctCount >= 5) {
                    printResult(tip, correctCount);
                }
            }
        } catch (IOException e) {
            System.err.println(String.format("error reading file '%s'", filePath));
        }
    }

    private List<LotteryTip> readLotteryTipsFromFile(Path path) throws IOException {
        List<LotteryTip> tips = new ArrayList<>();

        for (var line : Files.readAllLines(filePath)) {
            var tip = processLine(line);
            if (tip != null) {
                tips.add(tip);
            }
        }

        return tips;
    }

    private LotteryTip processLine(String line) {
        try {
            var parts = line.trim().split(",");
            if (parts.length != 7) {
                return null;
            }

            var id = parts[0];
            int[] numbers = new int[6];
            for (int i = 0; i < 6; i++) {
                numbers[i] = Integer.parseInt(parts[i + 1]);
            }

            return new LotteryTip(id, numbers);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private int getCorrectCount(LotteryTip tip) {
        int count = 0;

        for (int number : tip.numbers()) {
            if (winningNumbers.contains(number)) {
                count++;
            }
        }

        return count;
    }

    private void printResult(LotteryTip tip, int correctCount) {
        var sb = new StringBuilder();
        sb.append(String.format("Thread: %s, ID: %s ", Thread.currentThread().getName(), tip.id()));

        for (var number : tip.numbers()) {
            sb.append(String.format("%d%s ", number, winningNumbers.contains(number) ? "*" : ""));
        }

        sb.append("- Corr ").append(correctCount);
        System.out.println(sb.toString());
    }
}
