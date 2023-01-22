package org.example.controller;

import org.example.elevator.Direction;
import org.example.elevator.Elevator;
import org.example.request.Request;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ElevatorSystemController {
    private final List<Elevator> elevators = new LinkedList<>();

    public ElevatorSystemController(int elevatorsNumber) {
        IntStream.range(0, elevatorsNumber).forEach(id -> elevators.add(new Elevator(id)));
    }

    public void start() {
        elevators.forEach(elevator -> new Thread(elevator).start());
    }

    public void pickup(int sourceFloor, Direction direction) {
        elevators.get(0).addRequest(new Request(sourceFloor));
    }

    public void update(int elevatorId, int currentFloor, int currentDestinationFloor) {
        elevators.get(elevatorId).updateStatus(currentFloor, currentDestinationFloor);
    }

    public void step() {}

    public List<List<Integer>> status() {
        return elevators.stream().map(Elevator::getStatus).collect(Collectors.toList());
    }
}
