package com.pluralsight;

import java.text.DecimalFormat;

/*
 Represents a single vehicle in the dealership inventory.
 Each vehicle has identifying info (ID, make, model, etc.) and details like mileage and price.
 */

public class vehicle {
    // ===== Fields =====

    private String id;
    private int year;
    private String make;
    private String model;
    private String type;   // e.g. car, truck, SUV, van
    private String color;
    private int mileage;
    private double price;

    // ===== Constructors =====

    public vehicle() { }

    public vehicle(String id, int year, String make, String model, String type, String color, int mileage, double price) {
        this.id = id;
        this.year = year;
        this.make = make;
        this.model = model;
        this.type = type;
        this.color = color;
        this.mileage = mileage;
        this.price = price;
    }

    // ===== Getters and Setters =====
    // These provide controlled access to private fields.

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public int getMileage() { return mileage; }
    public void setMileage(int mileage) { this.mileage = mileage; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    // ===== Methods =====

    /*
      Returns a nicely formatted string for console display.
     */

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return String.format("%s | %d %s %s | %s | %s | %d miles | $%s",
                id, year, make, model, type, color, mileage, df.format(price));
    }

    /*
      Converts a vehicle into a pipe-delimited string to save into the file.
     */

    public String toFileString() {
        return String.join("|",
                id,
                String.valueOf(year),
                make,
                model,
                type,
                color,
                String.valueOf(mileage),
                String.format("%.2f", price)
        );
    }

    /*
      Creates a Vehicle object from one line of the pipe-delimited file.
     */

    public static vehicle fromFileString(String line) throws NumberFormatException {
        String[] parts = line.split("\\|");
        if (parts.length != 8) {
            throw new IllegalArgumentException("Invalid vehicle line: " + line);
        }
        // Parse each field
        String id = parts[0].trim();
        int year = Integer.parseInt(parts[1].trim());
        String make = parts[2].trim();
        String model = parts[3].trim();
        String type = parts[4].trim();
        String color = parts[5].trim();
        int mileage = Integer.parseInt(parts[6].trim());
        double price = Double.parseDouble(parts[7].trim());
        return new vehicle(id, year, make, model, type, color, mileage, price);
    }
}

