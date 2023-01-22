package org.example.reader;

import org.example.elevator.Direction;

import java.util.Formatter;
import java.util.Objects;
import java.util.Scanner;

public class UserInputReader {
    private static final Scanner scanner = new Scanner(System.in);

    public static int readInt(String message, int minNumber, int maxNumber) throws InterruptedException {
        int number;

        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();
            if (Objects.equals(input, "q")) throw new InterruptedException();

            try {
                number = Integer.parseInt(input);
                validateInt(number, minNumber, maxNumber);
                return number;
            } catch (NumberFormatException e) {
                System.out.println("\"" + input + "\" is not a number!");
            } catch (IllegalArgumentException e) {
                System.out.printf(e.getMessage());
            }
        }
    }

    public static Direction readDirection(String message) throws InterruptedException {
        int number;

        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();
            if (Objects.equals(input, "q")) throw new InterruptedException();

            try {
                number = Integer.parseInt(input);
                validateInt(number, 1, Direction.values().length);
                return number == 1 ? Direction.UP : Direction.DOWN;
            } catch (NumberFormatException e) {
                System.out.println(input + " is not a number!");
            } catch (IllegalArgumentException e) {
                System.out.printf(e.getMessage());
            }
        }
    }

    private static void validateInt(int number, int minNumber, int maxNumber) throws IllegalArgumentException {
        if (number < minNumber || number > maxNumber) {
            String exceptionMessage = new Formatter().format("The number must be between %d and %d!%n", minNumber, maxNumber).toString();
            throw new IllegalArgumentException(exceptionMessage);
        }
    }
}
