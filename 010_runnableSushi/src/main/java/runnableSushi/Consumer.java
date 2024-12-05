package runnableSushi;

import java.util.ArrayList;
import java.util.List;

public class Consumer extends Thread {
    private String name;
    private ConsumerType consumerType;
    private Belt belt;
    private int pos;
    private List<Food> foodList;
    private ConsumerStrategy strategy;

    public Consumer(ConsumerType consumerType, String name, Belt belt, int pos) {
        this.consumerType = consumerType;
        switch (consumerType) {
            case CLEANER:
                strategy = new CleanerConsumer();
                break;
            case GUEST:
                strategy = new GuestConsumer();
                break;
        }
        this.name = name;
        this.belt = belt;
        this.pos = pos;
        this.foodList = new ArrayList<>();
    }

    @Override
    public void run() {
        System.out.println(String.format("%s %s starts consuming at position %d", consumerType.toString(), name, pos));

        try {
            while (consumerType == ConsumerType.CLEANER ? !belt.isEmpty() : !isInterrupted()) {
                var food = strategy.consume(belt, pos, name);
                if (food != null) {
                    foodList.add(food);
                }
            }
        } catch (InterruptedException e) {
        }

        System.out.println(String.format("%s %s stopped\n%s", consumerType.toString(), name, getConsumedFood()));
    }

    /**
     * Returns a string representation of all consumed food
     * 
     * @return a string representation of all consumed food
     */
    public String getConsumedFood() {
        var sb = new StringBuilder();

        for (var i = 0; i < foodList.size(); i++) {
            sb.append(foodList.get(i).getId());
            if (i != foodList.size() - 1) {
                sb.append(" | ");
            }
        }

        return String.format("%s %s took %s", consumerType.toString(), name, sb.toString());
    }
}