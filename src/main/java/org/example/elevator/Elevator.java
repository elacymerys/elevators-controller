package org.example.elevator;

import org.example.request.Request;

import java.util.ArrayList;

public class Elevator implements Runnable {
    private int currentFloor = 0;
    private final ArrayList<Request> requests = new ArrayList<>();

    public void run() {
        System.out.println("Elevator has started working");

        while (true) {
            if (!requests.isEmpty()) {
                // first-come, first-serve approach
                Request request = requests.remove(0);
                processRequest(request);
            }
        }
    }

    private void processRequest(Request request) {
        int sourceFloor = request.externalRequest().sourceFloor();
        int destinationFloor = request.internalRequest().destinationFloor();

        System.out.println("Floor: " + currentFloor);
        // first go to external request source floor
        reachFloor(sourceFloor);
        // then go to internal request destination floor
        reachFloor(destinationFloor);
    }

    private void reachFloor(int floor) {
        int startFloor = currentFloor;

        if (startFloor < floor) {
            for (int reachedFloor = startFloor + 1; reachedFloor <= floor; reachedFloor++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Floor: " + reachedFloor);
                currentFloor = reachedFloor;
            }
        } else if (startFloor > floor) {
            for (int reachedFloor = startFloor - 1; reachedFloor >= floor; reachedFloor--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Floor: " + reachedFloor);
                currentFloor = reachedFloor;
            }
        }
    }
}
