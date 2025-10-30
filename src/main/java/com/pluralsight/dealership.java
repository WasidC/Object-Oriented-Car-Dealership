package com.pluralsight;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
Represents a car dealership. Holds metadata (name, address, phone)
and a list of Vehicle objects (the inventory).
Provides search and modification methods for that inventory.
 */

public class dealership {
    private String name;
    private String address;
    private String phone;
    private List<vehicle> inventory; // holds all vehicles

    // ===== Constructors =====

    public dealership() {
        this.inventory = new ArrayList<>();
    }

    public dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<>();
    }

    // ===== Basic Getters / Setters =====

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public List<vehicle> getInventory() { return inventory; }

    // ===== Core methods =====

    /* Adds a vehicle to the dealership inventory. */

    public void addVehicle(vehicle v) {
        inventory.add(v);
    }

    /* Removes a vehicle by ID. Returns true if successful. */

    public boolean removeVehicleById(String id) {
        return inventory.removeIf(v -> v.getId().equalsIgnoreCase(id));
    }

    /* Returns a copy of the entire inventory list. */

    public List<vehicle> getAllVehicles() {
        return new ArrayList<>(inventory);
    }

    // ===== Search Methods =====

    public List<vehicle> findByPriceRange(double min, double max) {
        return inventory.stream()
                .filter(v -> v.getPrice() >= min && v.getPrice() <= max)
                .collect(Collectors.toList());
    }

    public List<vehicle> findByMakeModel(String make, String model) {
        String mLower = make == null ? "" : make.trim().toLowerCase();
        String moLower = model == null ? "" : model.trim().toLowerCase();
        return inventory.stream()
                .filter(v -> (mLower.isEmpty() || v.getMake().toLowerCase().contains(mLower)) &&
                        (moLower.isEmpty() || v.getModel().toLowerCase().contains(moLower)))
                .collect(Collectors.toList());
    }

    public List<vehicle> findByYearRange(int from, int to) {
        return inventory.stream()
                .filter(v -> v.getYear() >= from && v.getYear() <= to)
                .collect(Collectors.toList());
    }

    public List<vehicle> findByColor(String color) {
        String c = color == null ? "" : color.trim().toLowerCase();
        return inventory.stream()
                .filter(v -> v.getColor().toLowerCase().contains(c))
                .collect(Collectors.toList());
    }

    public List<vehicle> findByMileageRange(int min, int max) {
        return inventory.stream()
                .filter(v -> v.getMileage() >= min && v.getMileage() <= max)
                .collect(Collectors.toList());
    }

    public List<vehicle> findByType(String type) {
        String t = type == null ? "" : type.trim().toLowerCase();
        return inventory.stream()
                .filter(v -> v.getType().toLowerCase().contains(t))
                .collect(Collectors.toList());
    }
}
