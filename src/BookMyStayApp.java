import java.util.*;
class CancellationException extends Exception {
 public CancellationException(String message) {
  super(message);
 }
}

// Reservation Model
class Reservation {
 private String reservationId;
 private String roomType;
 private String roomId;
 private boolean isCancelled;

 public Reservation(String reservationId, String roomType, String roomId) {
  this.reservationId = reservationId;
  this.roomType = roomType;
  this.roomId = roomId;
  this.isCancelled = false;
 }

 public String getReservationId() {
  return reservationId;
 }

 public String getRoomType() {
  return roomType;
 }

 public String getRoomId() {
  return roomId;
 }

 public boolean isCancelled() {
  return isCancelled;
 }

 public void cancel() {
  isCancelled = true;
 }

 @Override
 public String toString() {
  return "Reservation ID: " + reservationId +
          ", Room Type: " + roomType +
          ", Room ID: " + roomId +
          ", Status: " + (isCancelled ? "Cancelled" : "Confirmed");
 }
}

// Inventory Management
class RoomInventory {

 private Map<String, Integer> inventory;

 public RoomInventory() {
  inventory = new HashMap<>();
  inventory.put("Standard", 2);
  inventory.put("Deluxe", 1);
  inventory.put("Suite", 1);
 }

 public void increment(String roomType) {
  inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
 }

 public void decrement(String roomType) {
  inventory.put(roomType, inventory.get(roomType) - 1);
 }

 public void displayInventory() {
  System.out.println("\nCurrent Inventory:");
  for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
   System.out.println(entry.getKey() + ": " + entry.getValue());
  }
 }
}

// Booking Store (acts like history + active records)
class BookingStore {

 private Map<String, Reservation> reservations = new HashMap<>();

 public void addReservation(Reservation reservation) {
  reservations.put(reservation.getReservationId(), reservation);
 }

 public Reservation getReservation(String id) {
  return reservations.get(id);
 }
}

// Cancellation Service with rollback using Stack
class CancellationService {

 private RoomInventory inventory;
 private BookingStore store;

 // Stack to track released room IDs (LIFO rollback)
 private Stack<String> rollbackStack = new Stack<>();

 public CancellationService(RoomInventory inventory, BookingStore store) {
  this.inventory = inventory;
  this.store = store;
 }

 public void cancelBooking(String reservationId) {
  try {
   Reservation reservation = store.getReservation(reservationId);

   // Validation
   if (reservation == null) {
    throw new CancellationException("Reservation does not exist.");
   }

   if (reservation.isCancelled()) {
    throw new CancellationException("Reservation already cancelled.");
   }

   // Step 1: Track room ID in rollback stack
   rollbackStack.push(reservation.getRoomId());

   // Step 2: Restore inventory
   inventory.increment(reservation.getRoomType());

   // Step 3: Mark reservation cancelled
   reservation.cancel();

   System.out.println("Cancellation successful for Reservation ID: " + reservationId);

  } catch (CancellationException e) {
   System.out.println("Cancellation failed: " + e.getMessage());
  }
 }

 public void displayRollbackStack() {
  System.out.println("\nRollback Stack (Recently Released Room IDs): " + rollbackStack);
 }
}





public class BookMyStayApp {
 public static void main(String[] args) {
  RoomInventory inventory = new RoomInventory();
  BookingStore store = new BookingStore();
  CancellationService cancellationService = new CancellationService(inventory, store);

  // Simulate confirmed bookings
  Reservation r1 = new Reservation("RES201", "Deluxe", "D1");
  Reservation r2 = new Reservation("RES202", "Standard", "S1");

  store.addReservation(r1);
  store.addReservation(r2);

  // Assume inventory was reduced earlier
  inventory.decrement("Deluxe");
  inventory.decrement("Standard");

  inventory.displayInventory();

  // Valid cancellation
  cancellationService.cancelBooking("RES201");

  // Duplicate cancellation
  cancellationService.cancelBooking("RES201");

  // Invalid reservation
  cancellationService.cancelBooking("RES999");

  inventory.displayInventory();

  cancellationService.displayRollbackStack();
 }
}











