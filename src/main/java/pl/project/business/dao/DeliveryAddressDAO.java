package pl.project.business.dao;

import pl.project.domain.model.DeliveryAddress;

import java.util.Optional;

public interface DeliveryAddressDAO {

    Optional<DeliveryAddress> createDeliveryAddress(DeliveryAddress deliveryAddress);

    Integer changeDeliveryAddressCity(String newCity, Integer deliveryAddressId);

    Integer changeDeliveryAddressPostalCode(String newPostalCode, Integer deliveryAddressId);

    Integer changeDeliveryAddressStreet(String newStreet, Integer deliveryAddressId);
}
