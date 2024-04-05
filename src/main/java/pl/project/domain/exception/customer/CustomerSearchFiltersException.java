package pl.project.domain.exception.customer;

public class CustomerSearchFiltersException extends RuntimeException{
    public CustomerSearchFiltersException(String message) {
        super(message);
    }

    public CustomerSearchFiltersException(String message, Throwable cause) {
        super(message, cause);
    }
}
