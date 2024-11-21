import java.util.concurrent.TimeUnit;

public class BankAccount {
    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        try {
            System.out.println("Einzahlung: Warten auf System ...");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        synchronized (this) {
            System.out.printf(
                    "Alter Kontostand: %.2f, Einzahlung: %.2f, Neuer Kontostand: %.2f\n",
                    balance,
                    amount,
                    balance += amount);
        }
    }

    public void withdraw(double amount) {
        try {
            System.out.println("Auszahlung: Warten auf System ...");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        synchronized (this) {
            if (amount <= balance) {
                System.out.printf(
                        "Alter Kontostand: %.2f, Auszahlung: %.2f, Neuer Kontostand: %.2f\n",
                        balance,
                        amount,
                        balance -= amount);
            } else {
                System.out.printf(
                        "Alter Kontostand: %.2f, Auszahlung: %.2f, Guthaben nicht ausreichend\n",
                        balance,
                        amount);
            }
        }
    }

    public double getBalance() {
        return balance;
    }
}
