package org.example;

import org.example.elevator.Elevator;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Elevator Control System!");

        Elevator elevator = new Elevator();
        elevator.start();
    }
}