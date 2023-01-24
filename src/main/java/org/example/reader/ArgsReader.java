package org.example.reader;

import org.example.building.Building;

public class ArgsReader {

    public static Building read(String[] args) throws IllegalArgumentException {
        if (args.length != 2) throw new IllegalArgumentException("Wrong number of arguments");

        try {
            int floorsNumber = Integer.parseInt(args[0]);
            if (floorsNumber < 1 || floorsNumber > 10) {
                throw new IllegalArgumentException("Floors number must be between 1 and 10");
            }
            int elevatorsNumber = Integer.parseInt(args[1]);
            if (elevatorsNumber < 1 || elevatorsNumber > 16) {
                throw new IllegalArgumentException("Elevators number must be between 1 and 16");
            }
            return new Building(floorsNumber, elevatorsNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Wrong arguments format");
        }
    }
}
