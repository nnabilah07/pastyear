package src.main.java.January2025;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.LinkedList;
import java.util.Queue;

class ETSTicketCounter {
    private final AtomicInteger availableSeats;
    private final Queue<String> waitingQueue = new LinkedList<>();
    private final Object lock = new Object();
    private final int totalSeats = 5;
    private final boolean[] seatStatus = new boolean[totalSeats]; // Track seat occupancy
    private final int[] customerToSeatMap = new int[11]; // Customer 1-10 to seat mapping (index 0 unused)

    public ETSTicketCounter() {
        this.availableSeats = new AtomicInteger(totalSeats);
    }

    public void bookSeat(String customerName) throws InterruptedException {
        synchronized (lock) {
            int customerNumber = parseCustomerNumber(customerName);
            if (availableSeats.get() > 0) {
                int seatNumber = findAvailableSeat();
                if (seatNumber != -1) {
                    seatStatus[seatNumber - 1] = true;
                    availableSeats.decrementAndGet();
                    customerToSeatMap[customerNumber] = seatNumber;
                    System.out.println(customerName + " successfully booked seat #" + seatNumber);
                    return;
                }
            }
            System.out.println(customerName + ": No seats available. Waiting...");
            waitingQueue.add(customerName);
            lock.wait();
            // Retry booking after being notified
            bookSeat(customerName);
        }
    }

    private int findAvailableSeat() {
        for (int i = 0; i < seatStatus.length; i++) {
            if (!seatStatus[i]) {
                return i + 1; // Seats are 1-based
            }
        }
        return -1;
    }

    private int parseCustomerNumber(String customerName) {
        return Integer.parseInt(customerName.split("-")[1]);
    }

    public void releaseSeat(String customerName) {
        synchronized (lock) {
            int customerNumber = parseCustomerNumber(customerName);
            int seatNumber = customerToSeatMap[customerNumber];
            if (seatNumber > 0) {
                seatStatus[seatNumber - 1] = false;
                customerToSeatMap[customerNumber] = 0;
                availableSeats.incrementAndGet();
                System.out.println(customerName + " released a seat. Seats now available: " + availableSeats.get());
            }
            if (!waitingQueue.isEmpty()) {
                lock.notifyAll();
            }
        }
    }
}

class BookingTask implements Runnable {
    private final ETSTicketCounter ticketCounter;
    private final String customerName;

    public BookingTask(ETSTicketCounter ticketCounter, String customerName) {
        this.ticketCounter = ticketCounter;
        this.customerName = customerName;
    }

    @Override
    public void run() {
        try {
            ticketCounter.bookSeat(customerName);
            Thread.sleep((long) (Math.random() * 1000));
            ticketCounter.releaseSeat(customerName);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(customerName + " was interrupted");
        }
    }
}

public class ETSBookingSystem {
    public static void main(String[] args) {
        ETSTicketCounter ticketCounter = new ETSTicketCounter();

        Thread customer1 = new Thread(new BookingTask(ticketCounter, "Customer-1"));
        Thread customer2 = new Thread(new BookingTask(ticketCounter, "Customer-2"));
        Thread customer3 = new Thread(new BookingTask(ticketCounter, "Customer-3"));
        Thread customer4 = new Thread(new BookingTask(ticketCounter, "Customer-4"));
        Thread customer5 = new Thread(new BookingTask(ticketCounter, "Customer-5"));
        Thread customer6 = new Thread(new BookingTask(ticketCounter, "Customer-6"));
        Thread customer7 = new Thread(new BookingTask(ticketCounter, "Customer-7"));
        Thread customer8 = new Thread(new BookingTask(ticketCounter, "Customer-8"));
        Thread customer9 = new Thread(new BookingTask(ticketCounter, "Customer-9"));
        Thread customer10 = new Thread(new BookingTask(ticketCounter, "Customer-10"));

        customer1.start();
        customer2.start();
        customer3.start();
        customer4.start();
        customer5.start();
        customer6.start();
        customer7.start();
        customer8.start();
        customer9.start();
        customer10.start();
    }
}

// output:
//    Customer-1 successfully booked seat #1
//    Customer-10 successfully booked seat #2
//    Customer-9 successfully booked seat #3
//    Customer-8 successfully booked seat #4
//    Customer-7 successfully booked seat #5
//    Customer-6: No seats available. Waiting...
//    Customer-5: No seats available. Waiting...
//    Customer-4: No seats available. Waiting...
//    Customer-2: No seats available. Waiting...
//    Customer-3: No seats available. Waiting...
//    Customer-1 released a seat. Seats now available: 1
//    Customer-6 successfully booked seat #1
//    Customer-3: No seats available. Waiting...
//    Customer-2: No seats available. Waiting...
//    Customer-4: No seats available. Waiting...
//    Customer-5: No seats available. Waiting...
//    Customer-9 released a seat. Seats now available: 1
//    Customer-3 successfully booked seat #3 xsama dgn output nak should be 1
//    Customer-5: No seats available. Waiting...
//    Customer-4: No seats available. Waiting...
//    Customer-2: No seats available. Waiting...
//    Customer-10 released a seat. Seats now available: 1
//    Customer-5 successfully booked seat #2 xsama dgn output nak should be 1
//    Customer-2: No seats available. Waiting...
//    Customer-4: No seats available. Waiting...
//    Customer-8 released a seat. Seats now available: 1
//    Customer-2 successfully booked seat #4 xsama dgn output nak should be 1
//    Customer-4: No seats available. Waiting...
//    Customer-5 released a seat. Seats now available: 1
//    Customer-4 successfully booked seat #2 xsama dgn output nak should be 1
//    Customer-3 released a seat. Seats now available: 1
//    Customer-7 released a seat. Seats now available: 2
//    Customer-6 released a seat. Seats now available: 3
//    Customer-2 released a seat. Seats now available: 4
//    Customer-4 released a seat. Seats now available: 5