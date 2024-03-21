package pl.project.domain.exception.storage;

public class DishPhotoStorageException extends RuntimeException{

    public DishPhotoStorageException(String message) {
        super(message);
    }
    public DishPhotoStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
