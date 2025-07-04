package src.main.java.July2024;

import java.util.concurrent.CyclicBarrier;

class Cyclist implements Runnable {
    private final CyclicBarrier point2;
    private final CyclicBarrier point3;
    private final int id;

    public Cyclist(CyclicBarrier b1, CyclicBarrier b2, int id) {
        this.point2 = b1;
        this.point3 = b2;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            // Point-1 to Point-2
            System.out.printf("Cyclist %d is cycling from Point-1 to Point-2\n", id);
            Thread.sleep((long) (Math.random() * 2000));
            System.out.printf("Cyclist %d has reached Point-2.\n", id);
            point2.await();

            // Point-2 to Point-3
            System.out.printf("Cyclist %d is now cycling from Point-2 to Point-3\n", id);
            Thread.sleep((long) (Math.random() * 2000));
            System.out.printf("Cyclist %d has finished the race at Point-3.\n", id);
            point3.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class CyclistRace { // wrapper class
    public static void main(String[] args) { // cannot change
        final int NUM_CYCLISTS = 4;

        CyclicBarrier b1 = new CyclicBarrier(NUM_CYCLISTS,
                () -> System.out.println("All cyclists have regrouped at Point-2."));
        CyclicBarrier b2 = new CyclicBarrier(NUM_CYCLISTS,
                () -> System.out.println("All cyclists have reached the finish line at Point-3."));

        for (int i = 1; i <= NUM_CYCLISTS; i++) {
            new Thread(new Cyclist(b1, b2, i)).start();
        }
    }
}

// output:
//    Cyclist 1 is cycling from Point-1 to Point-2
//    Cyclist 3 is cycling from Point-1 to Point-2
//    Cyclist 4 is cycling from Point-1 to Point-2
//    Cyclist 2 is cycling from Point-1 to Point-2
//    Cyclist 2 has reached Point-2.
//    Cyclist 1 has reached Point-2.
//    Cyclist 3 has reached Point-2.
//    Cyclist 4 has reached Point-2.
//    All cyclists have regrouped at Point-2.
//    Cyclist 4 is now cycling from Point-2 to Point-3
//    Cyclist 2 is now cycling from Point-2 to Point-3
//    Cyclist 1 is now cycling from Point-2 to Point-3
//    Cyclist 3 is now cycling from Point-2 to Point-3
//    Cyclist 4 has finished the race at Point-3.
//    Cyclist 1 has finished the race at Point-3.
//    Cyclist 3 has finished the race at Point-3.
//    Cyclist 2 has finished the race at Point-3.
//    All cyclists have reached the finish line at Point-3.