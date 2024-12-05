package runnableSushi;

public class Belt extends Thread {

    private Food[] foodArr;

    /**
     *
     * @param capacity The capacity of the belt, i.e. the number of positions on the
     *                 belt
     */
    public Belt(int capacity) {
        foodArr = new Food[capacity];
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                synchronized (this) {
                    move();
                    notifyAll();
                    System.out.println(toString());
                }

                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }

        System.out.println("Belt stopped");
    }

    /**
     * Checks if a position is valid on the belt
     * 
     * @param pos A position on the belt
     * @return true if the position is valid, false otherwise
     */
    public boolean isValidPosition(int pos) {
        return pos >= 0 && pos < foodArr.length;
    }

    /**
     * Checks if a position is free on the belt, so that food can be placed there
     * 
     * @param pos A position on the belt
     * @return true if the position is free, false otherwise
     */
    public boolean isFreeAtPosition(int pos) {
        return foodArr[pos] == null;
    }

    /**
     * Checks if the belt is empty, i.e. there is no food on it
     * 
     * @return true if the belt is empty, false otherwise
     */
    public boolean isEmpty() {
        for (var food : foodArr) {
            if (food != null) {
                return false;
            }
        }

        return true;
    }

    /**
     * Adds food to the belt at a given position if the position is free
     * 
     * @param food The food to be added
     * @param pos  The position on the belt where the food should be added
     * @return true if the food was added successfully, false otherwise
     */
    public boolean add(Food food, int pos) {
        if (!isFreeAtPosition(pos)) {
            return false;
        }

        foodArr[pos] = food;
        return true;
    }

    /**
     * Removes food from the belt at a given position
     * 
     * @param pos The position on the belt where the food should be removed
     * @return The food that was removed or null if there was no food at the given
     *         position
     */
    public Food remove(int pos) {
        var food = foodArr[pos];
        foodArr[pos] = null;
        return food;
    }

    /**
     * Moves all food on the belt one position to the right
     */
    public void move() {
        var previous = foodArr[0];

        for (var i = 1; i <= foodArr.length; i++) {
            var current = foodArr[i % foodArr.length];
            foodArr[i % foodArr.length] = previous;
            previous = current;
        }
    }

    /**
     * Returns a string representation of the belt
     * 
     * @return A string representation of the belt
     */
    @Override
    public String toString() {
        var sb = new StringBuilder();

        for (var i = 0; i < foodArr.length; i++) {
            sb.append(String.format("-(%d:%s)", i, foodArr[i] == null ? "..." : foodArr[i].getId()));
        }

        return sb.toString();
    }

}
