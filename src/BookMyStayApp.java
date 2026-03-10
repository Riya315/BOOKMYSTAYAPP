import java.util.*;

abstract class Room {
 private String roomType;
 private int numberOfBeds;
 private double pricePerNight;

 // Constructor
 public Room(String roomType, int numberOfBeds, double pricePerNight) {
  this.roomType = roomType;
  this.numberOfBeds = numberOfBeds;
  this.pricePerNight = pricePerNight;
 }

 // Getter methods
 public String getRoomType() {
  return roomType;
 }

 public int getNumberOfBeds() {
  return numberOfBeds;
 }

 public double getPricePerNight() {
  return pricePerNight;
 }

 // Abstract method to display room details
 public abstract void displayRoomDetails();
}

// Concrete class for Single Room
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

// Concrete class for Double Room
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

// Concrete class for Suite Room
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


public class BookMyStayApp {
 public static void main(String[] args) {
  System.out.println("======================================");
  System.out.println("   Book My Stay - Version 2.1         ");
  System.out.println("   Basic Room Types & Availability    ");
  System.out.println("======================================");

  // Initialize room objects
  Room singleRoom = new SingleRoom();
  Room doubleRoom = new DoubleRoom();
  Room suiteRoom = new SuiteRoom();

  // Static availability variables
  int singleRoomAvailability = 5;
  int doubleRoomAvailability = 3;
  int suiteRoomAvailability = 2;

  // Display room details and availability
  System.out.println("\n--- Room Details ---");
  singleRoom.displayRoomDetails();
  System.out.println("Available: " + singleRoomAvailability + "\n");

  doubleRoom.displayRoomDetails();
  System.out.println("Available: " + doubleRoomAvailability + "\n");

  suiteRoom.displayRoomDetails();
  System.out.println("Available: " + suiteRoomAvailability + "\n");

  System.out.println("======================================");
  System.out.println("   End of Use Case 2 Demonstration     ");
  System.out.println("======================================");


 }


}






