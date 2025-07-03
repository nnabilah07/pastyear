package src.main.java.January2025;

import java.util.concurrent.locks.ReentrantLock;

class Counter implements Runnable{
    private static int count = 0;
    private final ReentrantLock lock;

    public Counter(ReentrantLock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            for (int i = 0; i < 2; i++) {
                int temp = count;
                temp++;
                count = temp;
                System.out.println(Thread.currentThread().getName() + ": " + count);
            }
        } finally {
            lock.unlock();
        }
    }
}
public class S4 {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(new Counter(lock));
        Thread t2 = new Thread(new Counter(lock), "Thread-B");
        Thread t3 = new Thread(new Counter(lock));
        t1.start();
        t2.start();
        t3.start();
    }
}

// output:
//    Thread-0: 1
//    Thread-0: 2
//    Thread-B: 3
//    Thread-B: 4
//    Thread-1: 5
//    Thread-1: 6