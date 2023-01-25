package org.example.controller;

import org.example.building.Building;
import org.example.elevator.Direction;
import org.example.elevator.Elevator;
import org.example.reader.UserInputReader;
import org.example.request.Request;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ElevatorSystemController {
    private final Building building;
    private final List<Elevator> elevators = new LinkedList<>();

    public ElevatorSystemController(Building building) {
        this.building = building;
        IntStream.range(0, building.elevatorsNumber()).forEach(id -> {
            elevators.add(new Elevator(id));
            System.out.printf("Elevator with id=%d has started working%n", id);
        });
        System.out.println();
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
                System.out.println("See you!");
                return;
            }

            try {
                switch (actionNumber) {
                    case 1 -> {
                        System.out.println("Please provide:");
                        int sourceFloor = UserInputReader.readInt("\tsource floor: ", 0, building.floorsNumber());
                        Direction direction = sourceFloor == 0 ? Direction.UP : sourceFloor == building.floorsNumber() ? Direction.DOWN : UserInputReader.readDirection();
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
        // wybieramy windę, która jest bezczynna, tzn. nie ma żadnych requestów do przetworzenia
        Optional<Elevator> choosenElevator = elevators.stream().filter(Elevator::isIdle).findFirst();
        if (choosenElevator.isPresent()) {
            choosenElevator.get().addRequest(new Request(sourceFloor, direction));
            return;
        }

        // wybieramy windę z wybranym tym samym kierunkiem, gdzie jesteśmy na jej drodze i jest ona w najmniejszej odległości od nas
        choosenElevator = elevators.stream()
                .filter(elevator -> direction == elevator.getChosenDirection())
                .filter(elevator -> elevator.isBetween(sourceFloor))
                .min(Comparator.comparingInt(elevator -> elevator.getCurrentFloorDistance(sourceFloor)));
        if (choosenElevator.isPresent()) {
            choosenElevator.get().addRequest(new Request(sourceFloor, direction));
            return;
        }

        // wybieramy windę, która od destinationFloor ma do nas najbliżej
        choosenElevator = elevators.stream()
                .filter(elevator -> direction == elevator.getChosenDirection())
                .min(Comparator.comparingInt(elevator -> elevator.getCurrentDestinationFloorDistance(sourceFloor)));
        if (choosenElevator.isPresent()) {
            choosenElevator.get().addRequest(new Request(sourceFloor, direction));
            return;
        }

        // co w przypadku, gdy nie ma windy, która ma wybrany ten sam kierunek?
        // wybieramy windę 'najmniej zajętą', czy która ma najmniej requestów do wykonania
        choosenElevator = elevators.stream().min(Comparator.comparingInt(Elevator::getRequestsNumber));
        choosenElevator.ifPresent(elevator -> elevator.addRequest(new Request(sourceFloor, direction)));
    }

    public void step() {
        elevators.forEach(Elevator::move);
    }

    public void update(int elevatorId, int currentFloor, int currentDestinationFloor) {
        elevators.get(elevatorId).updateStatus(currentFloor, currentDestinationFloor);
    }

    public List<List<Integer>> status() {
        return elevators.stream().map(Elevator::getStatus).collect(Collectors.toList());
    }
}
