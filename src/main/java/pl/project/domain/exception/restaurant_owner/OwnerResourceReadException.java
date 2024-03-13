package pl.project.domain.exception.restaurant_owner;

public class OwnerResourceReadException extends RuntimeException{
    public OwnerResourceReadException(String message) {
        super(message);
    }

    public OwnerResourceReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
