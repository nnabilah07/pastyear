package src.main.java.January2025;

public class S2 {

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
           for (int i = 1; i <= 2; i++) {
               System.out.println(Thread.currentThread().getName() + ": Step " + i);
               try {
                   Thread.sleep(400);
               }catch (InterruptedException e){
                   throw new RuntimeException(e);
               }
           }
        }, "Thread-A");
        Thread thread2 = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println(Thread.currentThread().getName() + ": Step " + i);
                try {
                    Thread.sleep(600);
                }catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
            }
        }, "Thread-B");
        Thread thread3 = new Thread(() -> {
            for (int i = 1; i <= 4; i++) {
                System.out.println(Thread.currentThread().getName() + ": Step " + i);
                try {
                    Thread.sleep(500);
                }catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
            }
        }, "Thread-C");

        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println(Thread.currentThread().getName() + ": All threads completed");
    }
}

//output:
    //Thread-C: Step 1
    //Thread-B: Step 1
    //Thread-A: Step 1
    //Thread-A: Step 2
    //Thread-C: Step 2
    //Thread-B: Step 2
    //Thread-C: Step 3
    //Thread-B: Step 3
    //Thread-C: Step 4
    //main: All threads completed