package org.visionFive.exception;

public class NoTaskAvailableForUserException extends TodoListException {
    public NoTaskAvailableForUserException(String message) {
        super(message);
    }
}
