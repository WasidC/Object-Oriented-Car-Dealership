package com.pluralsight;

/*
 Main class for the Car Dealership application.
 Creates the UserInterface and starts the menu loop.
 */

public class main {
    public static void main(String[] args) {
        // Allow optional command-line argument for custom file name

        String filename = args.length > 0 ? args[0] : "inventory.csv";

        userinterface ui = new userinterface(filename);
        ui.display();
    }
}
