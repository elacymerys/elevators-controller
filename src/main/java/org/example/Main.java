package org.example;

import org.example.elevator.Direction;
import org.example.elevator.Elevator;
import org.example.request.ExternalRequest;
import org.example.request.InternalRequest;
import org.example.request.Request;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Elevator Control System!");

        Elevator elevator = new Elevator();
        Thread thread = new Thread(elevator);
        thread.start();

        ExternalRequest er1 = new ExternalRequest(3, Direction.DOWN);
        InternalRequest ir1 = new InternalRequest(List.of(0));
        Request r1 = new Request(er1, ir1);

        ExternalRequest er2 = new ExternalRequest(0, Direction.UP);
        InternalRequest ir2 = new InternalRequest(List.of(6, 3, 2, 3, 4, 6));
        Request r2 = new Request(er2, ir2);

        elevator.addRequest(r1);
        elevator.addRequest(r2);
    }
}