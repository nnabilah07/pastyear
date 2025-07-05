package src.main.java.February2024;

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

    public static void main(String[] args) {
        for (int i = 1; i <= 3; i++) {
            Main calculator = new Main(i);
            Thread thread = new Thread(calculator);
            thread.start();
        }
    }
}

// output:

//    Thread-0: 1 * 1 = 1
//    Thread-0: 1 * 2 = 2
//    Thread-1: 2 * 1 = 2
//    Thread-1: 2 * 2 = 4
//    Thread-2: 3 * 1 = 3
//    Thread-2: 3 * 2 = 6