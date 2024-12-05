package runnableSushi;

public class GuestConsumer implements ConsumerStrategy {
    @Override
    public Food consume(Belt belt, int pos, String name) throws InterruptedException {
        Thread.sleep((long) (1000 + Math.random() * 5000));

        synchronized (belt) {
            while (belt.isFreeAtPosition(pos)) {
                belt.wait();
            }

            var food = belt.remove(pos);
            System.out.println(
                    String.format("*** Guest %s consumed %s at position %d", name, food.getId(),
                            pos));
            return food;
        }
    }
}
