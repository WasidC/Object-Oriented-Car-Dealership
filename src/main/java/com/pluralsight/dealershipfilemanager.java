package com.pluralsight;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/*
 Handles reading and writing of dealership inventory data to a pipe-delimited file.
 Line 1: dealership info (name|address|phone)
 Lines 2+: vehicle info (1 per line)
 */

public class dealershipfilemanager {
    private String filename;

    public dealershipfilemanager(String filename) {
        this.filename = filename;
    }

    /*
     Reads the file and returns a populated Dealership object.
     If file doesn't exist, a default one is created.
     */

    public dealership getdealership() throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            createDefaultFile(file);
        }

        List<String> lines = Files.readAllLines(Paths.get(filename));
        if (lines.isEmpty()) {
            throw new IOException("File is empty: " + filename);
        }

        // Parse first line: dealership info

        String dealerLine = lines.get(0);
        String[] parts = dealerLine.split("\\|");
        String name = parts.length > 0 ? parts[0].trim() : "";
        String address = parts.length > 1 ? parts[1].trim() : "";
        String phone = parts.length > 2 ? parts[2].trim() : "";

        dealership dealership = new dealership(name, address, phone);

        // Parse remaining lines as vehicles
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;
            try {
                vehicle v = vehicle.fromFileString(line);
                dealership.addVehicle(v);
            } catch (Exception ex) {
                System.err.println("Warning: invalid vehicle line " + (i + 1) + ": " + ex.getMessage());
            }
        }
        return dealership;
    }

    /**
     * Saves the dealership (and its vehicles) back to the file.
     * Overwrites existing file.
     */
    public void savedealership(dealership dealership) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, false))) {
            // Header line
            bw.write(String.join("|",
                    safe(dealership.getName()),
                    safe(dealership.getAddress()),
                    safe(dealership.getPhone())));
            bw.newLine();

            // Each vehicle line

            for (vehicle v : dealership.getInventory()) {
                bw.write(v.toFileString());
                bw.newLine();
            }
        }
    }

    /* Creates a starter file if missing. */

    private void createDefaultFile(File file) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("D & B Used Cars|111 Old Benbrook Rd|817-555-5555");
            bw.newLine();
            bw.write("10112|1993|Ford|Explorer|SUV|Red|525123|995.00");
            bw.newLine();
            bw.write("37846|2001|Ford|Ranger|truck|Yellow|172544|1995.00");
            bw.newLine();
            bw.write("44901|2012|Honda|Civic|car|Gray|103221|6995.00");
            bw.newLine();
        }
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }
}
