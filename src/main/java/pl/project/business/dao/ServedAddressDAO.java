package pl.project.business.dao;

import pl.project.domain.model.RestaurantOwner;
import pl.project.domain.model.ServedAddress;

import java.util.Optional;

public interface ServedAddressDAO {

    Optional<ServedAddress> createServedAddress(ServedAddress servedAddress);

    Integer changeCity(String newCity, Integer servedAddressId);

    Integer changeStreet(String newStreet, Integer servedAddressId);
}
