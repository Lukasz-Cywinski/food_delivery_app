package pl.project.domain.exception.restaurant_owner;

public class OwnerResourceModificationException extends RuntimeException{
    public OwnerResourceModificationException(String message) {
        super(message);
    }

    public OwnerResourceModificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
