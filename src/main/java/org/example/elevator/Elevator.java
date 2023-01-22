package org.example.elevator;

import org.example.request.Request;

import java.util.ArrayList;
import java.util.List;

public class Elevator {
    private final int id;
    private int currentFloor = 0;
    private int currentDestinationFloor = 0;
    private State state = State.STOPPED;
    private final ArrayList<Request> requests = new ArrayList<>();

    public Elevator(int id) {
        this.id = id;
    }

    public void move() {
        if (requests.isEmpty()) return;

        if (state == State.STOPPED) {
            Request request = requests.get(0);
            state = State.MOVING;
            currentDestinationFloor = request.floor();
        }

        if (currentFloor < currentDestinationFloor) {
            currentFloor++;
        } else if (currentFloor > currentDestinationFloor) {
            currentFloor--;
        }

        if (currentFloor == currentDestinationFloor) {
            state = State.STOPPED;
            requests.remove(0);
        }
    }

    public void addRequest(Request request) {
        requests.add(request);
    }

    public List<Integer> getStatus() {
        return List.of(id, currentFloor, currentDestinationFloor);
    }

    public void updateStatus(int currentFloor, int currentDestinationFloor) {
        this.currentFloor = currentFloor;
        this.currentDestinationFloor = currentDestinationFloor;
        requests.add(0, new Request(currentDestinationFloor));
        state = State.MOVING;
    }
}
