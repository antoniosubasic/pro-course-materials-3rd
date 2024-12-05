package runnableSushi;

import java.util.concurrent.TimeUnit;

public class RunnableSushi {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Runnable Sushi opens ...");

        // Create and start belt
        var belt = new Belt(12);
        belt.start();

        // Create and start cooks
        var sushiCook = new Producer("S", FoodType.SUSHI, belt, 1);
        var appetizerCook = new Producer("A", FoodType.APPETIZER, belt, 2);
        sushiCook.start();
        appetizerCook.start();

        // Create and start guests
        var ann = new Consumer(ConsumerType.GUEST, "Ann", belt, 3);
        var bob = new Consumer(ConsumerType.GUEST, "Bob", belt, 5);
        var joe = new Consumer(ConsumerType.GUEST, "Joe", belt, 7);
        ann.start();
        bob.start();
        joe.start();

        // Wait until the Runnable Sushi has to close
        TimeUnit.SECONDS.sleep(15);

        // Stop the cooks
        sushiCook.interrupt();
        appetizerCook.interrupt();

        // Stop the guests
        ann.interrupt();
        bob.interrupt();
        joe.interrupt();

        // Clean the belt
        var cleaner = new Consumer(ConsumerType.CLEANER, "Tom", belt, 0);
        cleaner.start();

        sushiCook.join();
        appetizerCook.join();
        ann.join();
        bob.join();
        joe.join();
        cleaner.join();

        // Stop the belt
        belt.interrupt();
        belt.join();

        // Close Runnable Sushi
        System.out.println("Runnable Sushi closes");
    }

}
