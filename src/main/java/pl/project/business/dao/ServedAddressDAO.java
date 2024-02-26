package pl.project.business.dao;

import pl.project.domain.model.Restaurant;
import pl.project.domain.model.ServedAddress;

import java.util.List;
import java.util.Optional;

public interface ServedAddressDAO {

    Optional<ServedAddress> createServedAddress(ServedAddress servedAddress);

    Integer changeCity(String newCity, Integer servedAddressId);

    Integer changeStreet(String newStreet, Integer servedAddressId);

    Integer deleteServedAddress(ServedAddress servedAddress);

    List<ServedAddress> getServedAddresses(Restaurant restaurant);

    List<Restaurant> getRestaurantByServedAddress(ServedAddress servedAddress);
}
