package pl.project.api.controller.exception;

public class CustomerIncorrectInputException extends RuntimeException{
    public CustomerIncorrectInputException(String message) {
        super(message);
    }

    public CustomerIncorrectInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
