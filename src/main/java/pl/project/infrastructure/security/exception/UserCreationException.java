package pl.project.infrastructure.security.exception;

public class UserCreationException extends RuntimeException{
    public UserCreationException(String message) {
        super(message);
    }
}
