import java.util.*;
import java.util.HashMap;
import java.util.Map;

// Abstract Room class
abstract class Room {
 private String roomType;
 private int numberOfBeds;
 private double pricePerNight;

 public Room(String roomType, int numberOfBeds, double pricePerNight) {
  this.roomType = roomType;
  this.numberOfBeds = numberOfBeds;
  this.pricePerNight = pricePerNight;
 }

 public String getRoomType() {
  return roomType;
 }

 public int getNumberOfBeds() {
  return numberOfBeds;
 }

 public double getPricePerNight() {
  return pricePerNight;
 }

 public abstract void displayRoomDetails();
}

// Concrete room types
class SingleRoom extends Room {
 public SingleRoom() {
  super("Single Room", 1, 1500.00);
 }
 @Override
 public void displayRoomDetails() {
  System.out.println("Room Type: " + getRoomType());
  System.out.println("Beds: " + getNumberOfBeds());
  System.out.println("Price per Night: ₹" + getPricePerNight());
 }
}

class DoubleRoom extends Room {
 public DoubleRoom() {
  super("Double Room", 2, 2500.00);
 }
 @Override
 public void displayRoomDetails() {
  System.out.println("Room Type: " + getRoomType());
  System.out.println("Beds: " + getNumberOfBeds());
  System.out.println("Price per Night: ₹" + getPricePerNight());
 }
}

class SuiteRoom extends Room {
 public SuiteRoom() {
  super("Suite Room", 3, 5000.00);
 }
 @Override
 public void displayRoomDetails() {
  System.out.println("Room Type: " + getRoomType());
  System.out.println("Beds: " + getNumberOfBeds());
  System.out.println("Price per Night: ₹" + getPricePerNight());
 }
}

// Centralized Inventory
class RoomInventory {
 private Map<String, Integer> inventory;

 public RoomInventory() {
  inventory = new HashMap<>();
 }

 public void addRoomType(String roomType, int availability) {
  inventory.put(roomType, availability);
 }

 public int getAvailability(String roomType) {
  return inventory.getOrDefault(roomType, 0);
 }

 public Map<String, Integer> getAllAvailability() {
  return new HashMap<>(inventory); // defensive copy
 }
}

// Search Service (read-only)
class SearchService {
 private RoomInventory inventory;
 private Map<String, Room> roomCatalog;

 public SearchService(RoomInventory inventory) {
  this.inventory = inventory;
  roomCatalog = new HashMap<>();

  // Register room domain objects
  roomCatalog.put("Single Room", new SingleRoom());
  roomCatalog.put("Double Room", new DoubleRoom());
  roomCatalog.put("Suite Room", new SuiteRoom());
 }

 // Display available rooms without modifying inventory
 public void displayAvailableRooms() {
  System.out.println("\n--- Available Rooms ---");
  for (Map.Entry<String, Room> entry : roomCatalog.entrySet()) {
   String roomType = entry.getKey();
   int availability = inventory.getAvailability(roomType);

   if (availability > 0) {
    Room room = entry.getValue();
    room.displayRoomDetails();
    System.out.println("Available: " + availability + "\n");
   }
  }
 }
}


public class BookMyStayApp {
 public static void main(String[] args) {
  System.out.println("======================================");
  System.out.println("   Book My Stay - Version 4.1         ");
  System.out.println("   Room Search & Availability Check   ");
  System.out.println("======================================");

  // Initialize inventory
  RoomInventory inventory = new RoomInventory();
  inventory.addRoomType("Single Room", 5);
  inventory.addRoomType("Double Room", 0); // unavailable
  inventory.addRoomType("Suite Room", 2);

  // Initialize search service
  SearchService searchService = new SearchService(inventory);

  // Guest initiates search
  searchService.displayAvailableRooms();

  System.out.println("======================================");
  System.out.println("   End of Use Case 4 Demonstration     ");
  System.out.println("======================================");


 }
}









