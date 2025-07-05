package src.main.java.February2024;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import  java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableExample {
    private static int sum = 0;

    public static void main (String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Callable<String> callable = () -> {
            Thread.sleep(100);
            for (int i = 1; i <= 3; i++) {
                sum += i;
            }
            return Thread.currentThread().getName() + "-" + sum;
        };

        try {
            for (int i = 0; i < 8; i++) {
                Future<String> future = executor.submit(callable);
                String result = future.get();
                System.out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        System.out.println("Total: " +sum);
    }
}

// output:

//    pool-1-thread-1-6
//    pool-1-thread-2-12
//    pool-1-thread-3-18
//    pool-1-thread-1-24
//    pool-1-thread-2-30
//    pool-1-thread-3-36
//    pool-1-thread-1-42
//    pool-1-thread-2-48
//    Total: 48