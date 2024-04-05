package pl.project.domain.exception.customer;

public class CustomerResourceReadException extends RuntimeException{
    public CustomerResourceReadException(String message) {
        super(message);
    }

    public CustomerResourceReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
