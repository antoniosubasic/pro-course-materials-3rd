public class CustomThread extends Thread {
    @Override
    public void run() {
        for (var i = 0; i < 5; i++) {
            System.out.print("1 ");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
