public class Main {
    public static void main(String[] args) {
        var sw = new StopWatch();

        var green = new Thread(
            () -> sw.countDown(7),
            ThreadColor.ANSI_GREEN.name()
        );
        var red = new Thread(
            () -> sw.countDown(10),
            ThreadColor.ANSI_RED.name()
        );
        var blue = new Thread(
            () -> sw.countDown(10),
            ThreadColor.ANSI_BLUE.name()
        );

        green.start();
        red.start();
        blue.start();
    }
}

class StopWatch {
    private int i; // all threads will share this variable

    public StopWatch() {
    }

    public void countDown(int startCount) {
        String threadName = Thread.currentThread().getName();
        ThreadColor threadColor = ThreadColor.ANSI_RESET;
        try {
            threadColor = ThreadColor.valueOf(threadName);
        } catch (IllegalArgumentException ignore) {
        }
        
        String color = threadColor.color();

        for (i = startCount; i >= 0; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("%s%s: %d%n", color, threadName, i);
        }
    }
}