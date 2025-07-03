package src.main.java.January2025;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BaggageHandlingSystem {

    public static void main(String[] args) {
        // Create a thread pool with 2 workers representing 2 conveyor belts
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Submit baggage processing tasks dynamically
        for (int i = 0; i < 4; i++) {
            String baggageType = (i % 2 == 0) ? "Connecting Flight Bags" : "Baggage Claim Bags";

            executor.submit(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + " is processing " + baggageType);

                try {
                    // Simulate processing time between 0.5 to 2 seconds
                    long processingTime = (long) (500 + Math.random() * 1500);
                    Thread.sleep(processingTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println(threadName + " finished processing " + baggageType);
            });
        }

        // Gracefully shut down the thread pool after submitting all tasks
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

// output:
//    pool-1-thread-2 is processing Baggage Claim Bags
//    pool-1-thread-1 is processing Connecting Flight Bags
//    pool-1-thread-2 finished processing Baggage Claim Bags
//    pool-1-thread-2 is processing Connecting Flight Bags
//    pool-1-thread-1 finished processing Connecting Flight Bags
//    pool-1-thread-1 is processing Baggage Claim Bags
//    pool-1-thread-2 finished processing Connecting Flight Bags
//    pool-1-thread-1 finished processing Baggage Claim Bags
