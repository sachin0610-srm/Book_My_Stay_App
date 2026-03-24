import java.util.*;

// =======================
// Custom Exception Class
// =======================
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// =======================
// Room Inventory Class
// =======================
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Suite", 2);
    }

    public boolean isRoomTypeValid(String roomType) {
        return rooms.containsKey(roomType);
    }

    public int getAvailableRooms(String roomType) {
        return rooms.getOrDefault(roomType, 0);
    }

    public void bookRoom(String roomType) throws InvalidBookingException {
        int available = getAvailableRooms(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }

        rooms.put(roomType, available - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Availability:");
        for (String type : rooms.keySet()) {
            System.out.println(type + " : " + rooms.get(type));
        }
    }
}

// =======================
// Booking Request Queue
// =======================
class BookingRequestQueue {
    private Queue<String> queue = new LinkedList<>();

    public void addRequest(String request) {
        queue.offer(request);
    }

    public void processRequests() {
        System.out.println("\nProcessing Booking Requests...");
        while (!queue.isEmpty()) {
            System.out.println("Processed: " + queue.poll());
        }
    }
}

// =======================
// Reservation Validator
// =======================
class ReservationValidator {

    private static final Set<String> VALID_ROOM_TYPES =
            new HashSet<>(Arrays.asList("Single", "Double", "Suite"));

    public void validate(String guestName, String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (roomType == null || roomType.trim().isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty.");
        }

        // ✅ CASE-SENSITIVE + EXACT MESSAGE
        if (!VALID_ROOM_TYPES.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        if (inventory.getAvailableRooms(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available.");
        }
    }
}

// =======================
// Main Use Case Class
// =======================
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=== Booking Validation System ===");

        Scanner scanner = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        try {
            // Input from user
            System.out.print("Enter Guest Name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter Room Type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            // Step 1: Validate input
            validator.validate(guestName, roomType, inventory);

            // Step 2: Book room
            inventory.bookRoom(roomType);

            // Step 3: Add to queue
            bookingQueue.addRequest(guestName + " booked " + roomType);

            System.out.println("Booking successful!");

        } catch (InvalidBookingException e) {
            // Graceful failure handling
            System.out.println("Booking failed: " + e.getMessage());
        } finally {
            scanner.close();
        }

        // Continue system safely
        inventory.displayInventory();
        bookingQueue.processRequests();
    }
}