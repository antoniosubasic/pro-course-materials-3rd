import java.time.Duration;
import java.time.Instant;

public class CounterExt extends Thread {
    private static volatile int totalCount = 0;
    private final int maxTotalCount;

    public CounterExt(int maxTotalCount, String name) {
        super(name);
        this.maxTotalCount = maxTotalCount;
    }

    @Override
    public void run() {
        var myCount = 0;
        var start = Instant.now();

        while (true) {
            synchronized (getClass()) {
                if (totalCount >= maxTotalCount) {
                    break;
                } else {
                    myCount++;
                    totalCount++;
                }
            }
        }

        System.out.printf(
                "Name: %s, Durchgänge: %d, Globaler Zählwert: %d, Dauer (ms): %d\n",
                getName(),
                myCount,
                totalCount,
                Duration.between(start, Instant.now()).toMillis());
    }

    public static int getTotalCount() {
        return totalCount;
    }
}
