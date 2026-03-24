import java.util.*;
import java.util.*;

public class BookMyStayApp {

    // ------------------- EXISTING CLASSES (Simplified) -------------------

    static class Reservation {
        private String reservationId;
        private String roomType;

        public Reservation(String reservationId, String roomType) {
            this.reservationId = reservationId;
            this.roomType = roomType;
        }

        public String getReservationId() {
            return reservationId;
        }

        public String getRoomType() {
            return roomType;
        }
    }

    // ------------------- ADD-ON SERVICE CLASSES -------------------

    // Represents a service
    static class AddOnService {
        private String name;
        private double cost;

        public AddOnService(String name, double cost) {
            this.name = name;
            this.cost = cost;
        }

        public double getCost() {
            return cost;
        }

        public String getName() {
            return name;
        }
    }

    // Manages services
    static class AddOnServiceManager {
        private Map<String, List<AddOnService>> reservationServicesMap = new HashMap<>();

        // Add service
        public void addService(String reservationId, AddOnService service) {
            reservationServicesMap
                    .computeIfAbsent(reservationId, k -> new ArrayList<>())
                    .add(service);
        }

        // Get services
        public List<AddOnService> getServices(String reservationId) {
            return reservationServicesMap.getOrDefault(reservationId, new ArrayList<>());
        }

        // Calculate total cost
        public double calculateTotalCost(String reservationId) {
            double total = 0;

            for (AddOnService service : getServices(reservationId)) {
                total += service.getCost();
            }

            return total;
        }
    }

    // ------------------- MAIN METHOD -------------------

    public static void main(String[] args) {

        // Create a reservation (simulate existing booking)
        Reservation reservation = new Reservation("RES1", "Single");

        // Initialize Add-On Manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Create add-on services
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService pickup = new AddOnService("Airport Pickup", 1000);

        // Guest selects services
        System.out.println("Add-On Service Selection");

        manager.addService(reservation.getReservationId(), breakfast);
        manager.addService(reservation.getReservationId(), pickup);

        // Display reservation info
        System.out.println("Reservation ID: " + reservation.getRoomType() + "-1");

        // Calculate total cost
        double totalCost = manager.calculateTotalCost(reservation.getReservationId());

        System.out.println("Total Add-On Cost: " + totalCost);
    }
}