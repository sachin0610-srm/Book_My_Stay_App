import java.util.*;

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

    public void increaseRoom(String roomType) {
        rooms.put(roomType, rooms.get(roomType) + 1);
    }

    public int getAvailableRooms(String roomType) {
        return rooms.getOrDefault(roomType, 0);
    }
}

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
    }
}

// =======================
// MAIN CLASS (ONLY PUBLIC)
// =======================
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking Cancellation");

        RoomInventory inventory = new RoomInventory();
        BookingRecord record = new BookingRecord();
        CancellationService service = new CancellationService();

        // Simulated booking
        record.addBooking("Single-1", "Single");

        service.cancelBooking("Single-1", record, inventory);
    }
}