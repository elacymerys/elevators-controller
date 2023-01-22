package org.example;

import org.example.building.Building;
import org.example.controller.ElevatorSystemController;
import org.example.reader.ArgsReader;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Elevator Control System!\n");

        Building building = ArgsReader.read(args);

        ElevatorSystemController controller = new ElevatorSystemController(building);
        controller.start();
    }
}