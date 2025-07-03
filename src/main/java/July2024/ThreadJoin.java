package src.main.java.July2024;

public class ThreadJoin {
    public static void main (String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 4; i++) {
                    System.out.println(Thread.currentThread().getName() + ": " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    System.out.println(Thread.currentThread().getName() + ": " + i);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("Main thread ends");
    }
}

// output:
//    main
//    Thread-1: 0
//    Thread-0: 0
//    Thread-0: 1
//    Thread-1: 1
//    Thread-0: 2
//    Thread-0: 3
//    Thread-1: 2
//    Main thread ends
