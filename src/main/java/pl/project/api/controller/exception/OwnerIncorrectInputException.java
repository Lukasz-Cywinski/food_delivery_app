package pl.project.api.controller.exception;

public class OwnerIncorrectInputException extends RuntimeException{
    public OwnerIncorrectInputException(String message) {
        super(message);
    }

    public OwnerIncorrectInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
