package com.pluralsight;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/*
 * Handles all user interaction.
 * Displays menu, reads input, calls dealership methods, and prints results.
 */

public class userinterface {
    private dealership dealership;
    private dealershipfilemanager fileManager;
    private Scanner scanner;

    public userinterface(String filename) {
        this.fileManager = new dealershipfilemanager(filename);
        this.scanner = new Scanner(System.in);
    }

    /* Entry point for the interactive console menu. */

    public void display() {
        init();
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Select an option: ");

            switch (choice) {
                case 1: processPriceRangeRequest(); break;
                case 2: processMakeModelRequest(); break;
                case 3: processYearRangeRequest(); break;
                case 4: processColorRequest(); break;
                case 5: processMileageRangeRequest(); break;
                case 6: processTypeRequest(); break;
                case 7: processAllVehiclesRequest(); break;
                case 8: processAddVehicleRequest(); break;
                case 9: processRemoveVehicleRequest(); break;
                case 99: running = false; System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid option."); break;
            }
            System.out.println();
        }
    }

    /* Loads dealership data using the FileManager. */

    private void init() {
        try {
            this.dealership = fileManager.getdealership();
            System.out.println("Loaded dealership: " + dealership.getName());
        } catch (IOException e) {
            System.err.println("Failed to load dealership file: " + e.getMessage());
            this.dealership = new dealership("Unknown", "", "");
        }
    }

    /* Prints main menu options. */

    private void printMenu() {
        System.out.println("=== DEALERSHIP MENU ===");
        System.out.println("1  - Find vehicles within a price range");
        System.out.println("2  - Find vehicles by make / model");
        System.out.println("3  - Find vehicles by year range");
        System.out.println("4  - Find vehicles by color");
        System.out.println("5  - Find vehicles by mileage range");
        System.out.println("6  - Find vehicles by type (car, truck, SUV, van)");
        System.out.println("7  - List ALL vehicles");
        System.out.println("8  - Add a vehicle");
        System.out.println("9  - Remove a vehicle");
        System.out.println("99 - Quit");
    }

    /* Helper to print a list of vehicles. */

    private void displayVehicles(List<vehicle> vehicles) {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
            return;
        }
        System.out.println("Found " + vehicles.size() + " vehicle(s):");
        for (vehicle v : vehicles) {
            System.out.println(v);
        }
    }

    // ===== Menu Option Handlers =====

    private void processAllVehiclesRequest() {
        displayVehicles(dealership.getAllVehicles());
    }

    private void processPriceRangeRequest() {
        double min = readDouble("Enter min price: ");
        double max = readDouble("Enter max price: ");
        displayVehicles(dealership.findByPriceRange(min, max));
    }

    private void processMakeModelRequest() {
        String make = readString("Enter make (or leave blank): ");
        String model = readString("Enter model (or leave blank): ");
        displayVehicles(dealership.findByMakeModel(make, model));
    }

    private void processYearRangeRequest() {
        int from = readInt("Enter start year: ");
        int to = readInt("Enter end year: ");
        displayVehicles(dealership.findByYearRange(from, to));
    }

    private void processColorRequest() {
        String color = readString("Enter color: ");
        displayVehicles(dealership.findByColor(color));
    }

    private void processMileageRangeRequest() {
        int min = readInt("Enter min mileage: ");
        int max = readInt("Enter max mileage: ");
        displayVehicles(dealership.findByMileageRange(min, max));
    }

    private void processTypeRequest() {
        String type = readString("Enter type (car/truck/SUV/van): ");
        displayVehicles(dealership.findByType(type));
    }

    /* Allows user to add a new vehicle, then saves the updated file. */

    private void processAddVehicleRequest() {
        System.out.println("--- Add Vehicle ---");
        String id = readString("ID: ");
        int year = readInt("Year: ");
        String make = readString("Make: ");
        String model = readString("Model: ");
        String type = readString("Type: ");
        String color = readString("Color: ");
        int mileage = readInt("Mileage: ");
        double price = readDouble("Price: ");

        vehicle v = new vehicle(id, year, make, model, type, color, mileage, price);
        dealership.addVehicle(v);

        try {
            fileManager.savedealership(dealership);
            System.out.println("Vehicle added and saved!");
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        }
    }

    /* Allows user to remove a vehicle by ID, then saves the updated file. */

    private void processRemoveVehicleRequest() {
        String id = readString("Enter ID to remove: ");
        boolean removed = dealership.removeVehicleById(id);
        if (removed) {
            try {
                fileManager.savedealership(dealership);
                System.out.println("Vehicle removed.");
            } catch (IOException e) {
                System.err.println("Save failed: " + e.getMessage());
            }
        } else {
            System.out.println("No vehicle found with that ID.");
        }
    }

    // ===== Input helpers =====

    private int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid integer.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
