package pl.project.business.dao;

import pl.project.domain.model.DeliveryService;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface DeliveryServiceDAO {

    public Optional<DeliveryService> createDeliveryService(DeliveryService deliveryService);

    Optional<DeliveryService> getDeliveryServiceByDeliveryServiceCode(String deliveryServiceCode);

    Integer reportCompletedDateTime(OffsetDateTime deliveryDateTime, String deliveryServiceCode);
}
