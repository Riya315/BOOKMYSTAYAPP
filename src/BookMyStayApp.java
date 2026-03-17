import java.util.*;
class InvalidBookingException extends Exception {
 public InvalidBookingException(String message) {
  super(message);
 }
}

// Represents room inventory
class RoomInventory {

 private Map<String, Integer> inventory;

 public RoomInventory() {
  inventory = new HashMap<>();
  inventory.put("Standard", 2);
  inventory.put("Deluxe", 1);
  inventory.put("Suite", 1);
 }

 // Validate room type
 public void validateRoomType(String roomType) throws InvalidBookingException {
  if (!inventory.containsKey(roomType)) {
   throw new InvalidBookingException("Invalid room type: " + roomType);
  }
 }

 // Check availability
 public void validateAvailability(String roomType) throws InvalidBookingException {
  int available = inventory.get(roomType);
  if (available <= 0) {
   throw new InvalidBookingException("No rooms available for type: " + roomType);
  }
 }

 // Book a room (safe update)
 public void bookRoom(String roomType) throws InvalidBookingException {
  validateRoomType(roomType);
  validateAvailability(roomType);

  inventory.put(roomType, inventory.get(roomType) - 1);
 }

 public void displayInventory() {
  System.out.println("\nCurrent Inventory:");
  for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
   System.out.println(entry.getKey() + ": " + entry.getValue());
  }
 }
}

// Represents booking request
class BookingService {

 private RoomInventory inventory;

 public BookingService(RoomInventory inventory) {
  this.inventory = inventory;
 }

 public void createBooking(String guestName, String roomType) {
  try {
   // Fail-fast validation
   if (guestName == null || guestName.trim().isEmpty()) {
    throw new InvalidBookingException("Guest name cannot be empty.");
   }

   inventory.bookRoom(roomType);

   System.out.println("Booking successful for " + guestName +
           " [Room Type: " + roomType + "]");

  } catch (InvalidBookingException e) {
   System.out.println("Booking failed: " + e.getMessage());
  }
 }
}





public class BookMyStayApp {
 public static void main(String[] args) {

  RoomInventory inventory = new RoomInventory();
  BookingService bookingService = new BookingService(inventory);

  inventory.displayInventory();

  // Valid booking
  bookingService.createBooking("Alice", "Deluxe");

  // Invalid room type
  bookingService.createBooking("Bob", "Premium");

  // No availability case
  bookingService.createBooking("Charlie", "Deluxe");

  // Invalid guest name
  bookingService.createBooking("", "Standard");

  // Valid booking again
  bookingService.createBooking("David", "Standard");

  inventory.displayInventory();
 }
}








