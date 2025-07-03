package src.main.java.July2024;

class Main implements Runnable {

    private int number;

    public Main(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 2; i++) {
            System.out.printf("%s: %d * %d = %d\n", Thread.currentThread().getName(), number, i, i * number);
        }
    }
}

public class MyCalculator {
    public static void main (String[] args) {
        for (int i = 2; i <= 10; i += 3) {
            Main calculator = new Main(i);
            Thread thread = new Thread(calculator);
            thread.start();
        }
    }
}

// output:
//    Thread-0: 2 * 1 = 2
//    Thread-0: 2 * 2 = 4
//    Thread-1: 5 * 1 = 5
//    Thread-1: 5 * 2 = 10
//    Thread-2: 8 * 1 = 8
//    Thread-2: 8 * 2 = 16