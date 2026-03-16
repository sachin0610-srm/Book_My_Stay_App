import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;
    private String assignedRoomId;
    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
    public void setAssignedRoomId(String id) {
        this.assignedRoomId = id;
    }
    public String getGuestName() {
        return guestName;
    }
    public String getRoomType() {
        return roomType;
    }
    public String getAssignedRoomId() {
        return assignedRoomId;
    }
}
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation reservation) {
        queue.add(reservation);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasPendingRequests() {
        return !queue.isEmpty();
    }
}
class InventoryService {
    private Map<String, Integer> availableCounts = new HashMap<>();
    private Map<String, Integer> roomCounters = new HashMap<>();
    public InventoryService() {
        availableCounts.put("Single", 10);
        availableCounts.put("Double", 5);
        availableCounts.put("Suite", 2);
        roomCounters.put("Single", 0);
        roomCounters.put("Double", 0);
        roomCounters.put("Suite", 0);
    }
    public boolean isAvailable(String roomType) {
        return availableCounts.getOrDefault(roomType, 0) > 0;
    }
    public String allocateRoom(String roomType) {
        if (!isAvailable(roomType)) return null;
        int count = roomCounters.get(roomType) + 1;
        roomCounters.put(roomType, count);
        availableCounts.put(roomType, availableCounts.get(roomType) - 1);
        return roomType + "-" + count;
    }
}
class BookingService {
    private InventoryService inventory;
    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }
    public void processQueue(BookingRequestQueue queue) {
        System.out.println("Room Allocation Processing");
        while (queue.hasPendingRequests()) {
            Reservation request = queue.getNextRequest();
            if (inventory.isAvailable(request.getRoomType())) {
                String roomId = inventory.allocateRoom(request.getRoomType());
                request.setAssignedRoomId(roomId);

                System.out.println("Booking confirmed for Guest: "
                        + request.getGuestName()
                        + ", Room ID: "
                        + roomId);
            } else {
                System.out.println("Booking failed for Guest: "
                        + request.getGuestName()
                        + ", No rooms available for "
                        + request.getRoomType());
            }
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        InventoryService inventory = new InventoryService();
        BookingService service = new BookingService(inventory);
        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Suite"));
        service.processQueue(bookingQueue);
    }
}