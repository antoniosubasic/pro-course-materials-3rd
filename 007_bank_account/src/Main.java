public class Main {
    public static void main(String[] args) {
        var bankAccount = new BankAccount(10000);

        // bankAccount.withdraw(2500);
        // bankAccount.deposit(5000);
        // bankAccount.withdraw(2500);
        // bankAccount.withdraw(5000);

        var t1 = new Thread(() -> bankAccount.withdraw(2500));
        var t2 = new Thread(() -> bankAccount.deposit(5000));
        var t3 = new Thread(() -> bankAccount.withdraw(2500));
        var t4 = new Thread(() -> bankAccount.withdraw(5000));

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.printf("Finales Guthaben: %.2f\n", bankAccount.getBalance());
    }
}
