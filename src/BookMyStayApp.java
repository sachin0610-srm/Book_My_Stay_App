import java.util.*;

public class BookMyStayApp {

    // ------------------- RESERVATION -------------------
    public static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }
    }

    // ------------------- BOOKING HISTORY -------------------
    public static class BookingHistory {
        private List<Reservation> reservations = new ArrayList<>();

        // Add confirmed booking
        public void addReservation(Reservation reservation) {
            reservations.add(reservation);
        }

        // Get all bookings
        public List<Reservation> getAllReservations() {
            return reservations;
        }
    }

    // ------------------- REPORT SERVICE -------------------
    public static class BookingReportService {

        public void printReport(List<Reservation> reservations) {
            System.out.println("Booking History Report\n");

            for (Reservation r : reservations) {
                System.out.println("Guest: " + r.getGuestName() +
                        ", Room Type: " + r.getRoomType());
            }
        }
    }

    // ------------------- MAIN METHOD -------------------
    public static void main(String[] args) {

        System.out.println("Booking History and Reporting\n");

        // Initialize history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        history.addReservation(new Reservation("Abhi", "Single"));
        history.addReservation(new Reservation("Subha", "Double"));
        history.addReservation(new Reservation("Vanmathi", "Suite"));

        // Generate report
        BookingReportService reportService = new BookingReportService();
        reportService.printReport(history.getAllReservations());
    }
}