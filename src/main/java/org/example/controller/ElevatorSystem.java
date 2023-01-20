package org.example.controller;

import java.util.List;

public interface ElevatorSystem {
    void pickup(int sourceFloor, int direction);
    void update(int elevatorId, int sourceFloor, int destinationFloor);
    void step();
    List<List<Integer>> status();
}
