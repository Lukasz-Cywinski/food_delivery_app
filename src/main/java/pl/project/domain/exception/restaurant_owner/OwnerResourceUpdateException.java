package pl.project.domain.exception.restaurant_owner;

public class OwnerResourceUpdateException extends RuntimeException{
    public OwnerResourceUpdateException(String message) {
        super(message);
    }

    public OwnerResourceUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
