package pl.project.domain.exception.customer;

public class NoAvailableDeliveryManException extends RuntimeException{
    public NoAvailableDeliveryManException(String message) {
        super(message);
    }

    public NoAvailableDeliveryManException(String message, Throwable cause) {
        super(message, cause);
    }
}
