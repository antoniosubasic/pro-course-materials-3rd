package supermarket;

import java.util.Queue;

public class CustomerGen extends Thread {
    private Queue<Customer> queue;

    public CustomerGen(Queue<Customer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                synchronized (queue) {
                    queue.offer(Customer.create());
                    queue.notifyAll();
                }

                Thread.sleep((long) (Math.random() * 100));
            }
        } catch (InterruptedException _) {
        }

        System.out.println("customer generation done");
    }
}
