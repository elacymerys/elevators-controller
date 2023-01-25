package org.example.request;

import org.example.elevator.Direction;

public record Request(int floor, Direction direction) implements Comparable<Request> {
    @Override
    public int compareTo(Request request) {
        return Integer.compare(this.floor(), request.floor());
    }
}
