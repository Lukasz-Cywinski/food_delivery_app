package pl.project.domain.exception;

public class EntityCreationException extends RuntimeException{
    public EntityCreationException(String message) {
        super(message);
    }
}
