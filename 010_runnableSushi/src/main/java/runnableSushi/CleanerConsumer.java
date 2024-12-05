package runnableSushi;

public class CleanerConsumer implements ConsumerStrategy {
    @Override
    public Food consume(Belt belt, int pos, String name) throws InterruptedException {
        synchronized (belt) {
            while (belt.isFreeAtPosition(pos)) {
                belt.wait();
            }

            var food = belt.remove(pos);
            System.out.println(
                    String.format("*** Cleaner %s consumed %s at position %d", name,
                            food.getId(),
                            pos));
            return food;
        }
    }
}
