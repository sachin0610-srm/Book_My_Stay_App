class Room {
    private String name;
    private int numberOfBeds;
    private int squareFeet;
    private double pricePerNight;

    Room(String name, int numberOfBeds, int squareFeet, double pricePerNight) {
        this.name = name;
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public String getName() {
        return name;
    }
    public int getBeds() {
        return numberOfBeds;
    }
    public int getSize() {
        return squareFeet;
    }
    public double getPricePerNight() {
        return pricePerNight;
    }

    public void displayRoomInfo(int available) {
        System.out.println(name + " Room:");
        System.out.println("  Beds: " + numberOfBeds);
        System.out.println("  Size: " + squareFeet + " sqft");
        System.out.println("  Price per night: " + pricePerNight);
        System.out.println("  Available: " + available + "\n");
    }
}