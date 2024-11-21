import java.time.Duration;
import java.time.Instant;

public class CounterExt extends Thread {
    private static volatile int totalCount = 0;
    private int myCount = 0;
    private int maxTotalCount;
    private static final Object lock = new Object();

    public CounterExt(int maxTotalCount, String name) {
        super(name);
        this.maxTotalCount = maxTotalCount;
    }

    @Override
    public void run() {
        var start = Instant.now();

        while (true) {
            synchronized (lock) {
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

    public int getMyCount() {
        return myCount;
    }
}
