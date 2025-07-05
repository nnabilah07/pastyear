package src.main.java.February2024;

public class NumberCounter extends Thread{

    public static void main (String [] args) {
        new Thread(new NumberCounter ()).start();
    }

    public void run() {
        try {
            int x;
            for (x = 0; x < 300; x++) {
                if (x != 0 && x % 50 == 0) {
                    System.out.println(Thread.currentThread().getName() + x);
                }
                sleep(5);
            }
            System.out.println(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

// output:

