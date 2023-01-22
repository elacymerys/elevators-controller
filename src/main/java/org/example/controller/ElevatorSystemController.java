package org.example.controller;

import org.example.building.Building;
import org.example.elevator.Direction;
import org.example.elevator.Elevator;
import org.example.reader.UserInputReader;
import org.example.request.Request;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ElevatorSystemController {
    private final Building building;
    private final List<Elevator> elevators = new LinkedList<>();

    public ElevatorSystemController(Building building) {
        this.building = building;
        IntStream.range(0, building.elevatorsNumber()).forEach(id -> elevators.add(new Elevator(id)));
    }

    public void start() {

        while (true) {
            int actionNumber;

            try {
                actionNumber = UserInputReader.readInt(
                        "Actions:\n\t1. pickup\n\t2. update\n\t3. step\n\t4. status\n\t5. EXIT\nChoose action number: ",
                        1,
                        5
                );
            } catch (InterruptedException e) {
                System.out.println("Bye bye!");
                return;
            }

            try {
                switch (actionNumber) {
                    case 1 -> {
                        System.out.println("Please provide:");
                        int sourceFloor = UserInputReader.readInt("\tsource floor: ", 0, building.floorsNumber());
                        Direction direction = UserInputReader.readDirection("\tdirections:\n\t\t1. UP\n\t\t2. DOWN\n\tChoose direction: ");
                        pickup(sourceFloor, direction);
                    }
                    case 2 -> {
                        System.out.println("Please provide:");
                        int elevatorId = UserInputReader.readInt("\televator id: ", 0, building.elevatorsNumber() - 1);
                        int sourceFloor = UserInputReader.readInt("\tsource floor: ", 0, building.floorsNumber());
                        int destinationFloor = UserInputReader.readInt("\tdestination floor: ", 0, building.floorsNumber());
                        update(elevatorId, sourceFloor, destinationFloor);
                    }
                    case 3 -> step();
                    case 4 -> System.out.println(status());
                    case 5 -> {
                        System.out.println("See you!");
                        return;
                    }
                }
            } catch (InterruptedException ignored) {
            }
        }
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
