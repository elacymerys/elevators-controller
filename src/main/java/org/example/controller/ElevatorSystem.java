package org.example.controller;

import org.example.elevator.Direction;

import java.util.List;

public interface ElevatorSystem {
    void pickup(int sourceFloor, Direction direction);
    void update(int elevatorId, int currentFloor, int currentDestinationFloor);
    void step();
    List<List<Integer>> status();
}
