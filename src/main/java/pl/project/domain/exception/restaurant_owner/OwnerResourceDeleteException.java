package pl.project.domain.exception.restaurant_owner;

public class OwnerResourceDeleteException extends RuntimeException{
    public OwnerResourceDeleteException(String message) {
        super(message);
    }

    public OwnerResourceDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
