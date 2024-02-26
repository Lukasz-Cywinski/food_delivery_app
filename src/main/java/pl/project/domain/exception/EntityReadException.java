package pl.project.domain.exception;

public class EntityReadException extends RuntimeException{
    public EntityReadException(String message) {
        super(message);
    }
}
