package org.example.reader;

import org.example.building.Building;

public class ArgsReader {

    public static Building read(String[] args) throws IllegalArgumentException {
        if (args.length != 2) throw new IllegalArgumentException("Wrong number of arguments");

        try {
            int floorsNumber = Integer.parseInt(args[0]);
            int elevatorsNumber = Integer.parseInt(args[1]);
            return new Building(floorsNumber, elevatorsNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Wrong arguments format");
        }
    }
}
