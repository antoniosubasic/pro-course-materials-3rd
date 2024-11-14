public class ThreadBasic {
    public static void main(String[] args) {
        var currentThread = Thread.currentThread();
        printThreadState(currentThread);

        currentThread.setName("myThread");
        currentThread.setPriority(Thread.MAX_PRIORITY);
        printThreadState(currentThread);
    }

    public static void printThreadState(Thread t) {
        System.out.println("------------------");
        System.out.println("Thread ID: " + t.threadId());
        System.out.println("Thread Name: " + t.getName());
        System.out.println("Thread Priority: " + t.getPriority());
        System.out.println("Thread State: " + t.getState());
        System.out.println("Thread Group: " + t.getThreadGroup());
        System.out.println("Thread is Alive: " + t.isAlive());
    }
}
