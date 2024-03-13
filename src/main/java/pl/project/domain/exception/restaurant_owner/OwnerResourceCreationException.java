package pl.project.domain.exception.restaurant_owner;

public class OwnerResourceCreationException extends RuntimeException{
    public OwnerResourceCreationException(String message) {
        super(message);
    }

    public OwnerResourceCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
