import java.util.*;
import java.util.HashMap;
import java.util.Map;


class RoomInventory {
 private Map<String, Integer> inventory;

 // Constructor initializes the inventory
 public RoomInventory() {
  inventory = new HashMap<>();
 }

 // Register a room type with initial availability
 public void addRoomType(String roomType, int availability) {
  inventory.put(roomType, availability);
 }

 // Retrieve availability for a given room type
 public int getAvailability(String roomType) {
  return inventory.getOrDefault(roomType, 0);
 }

 // Update availability (e.g., after booking or cancellation)
 public void updateAvailability(String roomType, int newAvailability) {
  if (inventory.containsKey(roomType)) {
   inventory.put(roomType, newAvailability);
  } else {
   System.out.println("Room type not found in inventory: " + roomType);
  }
 }

 // Display the current inventory state
 public void displayInventory() {
  System.out.println("\n--- Current Room Inventory ---");
  for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
   System.out.println("Room Type: " + entry.getKey() + " | Available: " + entry.getValue());
  }
 }
}

public class BookMyStayApp {
 public static void main(String[] args) {
  System.out.println("======================================");
  System.out.println("   Book My Stay - Version 3.1         ");
  System.out.println("   Centralized Inventory Management   ");
  System.out.println("======================================");

  // Initialize inventory
  RoomInventory inventory = new RoomInventory();

  // Register room types with availability
  inventory.addRoomType("Single Room", 5);
  inventory.addRoomType("Double Room", 3);
  inventory.addRoomType("Suite Room", 2);

  // Display initial inventory
  inventory.displayInventory();

  // Simulate an update (e.g., booking reduces availability)
  System.out.println("\n--- Updating Inventory ---");
  inventory.updateAvailability("Single Room", 4); // One single room booked
  inventory.updateAvailability("Suite Room", 1);  // One suite booked

  // Display updated inventory
  inventory.displayInventory();

  System.out.println("======================================");
  System.out.println("   End of Use Case 3 Demonstration     ");
  System.out.println("======================================");
 }
}









