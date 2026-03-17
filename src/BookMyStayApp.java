import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;

import java.util.*;

// -------------------- Reservation --------------------
class Reservation implements Serializable {
 private static final long serialVersionUID = 1L;

 private String reservationId;
 private String guestName;
 private String roomType;

 public Reservation(String reservationId, String guestName, String roomType) {
  this.reservationId = reservationId;
  this.guestName = guestName;
  this.roomType = roomType;
 }

 public String getReservationId() {
  return reservationId;
 }

 public String getGuestName() {
  return guestName;
 }

 public String getRoomType() {
  return roomType;
 }

 @Override
 public String toString() {
  return reservationId + " | " + guestName + " | " + roomType;
 }
}

// -------------------- System State Wrapper --------------------
class SystemState implements Serializable {
 private static final long serialVersionUID = 1L;

 List<Reservation> reservations;
 Map<String, Integer> inventory;

 public SystemState(List<Reservation> reservations, Map<String, Integer> inventory) {
  this.reservations = reservations;
  this.inventory = inventory;
 }
}

// -------------------- Persistence Service --------------------
class PersistenceService {

 private static final String FILE_NAME = "system_state.ser";

 // Save state
 public void save(SystemState state) {
  try (ObjectOutputStream oos =
               new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

   oos.writeObject(state);
   System.out.println("✅ System state saved successfully.");

  } catch (IOException e) {
   System.out.println("❌ Error saving system state: " + e.getMessage());
  }
 }

 // Load state
 public SystemState load() {
  File file = new File(FILE_NAME);

  if (!file.exists()) {
   System.out.println("⚠ No previous state found. Starting fresh.");
   return null;
  }

  try (ObjectInputStream ois =
               new ObjectInputStream(new FileInputStream(FILE_NAME))) {

   SystemState state = (SystemState) ois.readObject();
   System.out.println("✅ System state loaded successfully.");
   return state;

  } catch (IOException | ClassNotFoundException e) {
   System.out.println("❌ Error loading state. Starting fresh.");
   return null;
  }
 }
}

public class BookMyStayApp {
 public static void main(String[] args) {
  PersistenceService persistenceService = new PersistenceService();

  List<Reservation> reservations;
  Map<String, Integer> inventory;

  // -------- LOAD PREVIOUS STATE --------
  SystemState loadedState = persistenceService.load();

  if (loadedState != null) {
   reservations = loadedState.reservations;
   inventory = loadedState.inventory;
  } else {
   // Fresh start
   reservations = new ArrayList<>();
   inventory = new HashMap<>();

   inventory.put("Standard", 2);
   inventory.put("Deluxe", 1);
   inventory.put("Suite", 1);
  }

  // -------- DISPLAY CURRENT STATE --------
  System.out.println("\n--- Current Inventory ---");
  for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
   System.out.println(entry.getKey() + ": " + entry.getValue());
  }

  System.out.println("\n--- Booking History ---");
  if (reservations.isEmpty()) {
   System.out.println("No reservations found.");
  } else {
   for (Reservation r : reservations) {
    System.out.println(r);
   }
  }

  // -------- ADD NEW BOOKING --------
  System.out.println("\nAdding new booking...");

  // Generate unique reservation ID
  String newId = "RES" + (300 + reservations.size() + 1);

  Reservation newReservation =
          new Reservation(newId, "Alice", "Standard");

  if (inventory.getOrDefault("Standard", 0) > 0) {
   reservations.add(newReservation);
   inventory.put("Standard", inventory.get("Standard") - 1);

   System.out.println("✅ Booking added: " + newReservation);
  } else {
   System.out.println("❌ No availability for Standard room.");
  }

  // -------- SAVE STATE --------
  SystemState newState = new SystemState(reservations, inventory);
  persistenceService.save(newState);

  System.out.println("\n💾 System ready for shutdown.");
 }
}













