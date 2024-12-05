package runnableSushi;

import java.util.ArrayList;
import java.util.List;

public class Producer extends Thread {

    private String name;
    private FoodType foodType;
    private Belt belt;
    private int pos;
    private List<Food> producedFood;

    /**
     * Constructor
     * 
     * @param name     name of the producer
     * @param foodType type of food produced
     * @param belt     belt to place the food on
     * @param pos      position on the belt to place the food
     */
    public Producer(String name, FoodType foodType, Belt belt, int pos) {
        this.name = name;
        this.foodType = foodType;
        this.belt = belt;
        this.pos = pos;
        this.producedFood = new ArrayList<>();
    }

    @Override
    public void run() {
        System.out.println(String.format("Producer %s starts producing at position %d ...", name, pos));

        try {
            while (!isInterrupted()) {
                Thread.sleep((long) (1000 + Math.random() * 1000));

                var food = new Food(String.format("%s-%d", name, producedFood.size() + 1), foodType);
                producedFood.add(food);

                synchronized (belt) {
                    while (!belt.isFreeAtPosition(pos)) {
                        belt.wait();
                    }

                    belt.add(food, pos);
                    System.out.println(String.format("*** %s placed %s at position %d", name, food.getId(), pos));
                }
            }
        } catch (InterruptedException e) {
        }

        System.out.println(String.format("Producer %s stopped\n%s", name, getProducedFood()));
    }

    /**
     * Returns a string representation of all produced food
     * 
     * @return a string representation of all produced food
     */
    public String getProducedFood() {
        var sb = new StringBuilder();

        for (var i = 0; i < producedFood.size(); i++) {
            sb.append(producedFood.get(i).getId());
            if (i != producedFood.size() - 1) {
                sb.append(" | ");
            }
        }

        return String.format("Producer %s produced: %s", name, sb.toString());
    }

}
