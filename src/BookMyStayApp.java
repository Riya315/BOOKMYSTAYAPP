import java.util.*;
// Represents an optional add-on service
class AddOnService {
 private String serviceName;
 private double cost;

 public AddOnService(String serviceName, double cost) {
  this.serviceName = serviceName;
  this.cost = cost;
 }

 public String getServiceName() {
  return serviceName;
 }

 public double getCost() {
  return cost;
 }

 @Override
 public String toString() {
  return serviceName + " (₹" + cost + ")";
 }
}

// Manages add-on services for reservations
class AddOnServiceManager {

 // Map<ReservationID, List of Services>
 private Map<String, List<AddOnService>> reservationServicesMap;

 public AddOnServiceManager() {
  reservationServicesMap = new HashMap<>();
 }

 // Add service to a reservation
 public void addService(String reservationId, AddOnService service) {
  reservationServicesMap
          .computeIfAbsent(reservationId, k -> new ArrayList<>())
          .add(service);

  System.out.println("Service added: " + service.getServiceName() +
          " for Reservation ID: " + reservationId);
 }

 // Get services for a reservation
 public List<AddOnService> getServices(String reservationId) {
  return reservationServicesMap.getOrDefault(reservationId, new ArrayList<>());
 }

 // Calculate total additional cost
 public double calculateTotalServiceCost(String reservationId) {
  List<AddOnService> services = getServices(reservationId);

  double total = 0;
  for (AddOnService service : services) {
   total += service.getCost();
  }
  return total;
 }

 // Display services
 public void displayServices(String reservationId) {
  List<AddOnService> services = getServices(reservationId);

  if (services.isEmpty()) {
   System.out.println("No add-on services for Reservation ID: " + reservationId);
   return;
  }

  System.out.println("\nAdd-On Services for Reservation ID: " + reservationId);
  for (AddOnService service : services) {
   System.out.println("- " + service);
  }

  System.out.println("Total Add-On Cost: ₹" +
          calculateTotalServiceCost(reservationId));
 }
}




public class BookMyStayApp {
 public static void main(String[] args) {
   AddOnServiceManager manager = new AddOnServiceManager();

  // Simulated reservation ID (from previous use case)
  String reservationId = "RES123";

  // Create add-on services
  AddOnService breakfast = new AddOnService("Breakfast", 500);
  AddOnService airportPickup = new AddOnService("Airport Pickup", 1200);
  AddOnService extraBed = new AddOnService("Extra Bed", 800);

  // Guest selects services
  manager.addService(reservationId, breakfast);
  manager.addService(reservationId, airportPickup);
  manager.addService(reservationId, extraBed);

  // Display selected services
  manager.displayServices(reservationId);
 }
}













