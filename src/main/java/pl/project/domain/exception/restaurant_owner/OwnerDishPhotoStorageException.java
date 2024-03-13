package pl.project.domain.exception.restaurant_owner;

public class OwnerDishPhotoStorageException extends RuntimeException{
    public OwnerDishPhotoStorageException(String message) {
        super(message);
    }

    public OwnerDishPhotoStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
