import java.util.*;

// =======================
// Booking Request Class
// =======================
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// =======================
// Thread-Safe Room Inventory
// =======================
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Single", 2);
        rooms.put("Double", 2);
    }

    // 🔐 Critical Section
    public synchronized String bookRoom(String roomType, String guestName) {

        int available = rooms.getOrDefault(roomType, 0);


// =======================
// Booking Record Class
// =======================
class BookingRecord {
    private Map<String, String> bookings = new HashMap<>();

    public void addBooking(String reservationId, String roomType) {
        bookings.put(reservationId, roomType);
    }

    public boolean exists(String reservationId) {
        return bookings.containsKey(reservationId);
    }

    public String getRoomType(String reservationId) {
        return bookings.get(reservationId);
    }

    public void removeBooking(String reservationId) {
        bookings.remove(reservationId);
    }
}

// =======================
// Cancellation Service
// =======================
class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId,
                              BookingRecord record,
                              RoomInventory inventory) {

        if (!record.exists(reservationId)) {
            System.out.println("Cancellation failed: Reservation not found.");
            return;

        if (available > 0) {
            rooms.put(roomType, available - 1);
            return guestName + " successfully booked " + roomType;
        } else {
            return guestName + " failed to book " + roomType + " (No rooms available)";

        }
    }


        String roomType = record.getRoomType(reservationId);

        rollbackStack.push(reservationId);

        inventory.increaseRoom(roomType);

        record.removeBooking(reservationId);

        // EXACT OUTPUT
        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);

        System.out.println("\nRollback History (Most Recent First):");
        while (!rollbackStack.isEmpty()) {
            System.out.println("Released Reservation ID: " + rollbackStack.pop());
        }

        System.out.println("\nUpdated " + roomType + " Room Availability: "
                + inventory.getAvailableRooms(roomType));

    public void display() {
        System.out.println("\nFinal Inventory:");
        System.out.println("Single : " + rooms.get("Single"));
        System.out.println("Double : " + rooms.get("Double"));

    }
}

// =======================

// MAIN CLASS (ONLY PUBLIC)

// Shared Booking Queue
// =======================
class BookingQueue {
    private Queue<BookingRequest> queue = new LinkedList<>();

    public synchronized void addRequest(BookingRequest request) {
        queue.offer(request);
    }

    public synchronized BookingRequest getRequest() {
        return queue.poll();
    }
}

// =======================
// Booking Processor Thread
// =======================
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private RoomInventory inventory;
    private List<String> outputList;

    public BookingProcessor(BookingQueue queue, RoomInventory inventory, List<String> outputList) {
        this.queue = queue;
        this.inventory = inventory;
        this.outputList = outputList;
    }

    @Override
    public void run() {
        while (true) {
            BookingRequest request;

            synchronized (queue) {
                request = queue.getRequest();
            }

            if (request == null) break;

            String result = inventory.bookRoom(request.roomType, request.guestName);

            // Store output in synchronized list (preserve order)
            synchronized (outputList) {
                outputList.add(result);
            }
        }
    }
}

// =======================
// MAIN CLASS

// =======================
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation\n");

        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();


        // Simulated booking
        record.addBooking("Single-1", "Single");

        service.cancelBooking("Single-1", record, inventory);

        // Ordered input (this ensures deterministic output)
        queue.addRequest(new BookingRequest("Alice", "Single"));
        queue.addRequest(new BookingRequest("Bob", "Single"));
        queue.addRequest(new BookingRequest("Charlie", "Single"));
        queue.addRequest(new BookingRequest("David", "Double"));
        queue.addRequest(new BookingRequest("Eve", "Double"));

        List<String> outputList = Collections.synchronizedList(new ArrayList<>());

        // Threads
        BookingProcessor t1 = new BookingProcessor(queue, inventory, outputList);
        BookingProcessor t2 = new BookingProcessor(queue, inventory, outputList);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //  Print in FIXED ORDER
        for (String result : outputList) {
            System.out.println(result);
        }

        inventory.display();

    }
}