public class App {
    public static void main(String[] args) throws Exception {
        var maxTotalCount = 1000000;

        var counter1 = new CounterExt(maxTotalCount, "Counter1");
        var counter2 = new CounterExt(maxTotalCount, "Counter2");
        var counter3 = new CounterExt(maxTotalCount, "Counter3");

        counter1.start();
        counter2.start();
        counter3.start();

        counter1.join();
        counter2.join();
        counter3.join();

        System.out.println("Main ist fertig");
    }
}
