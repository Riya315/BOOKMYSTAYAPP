import java.util.*;
class Reservation {
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

 @Override
 public String toString() {
  return "Reservation [Guest: " + guestName + ", Room Type: " + roomType + "]";
 }
}

// Booking Request Queue
class BookingRequestQueue {
 private Queue<Reservation> requestQueue;

 public BookingRequestQueue() {
  requestQueue = new LinkedList<>();
 }

 public void addRequest(Reservation reservation) {
  requestQueue.add(reservation);
  System.out.println("Request added: " + reservation);
 }

 public Reservation getNextRequest() {
  return requestQueue.poll(); // FIFO dequeue
 }

 public boolean hasRequests() {
  return !requestQueue.isEmpty();
 }
}

// Inventory Service
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

 public boolean allocateRoom(String roomType) {
  int availability = getAvailability(roomType);
  if (availability > 0) {
   inventory.put(roomType, availability - 1);
   return true;
  }
  return false;
 }

 public void displayInventory() {
  System.out.println("\n--- Current Inventory ---");
  for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
   System.out.println("Room Type: " + entry.getKey() + " | Available: " + entry.getValue());
  }
 }
}

// Booking Service
class BookingService {
 private RoomInventory inventory;
 private Map<String, Set<String>> allocatedRooms; // roomType -> set of room IDs

 public BookingService(RoomInventory inventory) {
  this.inventory = inventory;
  allocatedRooms = new HashMap<>();
 }

 // Generate unique room ID
 private String generateRoomID(String roomType, String guestName) {
  return roomType.substring(0, 3).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 6);
 }

 // Process a booking request
 public void processRequest(Reservation reservation) {
  String roomType = reservation.getRoomType();

  if (inventory.allocateRoom(roomType)) {
   String roomID = generateRoomID(roomType, reservation.getGuestName());

   allocatedRooms.putIfAbsent(roomType, new HashSet<>());
   allocatedRooms.get(roomType).add(roomID);

   System.out.println("CONFIRMED: " + reservation.getGuestName() +
           " booked a " + roomType + " | Room ID: " + roomID);
  } else {
   System.out.println("FAILED: No " + roomType + " available for " + reservation.getGuestName());
  }
 }

 public void displayAllocations() {
  System.out.println("\n--- Allocated Rooms ---");
  for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {
   System.out.println("Room Type: " + entry.getKey() + " | Room IDs: " + entry.getValue());
  }
 }
}




public class BookMyStayApp {
 public static void main(String[] args) {
  System.out.println("======================================");
  System.out.println("   Book My Stay - Version 6.1         ");
  System.out.println("   Reservation Confirmation & Allocation");
  System.out.println("======================================");

  // Initialize inventory
  RoomInventory inventory = new RoomInventory();
  inventory.addRoomType("Single Room", 2);
  inventory.addRoomType("Double Room", 1);
  inventory.addRoomType("Suite Room", 1);

  // Initialize booking queue
  BookingRequestQueue bookingQueue = new BookingRequestQueue();
  bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
  bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
  bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));
  bookingQueue.addRequest(new Reservation("Diana", "Single Room"));
  bookingQueue.addRequest(new Reservation("Ethan", "Double Room")); // should fail

  // Initialize booking service
  BookingService bookingService = new BookingService(inventory);

  // Process requests in FIFO order
  System.out.println("\n--- Processing Requests ---");
  while (bookingQueue.hasRequests()) {
   Reservation next = bookingQueue.getNextRequest();
   bookingService.processRequest(next);
  }

  // Display final allocations and inventory
  bookingService.displayAllocations();
  inventory.displayInventory();

  System.out.println("======================================");
  System.out.println("   End of Use Case 6 Demonstration     ");
  System.out.println("======================================");
 }
}













