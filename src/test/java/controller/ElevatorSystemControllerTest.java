package controller;

import org.example.building.Building;
import org.example.controller.ElevatorSystemController;
import org.example.elevator.Direction;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.IntStream;

public class ElevatorSystemControllerTest {
    private final int floorsNumber = 8;
    private final int elevatorsNumber = 4;

    @Test
    public void statusInitTest() {
        ElevatorSystemController controller = new ElevatorSystemController(
                new Building(floorsNumber, elevatorsNumber)
        );

        List<List<Integer>> elevatorStatuses = controller.status();
        Assert.assertEquals(elevatorsNumber, elevatorStatuses.size());

        IntStream.range(0, elevatorsNumber).forEach(elevatorId -> {
            List<Integer> elevatorStatus = elevatorStatuses.get(elevatorId);

            int id = elevatorStatus.get(0);
            int currentFloor = elevatorStatus.get(1);
            int destinationFloor = elevatorStatus.get(2);

            Assert.assertEquals(elevatorId, id);
            Assert.assertEquals(currentFloor, 0);
            Assert.assertEquals(destinationFloor, 0);
        });
    }

    @Test
    public void statusUpdateTest() {
        ElevatorSystemController controller = new ElevatorSystemController(
                new Building(floorsNumber, elevatorsNumber)
        );

        IntStream.range(0, elevatorsNumber).forEach(elevatorId -> controller.update(elevatorId, 2, 4));

        List<List<Integer>> elevatorStatuses = controller.status();
        Assert.assertEquals(elevatorsNumber, elevatorStatuses.size());

        IntStream.range(0, elevatorsNumber).forEach(elevatorId -> {
            List<Integer> elevatorStatus = elevatorStatuses.get(elevatorId);

            int id = elevatorStatus.get(0);
            int currentFloor = elevatorStatus.get(1);
            int destinationFloor = elevatorStatus.get(2);

            Assert.assertEquals(elevatorId, id);
            Assert.assertEquals(currentFloor, 2);
            Assert.assertEquals(destinationFloor, 4);
        });
    }

    @Test
    public void stepTest() {
        ElevatorSystemController controller = new ElevatorSystemController(
                new Building(floorsNumber, elevatorsNumber)
        );

        IntStream.range(0, elevatorsNumber).forEach(i -> controller.pickup(3, Direction.UP));
        controller.step();

        List<List<Integer>> elevatorStatuses = controller.status();
        Assert.assertEquals(elevatorsNumber, elevatorStatuses.size());

        IntStream.range(0, elevatorsNumber).forEach(elevatorId -> {
            List<Integer> elevatorStatus = elevatorStatuses.get(elevatorId);

            int id = elevatorStatus.get(0);
            int currentFloor = elevatorStatus.get(1);
            int destinationFloor = elevatorStatus.get(2);

            Assert.assertEquals(elevatorId, id);
            Assert.assertEquals(currentFloor, 1);
            Assert.assertEquals(destinationFloor, 3);
        });
    }
}
