package pl.project.api.controller.exception;

public class RegistrationIncorrectInputException extends RuntimeException{
    public RegistrationIncorrectInputException(String message) {
        super(message);
    }

    public RegistrationIncorrectInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
