
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Hotel Room Initialization\n");
        SingleRoom single = new SingleRoom(1, 250, 1500.0, 5);
        DoubleRoom doub = new DoubleRoom(2, 400, 2500.0, 3);
        SuiteRoom suite = new SuiteRoom(3, 750, 5000.0, 2);
        single.displayRoomInfo();
        doub.displayRoomInfo();
        suite.displayRoomInfo();
    }
}