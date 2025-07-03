package src.main.java.January2025;

import java.util.concurrent.*;

class S5 {
   private static int totalSum = 0;
   public static  void main(String[] args) throws Exception {
       ExecutorService executor = Executors.newFixedThreadPool(3);
       Callable<String> callable = () -> {
           int localSum = 0;
           for (int i = 1; i <= 3; i++) {
               localSum += i;
           }
           synchronized (S5.class) {
               totalSum += localSum;
               return Thread.currentThread().getName() + " - Local: " + localSum + ", Total: " + totalSum;
           }
       };

       try {
           for (int i = 0; i < 10; i++) {
               Future<String> future = executor.submit(callable);
               System.out.println(future.get());
           }
       } finally {
           executor.shutdown();
       }
   }
}

// output:
//    pool-1-thread-1 - Local: 6, Total: 6
//    pool-1-thread-2 - Local: 6, Total: 12
//    pool-1-thread-3 - Local: 6, Total: 18
//    pool-1-thread-1 - Local: 6, Total: 24
//    pool-1-thread-2 - Local: 6, Total: 30
//    pool-1-thread-3 - Local: 6, Total: 36
//    pool-1-thread-1 - Local: 6, Total: 42
//    pool-1-thread-2 - Local: 6, Total: 48
//    pool-1-thread-3 - Local: 6, Total: 54
//    pool-1-thread-1 - Local: 6, Total: 60