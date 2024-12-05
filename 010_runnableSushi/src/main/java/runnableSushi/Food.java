package runnableSushi;

public class Food {

    private String id;
    private FoodType foodType;

    /**
     * Constructor
     * 
     * @param id       food id
     * @param foodType food type
     */
    public Food(String id, FoodType foodType) {
        this.id = id;
        this.foodType = foodType;
    }

    /**
     * Get food id
     * 
     * @return food id
     */
    public String getId() {
        return id;
    }

    /**
     * A string representation of the food
     * 
     * @return string representation of the food
     */
    @Override
    public String toString() {
        return String.format("%s-%s", foodType.toString(), id);
    }
}
