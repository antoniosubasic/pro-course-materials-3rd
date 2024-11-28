package supermarket;

import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) throws Exception {
        Queue<Customer> queue = new LinkedList<>();

        var customerGen = new CustomerGen(queue);
        customerGen.start();

        var checkout1 = new Checkout(queue, "Checkout 1");
        var checkout2 = new Checkout(queue, "Checkout 2");
        var checkout3 = new Checkout(queue, "Checkout 3");
        var checkout4 = new Checkout(queue, "Checkout 4");

        checkout1.start();
        checkout2.start();
        checkout3.start();
        checkout4.start();

        Thread.sleep(3000);

        customerGen.interrupt();

        checkout1.interrupt();
        checkout2.interrupt();
        checkout3.interrupt();
        checkout4.interrupt();
    }
}
