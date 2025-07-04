package src.main.java.July2024;

import java.util.concurrent.locks.ReentrantLock;

public class MyMain {
    public static void main(String [] args) {
        CinemaHall cinemaHall = new CinemaHall(50);
        TicketCounter c1 = new TicketCounter(cinemaHall, 25, "Counter 1");
        TicketCounter c2 = new TicketCounter(cinemaHall, 25, "Counter 2");
        TicketCounter c3 = new TicketCounter(cinemaHall, 25, "Counter 3");

        c1.start();
        c2.start();
        c3.start();
    }
}

interface CinemaInterface { // cannot change
    boolean bookSeat(int seatNumber, String counterName);
}

class CinemaHall implements CinemaInterface {
    private final boolean[] seats;
    private final ReentrantLock lock;

    public CinemaHall(int capacity) {
        seats = new boolean[capacity];
        lock = new ReentrantLock(true); // Fair lock
    }

    @Override
    public boolean bookSeat(int seatNumber, String counterName) {
        lock.lock();
        try {
            if (seatNumber < 1 || seatNumber > seats.length) {
                System.out.printf("Invalid seat number %d by %s\n", seatNumber, counterName);
                return false;
            }
            if (!seats[seatNumber - 1]) {
                seats[seatNumber - 1] = true;
                System.out.printf("Seat %d successfully booked by %s\n", seatNumber, counterName);
                return true;
            } else {
                System.out.printf("Seat %d is already booked. %s could not book it.\n", seatNumber, counterName);
                return false;
            }
        } finally {
            lock.unlock();
        }
    }
}

class TicketCounter extends Thread { // cannot change
    private final CinemaHall hall;
    private final int seatNumber;

    public TicketCounter(CinemaHall hall, int seatNumber, String name) {
        this.hall = hall;
        this.seatNumber = seatNumber;
        this.setName(name);
    }

    public void run() {
        hall.bookSeat(seatNumber, getName());
    }
}

// output:
//    Seat 25 successfully booked by Counter 1
//    Seat 25 is already booked. Counter 2 could not book it.
//    Seat 25 is already booked. Counter 3 could not book it.