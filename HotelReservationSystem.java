import java.util.*;

class Room {
    int roomNumber;
    String category; // Standard, Deluxe, Suite
    boolean isAvailable;

    Room(int roomNumber, String category) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isAvailable = true;
    }

    public String toString() {
        return "Room " + roomNumber + " (" + category + ") - " + (isAvailable ? "Available" : "Booked");
    }
}

class Booking {
    static int counter = 1;
    int bookingId;
    String customerName;
    Room room;
    double paymentAmount;

    Booking(String customerName, Room room, double paymentAmount) {
        this.bookingId = counter++;
        this.customerName = customerName;
        this.room = room;
        this.paymentAmount = paymentAmount;
    }

    public String toString() {
        return "Booking ID: " + bookingId +
               "\nCustomer: " + customerName +
               "\nRoom: " + room.roomNumber +
               "\nCategory: " + room.category +
               "\nAmount Paid: $" + paymentAmount;
    }
}

class HotelSystem {
    ArrayList<Room> rooms = new ArrayList<>();
    ArrayList<Booking> bookings = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    HotelSystem() {
        // Sample rooms
        for (int i = 1; i <= 5; i++) rooms.add(new Room(i, "Standard"));
        for (int i = 6; i <= 8; i++) rooms.add(new Room(i, "Deluxe"));
        for (int i = 9; i <= 10; i++) rooms.add(new Room(i, "Suite"));
    }

    void showAvailableRooms(String category) {
        System.out.println("\nAvailable " + category + " Rooms:");
        for (Room room : rooms) {
            if (room.category.equalsIgnoreCase(category) && room.isAvailable) {
                System.out.println("Room Number: " + room.roomNumber);
            }
        }
    }

    void makeBooking() {
        System.out.print("\nEnter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter room category (Standard/Deluxe/Suite): ");
        String category = scanner.nextLine();

        Room selectedRoom = null;
        for (Room room : rooms) {
            if (room.category.equalsIgnoreCase(category) && room.isAvailable) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("❌ No rooms available in this category.");
            return;
        }

        double price = switch (category.toLowerCase()) {
            case "standard" -> 100;
            case "deluxe" -> 200;
            case "suite" -> 350;
            default -> 0;
        };

        System.out.println("Room found: " + selectedRoom.roomNumber + " | Price: $" + price);
        System.out.print("Proceed with payment? (yes/no): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            selectedRoom.isAvailable = false;
            Booking booking = new Booking(name, selectedRoom, price);
            bookings.add(booking);
            System.out.println("✅ Booking Successful!");
            System.out.println(booking);
        } else {
            System.out.println("Booking cancelled.");
        }
    }

    void cancelBooking() {
        System.out.print("\nEnter Booking ID to cancel: ");
        int bookingId = scanner.nextInt();
        scanner.nextLine(); // clear buffer

        Booking toRemove = null;
        for (Booking booking : bookings) {
            if (booking.bookingId == bookingId) {
                toRemove = booking;
                break;
            }
        }

        if (toRemove != null) {
            toRemove.room.isAvailable = true;
            bookings.remove(toRemove);
            System.out.println("✅ Booking cancelled successfully.");
        } else {
            System.out.println("❌ Booking ID not found.");
        }
    }

    void viewAllBookings() {
        System.out.println("\n📋 All Bookings:");
        if (bookings.isEmpty()) {
            System.out.println("No bookings made yet.");
            return;
        }

        for (Booking booking : bookings) {
            System.out.println("------------------------");
            System.out.println(booking);
        }
    }

    void start() {
        while (true) {
            System.out.println("\n=== Hotel Reservation System ===");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Make a Booking");
            System.out.println("3. Cancel a Booking");
            System.out.println("4. View All Bookings");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter room category (Standard/Deluxe/Suite): ");
                    String category = scanner.nextLine();
                    showAvailableRooms(category);
                }
                case 2 -> makeBooking();
                case 3 -> cancelBooking();
                case 4 -> viewAllBookings();
                case 5 -> {
                    System.out.println("Exiting... Thank you!");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) {
        HotelSystem system = new HotelSystem();
        system.start();
    }
}
