package src.main.java.July2024;

public class MyCounter extends Thread {

    public static void main (String [] args) {
        new Thread(new MyCounter()).start();
    }

    public void run() {
        try {
            int x;
            for ( x = 5; x < 55; x += 2) {
                if (x != 0 && x % 5 == 0) {
                    System.out.println(Thread.currentThread().getName() + "-" + x);
                }
                sleep(5);
            }
            System.out.println(x);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

// output:
//    Thread-1-5
//    Thread-1-15
//    Thread-1-25
//    Thread-1-35
//    Thread-1-45
//            55