package org.example.elevator;

import org.example.request.Request;

import java.util.List;
import java.util.TreeSet;

public class Elevator {
    private final int id;
    private int currentFloor = 0;
    private int currentDestinationFloor = 0;
    private State currentState = State.STOPPED;
    private Direction chosenDirection = Direction.UP;
    private TreeSet<Request> currentRequests = new TreeSet<>();
    private final TreeSet<Request> pendingUpRequests = new TreeSet<>();
    private final TreeSet<Request> pendingDownRequests = new TreeSet<>();

    public Elevator(int id) {
        this.id = id;
    }

    public Direction getChosenDirection() {
        return chosenDirection;
    }

    public void move() {
        if (currentRequests.isEmpty()) {
            addPendingRequestsToCurrentRequests();
            if (currentRequests.isEmpty()) return;
        }

        if (currentState == State.STOPPED) {
            Request request = chosenDirection == Direction.UP ? currentRequests.pollFirst() : currentRequests.pollLast();
            currentState = State.MOVING;
            chosenDirection = request.direction();
            currentDestinationFloor = request.floor();
        }

        if (currentFloor < currentDestinationFloor) {
            currentFloor++;
        } else if (currentFloor > currentDestinationFloor) {
            currentFloor--;
        }

        if (currentFloor == currentDestinationFloor) {
            currentState = State.STOPPED;
        }
    }

    private void addPendingRequestsToCurrentRequests() {
        if (chosenDirection == Direction.UP) {
            if (!pendingDownRequests.isEmpty()) {
                currentRequests = pendingDownRequests;
                pendingDownRequests.clear();
            } else if (!pendingUpRequests.isEmpty()) {
                currentRequests = pendingUpRequests;
                pendingUpRequests.clear();
            }
        } else {
            if (!pendingUpRequests.isEmpty()) {
                currentRequests = pendingUpRequests;
                pendingUpRequests.clear();
            } else if (!pendingDownRequests.isEmpty()) {
                currentRequests = pendingDownRequests;
                pendingDownRequests.clear();
            }
        }
    }

    public void addRequest(Request request) {
        if (request.direction() == Direction.UP) {
            pendingUpRequests.add(request);
        } else {
            pendingDownRequests.add(request);
        }
    }

    public List<Integer> getStatus() {
        return List.of(id, currentFloor, currentDestinationFloor);
    }

    public void updateStatus(int currentFloor, int currentDestinationFloor) {
        this.currentFloor = currentFloor;
        this.currentDestinationFloor = currentDestinationFloor;
        if (currentFloor == currentDestinationFloor) return;
        chosenDirection = currentDestinationFloor > currentFloor ? Direction.UP : Direction.DOWN;
        currentRequests.add(new Request(currentDestinationFloor, chosenDirection));
        currentState = State.MOVING;
    }

    public boolean isIdle() {
        return currentRequests.isEmpty() && pendingUpRequests.isEmpty() && pendingDownRequests.isEmpty();
    }

    public boolean isBetween(int floor) {
        return floor >= currentDestinationFloor && floor <= currentFloor;
    }

    public int getCurrentFloorDistance(int floor) {
        return Math.abs(floor - currentFloor);
    }

    public int getCurrentDestinationFloorDistance(int floor) {
        return Math.abs(floor - currentDestinationFloor);
    }

    public int getRequestsNumber() {
        return currentRequests.size();
    }
}
