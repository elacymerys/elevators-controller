package org.example.elevator;

import org.example.request.Request;

import java.util.ArrayList;
import java.util.List;

public class Elevator implements Runnable {
    private final int id;
    private int currentFloor = 0;
    private int currentDestinationFloor = 0;
    private volatile ArrayList<Request> requests = new ArrayList<>();

    private Thread thread;

    public Elevator(int id) {
        this.id = id;
    }

    public void run() {
        System.out.printf("Elevator with id=%d has started working%n", id);

        while (true) {
            if (!requests.isEmpty()) {
                // first-come, first-serve approach
                Request request = requests.remove(0);
                try {
                    processRequest(request);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    private void processRequest(Request request) throws InterruptedException {
        thread = new Thread(() -> {
            try {
                System.out.println("processRequest: " + request);
                reachFloor(request.floor());
            } catch (InterruptedException ignored) {
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ignored) {
        }
    }

    private void reachFloor(int floor) throws InterruptedException {
        currentDestinationFloor = floor;
        int startFloor = currentFloor;

        if (startFloor < floor) {
            for (int reachedFloor = startFloor + 1; reachedFloor <= floor; reachedFloor++) {
                Thread.sleep(1000);
                System.out.println("Elevator=" + id + "; Floor: " + reachedFloor);
                currentFloor = reachedFloor;
            }
        } else if (startFloor > floor) {
            for (int reachedFloor = startFloor - 1; reachedFloor >= floor; reachedFloor--) {
                Thread.sleep(1000);
                System.out.println("Elevator=" + id + "; Floor: " + reachedFloor);
                currentFloor = reachedFloor;
            }
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
        if (thread != null) thread.interrupt();
    }
}
