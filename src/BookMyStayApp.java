import java.util.*;
import java.util.LinkedList;
import java.util.Queue;

// Reservation class representing a guest’s booking intent
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

// Booking Request Queue class
class BookingRequestQueue {
 private Queue<Reservation> requestQueue;

 public BookingRequestQueue() {
  requestQueue = new LinkedList<>();
 }

 // Add a new booking request
 public void addRequest(Reservation reservation) {
  requestQueue.add(reservation);
  System.out.println("Request added: " + reservation);
 }

 // Display all requests in arrival order
 public void displayQueue() {
  System.out.println("\n--- Current Booking Requests (FIFO Order) ---");
  for (Reservation reservation : requestQueue) {
   System.out.println(reservation);
  }
 }
}



public class BookMyStayApp {
 public static void main(String[] args) {
  System.out.println("======================================");
  System.out.println("   Book My Stay - Version 5.1         ");
  System.out.println("   Booking Request Queue (FIFO)       ");
  System.out.println("======================================");

  // Initialize booking request queue
  BookingRequestQueue bookingQueue = new BookingRequestQueue();

  // Guests submit booking requests
  bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
  bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
  bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));
  bookingQueue.addRequest(new Reservation("Diana", "Single Room"));

  // Display queued requests
  bookingQueue.displayQueue();

  System.out.println("======================================");
  System.out.println("   End of Use Case 5 Demonstration     ");
  System.out.println("======================================");




 }
}









