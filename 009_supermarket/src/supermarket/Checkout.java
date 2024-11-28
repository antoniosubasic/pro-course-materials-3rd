package supermarket;

import java.util.Queue;

public class Checkout extends Thread {
    private double checkoutBalance;
    private Queue<Customer> queue;

    public Checkout(Queue<Customer> queue, String name) {
        super(name);
        checkoutBalance = 0;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                synchronized (queue) {
                    if (queue.isEmpty()) {
                        queue.wait();
                    } else {
                        checkoutBalance += queue.poll().getPurchaseValue();
                    }
                }

                Thread.sleep((long) (40 + Math.random() * 100));
            }
        } catch (InterruptedException _) {
        }

        try {
            while (!queue.isEmpty()) {
                synchronized (queue) {
                    if (queue.isEmpty()) {
                        break;
                    } else {
                        checkoutBalance += queue.poll().getPurchaseValue();
                    }
                }

                Thread.sleep((long) (40 + Math.random() * 100));
            }
        } catch (InterruptedException _) {
        }

        System.out.printf("total checkout balance by %s: € %.2f\n", getName(), checkoutBalance);
    }

    public double getCheckoutBalance() {
        return checkoutBalance;
    }
}
