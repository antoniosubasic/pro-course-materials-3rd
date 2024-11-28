package supermarket;

public class Customer {
    private int id;
    private double purchaseValue;

    private static int nextId = 1;

    private Customer() {
        id = nextId++;
        purchaseValue = Math.random() * 100;
    }

    public static Customer create() {
        var customer = new Customer();
        System.out.println("Purchase by " + customer.toString());
        return customer;
    }

    @Override
    public String toString() {
        return "Customer %d: € %7.2f".formatted(id, purchaseValue);
    }

    public int getId() {
        return id;
    }

    public double getPurchaseValue() {
        return purchaseValue;
    }
}
