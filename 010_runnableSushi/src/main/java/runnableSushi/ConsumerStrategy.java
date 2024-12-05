package runnableSushi;

public interface ConsumerStrategy {
    public Food consume(Belt belt, int pos, String name) throws InterruptedException;
}
