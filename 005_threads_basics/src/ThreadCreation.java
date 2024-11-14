public class ThreadCreation {
    public static void main(String[] args) {
        var customThread = new CustomThread();
        customThread.start();

        Runnable myRunnable = () -> {
            for (var i = 0; i < 8; i++) {
                System.out.print("2 ");
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        var myThread = new Thread(myRunnable);
        myThread.start();
    }
}
