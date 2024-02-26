package pl.project.domain.exception;

public class NoAvailableDeliveryMan extends RuntimeException{
    public NoAvailableDeliveryMan(String message) {
        super(message);
    }
}
