package org.example;

import org.example.building.Building;
import org.example.controller.ElevatorSystemController;
import org.example.reader.ArgsReader;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Elevator Control System!");

        Building building = ArgsReader.read(args);

        ElevatorSystemController controller = new ElevatorSystemController(building);
        controller.start();

//        controller.pickup(3, Direction.UP);
//
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println(controller.status());
//
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        controller.pickup(6, Direction.UP);
//
//        System.out.println(controller.status());   // [0, 2, 3]
//
//        controller.update(1, 6, 2);
//        controller.update(0, 6, 2);
//        System.out.println(controller.status());
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println(controller.status());
    }
}