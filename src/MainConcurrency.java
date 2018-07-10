public class MainConcurrency {
    public static void main(String[] args) {
        new Thread(new TestThread()).start();
        new Thread(new TestThread()).start();
    }

    static class TestThread implements Runnable {
        static final Object lockObject = new Object();

        @Override
        public void run() {
            synchronized (lockObject) {
                System.out.println(Thread.currentThread().getName() + " in sync block");
                try {
                    System.out.println(Thread.currentThread().getName() + " in try block");
                    lockObject.wait();
                    System.out.println(Thread.currentThread().getName() + " after wait");
                    lockObject.notify();
                    System.out.println(Thread.currentThread().getName() + " after notify");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
