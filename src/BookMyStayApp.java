import java.util.*;
class Reservation {
 private String reservationId;
 private String guestName;
 private String roomType;
 private double totalAmount;

 public Reservation(String reservationId, String guestName, String roomType, double totalAmount) {
  this.reservationId = reservationId;
  this.guestName = guestName;
  this.roomType = roomType;
  this.totalAmount = totalAmount;
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

 public double getTotalAmount() {
  return totalAmount;
 }

 @Override
 public String toString() {
  return "Reservation ID: " + reservationId +
          ", Guest: " + guestName +
          ", Room Type: " + roomType +
          ", Amount: ₹" + totalAmount;
 }
}

// Maintains booking history (ordered)
class BookingHistory {

 private List<Reservation> reservations;

 public BookingHistory() {
  reservations = new ArrayList<>();
 }

 // Add confirmed reservation
 public void addReservation(Reservation reservation) {
  reservations.add(reservation);
  System.out.println("Reservation added to history: " + reservation.getReservationId());
 }

 // Retrieve all reservations
 public List<Reservation> getAllReservations() {
  return Collections.unmodifiableList(reservations); // protects data from modification
 }
}

// Generates reports from booking history
class BookingReportService {

 // Display all bookings
 public void displayAllBookings(List<Reservation> reservations) {
  System.out.println("\n--- Booking History ---");

  if (reservations.isEmpty()) {
   System.out.println("No bookings found.");
   return;
  }

  for (Reservation res : reservations) {
   System.out.println(res);
  }
 }

 // Generate summary report
 public void generateSummary(List<Reservation> reservations) {
  System.out.println("\n--- Booking Summary Report ---");

  int totalBookings = reservations.size();
  double totalRevenue = 0;

  Map<String, Integer> roomTypeCount = new HashMap<>();

  for (Reservation res : reservations) {
   totalRevenue += res.getTotalAmount();

   roomTypeCount.put(
           res.getRoomType(),
           roomTypeCount.getOrDefault(res.getRoomType(), 0) + 1
   );
  }

  System.out.println("Total Bookings: " + totalBookings);
  System.out.println("Total Revenue: ₹" + totalRevenue);

  System.out.println("\nBookings by Room Type:");
  for (Map.Entry<String, Integer> entry : roomTypeCount.entrySet()) {
   System.out.println(entry.getKey() + ": " + entry.getValue());
  }
 }
}





public class BookMyStayApp {
 public static void main(String[] args) {
  BookingHistory history = new BookingHistory();
  BookingReportService reportService = new BookingReportService();

  // Simulate confirmed bookings
  Reservation r1 = new Reservation("RES101", "Alice", "Deluxe", 3000);
  Reservation r2 = new Reservation("RES102", "Bob", "Standard", 2000);
  Reservation r3 = new Reservation("RES103", "Charlie", "Suite", 5000);

  // Add to history (in order)
  history.addReservation(r1);
  history.addReservation(r2);
  history.addReservation(r3);

  // Admin views booking history
  List<Reservation> storedReservations = history.getAllReservations();

  reportService.displayAllBookings(storedReservations);

  // Generate summary report
  reportService.generateSummary(storedReservations);
 }
}










