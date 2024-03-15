package pl.project.domain.exception.restaurant_owner;

public class OwnerResourceCreateException extends RuntimeException{
    public OwnerResourceCreateException(String message) {
        super(message);
    }

    public OwnerResourceCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
