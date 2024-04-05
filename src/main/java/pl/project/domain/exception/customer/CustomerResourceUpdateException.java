package pl.project.domain.exception.customer;

public class CustomerResourceUpdateException extends RuntimeException{
    public CustomerResourceUpdateException(String message) {
        super(message);
    }

    public CustomerResourceUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
