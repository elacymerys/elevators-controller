package org.example.elevator;

import org.example.request.Request;

import java.util.ArrayList;
import java.util.List;

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

    public void addRequest(Request request) {
        requests.add(request);
    }

    private void processRequest(Request request) {
        int sourceFloor = request.externalRequest().sourceFloor();
        List<Integer> destinationFloors = request.internalRequest().destinationFloors();

        // first go to external request source floor
        reachFloor(sourceFloor);
        // then go to internal request destination floor
        for (int destinationFloor: destinationFloors.stream().distinct().toList()) reachFloor(destinationFloor);
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
