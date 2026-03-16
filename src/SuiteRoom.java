public class SuiteRoom extends Room {
    private int available;
    public SuiteRoom(int beds, int size, double price, int available) {
        super(beds, size, price);
        this.available = available;
    }

    public void displayRoomInfo() {
        System.out.println("Suite Room:");
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
        System.out.println("Available: " + available);
        System.out.println();
    }
}