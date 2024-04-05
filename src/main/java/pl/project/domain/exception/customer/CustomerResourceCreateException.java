package pl.project.domain.exception.customer;

public class CustomerResourceCreateException extends RuntimeException{
    public CustomerResourceCreateException(String message) {
        super(message);
    }

    public CustomerResourceCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
