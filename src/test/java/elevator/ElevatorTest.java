package elevator;

import org.example.elevator.Direction;
import org.example.elevator.Elevator;
import org.example.request.Request;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.List;
import java.util.stream.IntStream;

public class ElevatorTest {
    private final int elevatorId = 12;

    @Test
    public void getStatusElevatorInitStateTest() {
        Elevator elevator = new Elevator(elevatorId);
        List<Integer> elevatorStatus = elevator.getStatus();

        assertEquals(3, elevatorStatus.size());

        int id = elevatorStatus.get(0);
        int currentFloor = elevatorStatus.get(1);
        int destinationFloor = elevatorStatus.get(2);

        assertEquals(elevatorId, id);
        assertEquals(0, currentFloor);
        assertEquals(0, destinationFloor);
    }

    @Test
    public void getStatusOneRequestTest() {
        Elevator elevator = new Elevator(elevatorId);
        elevator.addRequest(new Request(5, Direction.UP));
        IntStream.range(0, 3).forEach(i -> elevator.move());

        List<Integer> elevatorStatus = elevator.getStatus();
        assertEquals(3, elevatorStatus.size());

        int id = elevatorStatus.get(0);
        int currentFloor = elevatorStatus.get(1);
        int destinationFloor = elevatorStatus.get(2);

        assertEquals(elevatorId, id);
        assertEquals(3, currentFloor);
        assertEquals(5, destinationFloor);
    }

    @Test
    public void getStatusMoreRequestsTest() {
        Elevator elevator = new Elevator(elevatorId);
        elevator.addRequest(new Request(5, Direction.UP));
        elevator.addRequest(new Request(3, Direction.DOWN));
        elevator.addRequest(new Request(8, Direction.DOWN));
        IntStream.range(0, 3).forEach(i -> elevator.move());

        List<Integer> elevatorStatus = elevator.getStatus();
        assertEquals(3, elevatorStatus.size());

        int id = elevatorStatus.get(0);
        int currentFloor = elevatorStatus.get(1);
        int destinationFloor = elevatorStatus.get(2);

        assertEquals(elevatorId, id);
        assertEquals(3, currentFloor);
        assertEquals(8, destinationFloor);
    }

    @Test
    public void updateStatusNoRequestsTest() {
        int currentFloorUpdated = 4;
        int currentDestinationFloorUpdated = 8;

        Elevator elevator = new Elevator(elevatorId);
        elevator.updateStatus(currentFloorUpdated, currentDestinationFloorUpdated);
        List<Integer> elevatorStatus = elevator.getStatus();

        int id = elevatorStatus.get(0);
        int currentFloor = elevatorStatus.get(1);
        int destinationFloor = elevatorStatus.get(2);

        assertEquals(elevatorId, id);
        assertEquals(currentFloorUpdated, currentFloor);
        assertEquals(currentDestinationFloorUpdated, destinationFloor);

        elevator.move();
        elevatorStatus = elevator.getStatus();

        id = elevatorStatus.get(0);
        currentFloor = elevatorStatus.get(1);
        destinationFloor = elevatorStatus.get(2);

        assertEquals(elevatorId, id);
        assertEquals(currentFloorUpdated+1, currentFloor);
        assertEquals(currentDestinationFloorUpdated, destinationFloor);
    }

    @Test
    public void updateStatusMoreRequestsTest() {
        int currentFloorUpdated = 4;
        int currentDestinationFloorUpdated = 8;

        Elevator elevator = new Elevator(elevatorId);
        elevator.addRequest(new Request(7, Direction.UP));
        elevator.move();

        elevator.updateStatus(currentFloorUpdated, currentDestinationFloorUpdated);
        List<Integer> elevatorStatus = elevator.getStatus();

        int id = elevatorStatus.get(0);
        int currentFloor = elevatorStatus.get(1);
        int destinationFloor = elevatorStatus.get(2);

        assertEquals(elevatorId, id);
        assertEquals(currentFloorUpdated, currentFloor);
        assertEquals(currentDestinationFloorUpdated, destinationFloor);

        elevator.move();
        elevatorStatus = elevator.getStatus();

        id = elevatorStatus.get(0);
        currentFloor = elevatorStatus.get(1);
        destinationFloor = elevatorStatus.get(2);

        assertEquals(elevatorId, id);
        assertEquals(currentFloorUpdated+1, currentFloor);
        assertEquals(currentDestinationFloorUpdated, destinationFloor);
    }

    @Test
    public void isIdleTest() {
        Elevator elevator = new Elevator(elevatorId);
        assertTrue(elevator.isIdle());

        elevator.addRequest(new Request(3, Direction.UP));
        IntStream.range(0, 2).forEach(i -> elevator.move());
        assertFalse(elevator.isIdle());

        elevator.move();
        assertTrue(elevator.isIdle());
    }

    @Test
    public void isBetweenTest() {
        Elevator elevator = new Elevator(elevatorId);
        elevator.addRequest(new Request(5, Direction.UP));
        elevator.move();

        assertTrue(elevator.isBetween(3));
        assertTrue(elevator.isBetween(1));
        assertTrue(elevator.isBetween(5));
        assertFalse(elevator.isBetween(0));
    }

    @Test
    public void getCurrentFloorDistanceTest() {
        Elevator elevator = new Elevator(elevatorId);
        elevator.addRequest(new Request(5, Direction.UP));
        elevator.move();

        assertEquals(3, elevator.getCurrentFloorDistance(4));
        assertEquals(0, elevator.getCurrentFloorDistance(1));
    }

    @Test
    public void getCurrentDestinationFloorDistanceTest() {
        Elevator elevator = new Elevator(elevatorId);
        elevator.addRequest(new Request(5, Direction.UP));
        elevator.move();

        assertEquals(4, elevator.getCurrentDestinationFloorDistance(1));
        assertEquals(0, elevator.getCurrentDestinationFloorDistance(5));
    }

    @Test
    public void getWaitingRequestsNumberTest() {
        Elevator elevator = new Elevator(elevatorId);
        assertEquals(0, elevator.getWaitingRequestsNumber());

        elevator.addRequest(new Request(5, Direction.UP));
        elevator.addRequest(new Request(2, Direction.DOWN));
        elevator.addRequest(new Request(4, Direction.UP));
        assertEquals(3, elevator.getWaitingRequestsNumber());

        elevator.move();
        assertEquals(2, elevator.getWaitingRequestsNumber());
    }
}
