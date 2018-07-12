public class MainConcurrency {
    private static final Object object1 = new Object();
    private static final Object object2 = new Object();

    public static void main(String[] args) {

        Thread firstThread = new Thread(() -> {
            synchronized (object1) {
                locked("obj1");
                Thread.yield();
                synchronized (object2) {
                    locked("obj2");
                }
                unlocked("obj2");
            }
            unlocked("obj1");
        });

        Thread secondThread = new Thread(() -> {
            synchronized (object2) {
                locked("obj2");
                Thread.yield();
                synchronized (object1) {
                    locked("obj1");
                }
                unlocked("obj1");
            }
            unlocked("obj2");
        });

        firstThread.start();
        secondThread.start();
    }

    static void unlocked(String objName) {
        System.out.println(Thread.currentThread().getName() + " unlocked " + objName);
    }

    static void locked(String objName) {
        System.out.println(Thread.currentThread().getName() + " locked on " + objName);
    }
}