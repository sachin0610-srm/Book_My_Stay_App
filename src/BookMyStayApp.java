import java.io.*;
import java.util.*;

// =======================
// Room Inventory Class
// =======================
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Suite", 2);
    }

    public Map<String, Integer> getRooms() {
        return rooms;
    }

    public void display() {
        System.out.println("\nCurrent Inventory:");
        System.out.println("Single: " + rooms.get("Single"));
        System.out.println("Double: " + rooms.get("Double"));
        System.out.println("Suite: " + rooms.get("Suite"));
    }
}

// =======================
// File Persistence Service
// =======================
class FilePersistenceService {

    // ✅ Save Inventory with file path
    public void saveInventory(RoomInventory inventory, String filePath) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(filePath))) {

            oos.writeObject(inventory);
            System.out.println("Inventory saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving inventory.");
        }
    }

    // ✅ Load Inventory with file path
    public RoomInventory loadInventory(String filePath) {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(filePath))) {

            return (RoomInventory) ois.readObject();

        } catch (Exception e) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return new RoomInventory();
        }
    }
}

// =======================
// MAIN CLASS
// =======================
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("System Recovery");

        String filePath = "inventory.dat";

        FilePersistenceService service = new FilePersistenceService();

        // Load from file
        RoomInventory inventory = service.loadInventory(filePath);

        // Display inventory
        inventory.display();

        // Save to file
        service.saveInventory(inventory, filePath);
    }
}