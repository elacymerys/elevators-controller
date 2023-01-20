package org.example.request;

import org.example.elevator.Direction;

public record ExternalRequest(int sourceFloor, Direction direction) {}
