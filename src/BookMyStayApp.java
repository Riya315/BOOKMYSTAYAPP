import java.util.*;
class BookingRequest {
 private String guestName;
 private String roomType;

 public BookingRequest(String guestName, String roomType) {
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

// Thread-safe Room Inventory
class RoomInventory {

 private Map<String, Integer> inventory = new HashMap<>();

 public RoomInventory() {
  inventory.put("Standard", 2);
  inventory.put("Deluxe", 1);
  inventory.put("Suite", 1);
 }

 // Critical Section (synchronized)
 public synchronized boolean bookRoom(String roomType, String guestName) {

  int available = inventory.getOrDefault(roomType, 0);

  if (available > 0) {
   System.out.println(Thread.currentThread().getName() +
           " processing booking for " + guestName +
           " (" + roomType + ")");

   // Simulate delay (to expose race conditions if not synchronized)
   try {
    Thread.sleep(100);
   } catch (InterruptedException e) {
    Thread.currentThread().interrupt();
   }

   inventory.put(roomType, available - 1);

   System.out.println("Booking SUCCESS for " + guestName +
           " [" + roomType + "]");
   return true;
  } else {
   System.out.println("Booking FAILED for " + guestName +
           " [" + roomType + "] - No availability");
   return false;
  }
 }

 public void displayInventory() {
  System.out.println("\nFinal Inventory:");
  for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
   System.out.println(entry.getKey() + ": " + entry.getValue());
  }
 }
}

// Shared Booking Queue
class BookingQueue {

 private Queue<BookingRequest> queue = new LinkedList<>();

 public synchronized void addRequest(BookingRequest request) {
  queue.offer(request);
 }

 public synchronized BookingRequest getRequest() {
  return queue.poll();
 }
}

// Booking Processor (Thread)
class BookingProcessor extends Thread {

 private BookingQueue queue;
 private RoomInventory inventory;

 public BookingProcessor(String name, BookingQueue queue, RoomInventory inventory) {
  super(name);
  this.queue = queue;
  this.inventory = inventory;
 }

 @Override
 public void run() {
  while (true) {
   BookingRequest request;

   // Fetch request safely
   synchronized (queue) {
    request = queue.getRequest();
   }

   if (request == null) {
    break; // No more requests
   }

   // Process booking (thread-safe method)
   inventory.bookRoom(request.getRoomType(), request.getGuestName());
  }
 }
}





public class BookMyStayApp {
 public static void main(String[] args) {
  RoomInventory inventory = new RoomInventory();
  BookingQueue queue = new BookingQueue();

  // Simulate multiple guests booking same room type
  queue.addRequest(new BookingRequest("Alice", "Deluxe"));
  queue.addRequest(new BookingRequest("Bob", "Deluxe"));
  queue.addRequest(new BookingRequest("Charlie", "Standard"));
  queue.addRequest(new BookingRequest("David", "Standard"));
  queue.addRequest(new BookingRequest("Eve", "Suite"));

  // Create multiple threads (simulating concurrent users)
  BookingProcessor t1 = new BookingProcessor("Thread-1", queue, inventory);
  BookingProcessor t2 = new BookingProcessor("Thread-2", queue, inventory);
  BookingProcessor t3 = new BookingProcessor("Thread-3", queue, inventory);

  // Start threads
  t1.start();
  t2.start();
  t3.start();

  // Wait for completion
  try {
   t1.join();
   t2.join();
   t3.join();
  } catch (InterruptedException e) {
   Thread.currentThread().interrupt();
  }

  // Final inventory state
  inventory.displayInventory();
 }
}













