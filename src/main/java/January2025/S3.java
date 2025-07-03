package src.main.java.January2025;

class Main implements Runnable {
    private final int number;

    public Main (int number) {
        this.number = number;
    }
    @Override
    public void run() {
        for (int i = 1; i <= 2; i++) {
            System.out.printf("%s: %d * %d = %d\n", Thread.currentThread().getName(), number, i, i * number);
        }
    }
}

public class S3{
    public static void main(String[] args) {
        for (int i = 2; i <= 6; i += 2){
            Thread thread = new Thread(new Main(i), "Thread-" + i);
            thread.start();
        }
    }
}

//output:
//    Thread-4: 4 * 1 = 4
//    Thread-4: 4 * 2 = 8
//    Thread-6: 6 * 1 = 6
//    Thread-6: 6 * 2 = 12
//    Thread-2: 2 * 1 = 2
//    Thread-2: 2 * 2 = 4