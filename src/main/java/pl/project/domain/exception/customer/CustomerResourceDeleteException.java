package pl.project.domain.exception.customer;

public class CustomerResourceDeleteException extends RuntimeException{
    public CustomerResourceDeleteException(String message) {
        super(message);
    }

    public CustomerResourceDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
