package pl.project.business.dao;

import pl.project.domain.model.RestaurantOwner;

import java.util.List;
import java.util.Optional;

public interface RestaurantOwnerDAO {

    Optional<RestaurantOwner> createRestaurantOwner(RestaurantOwner restaurantOwner);

    Optional<RestaurantOwner> findRestaurantOwnerByEmail(String email);

    List<RestaurantOwner> findActiveRestaurantOwner();

    Integer changeRestaurantOwnerEmail(String newEmail, String oldEmail);

    Integer changeRestaurantOwnerPhoneNumber(String newPhoneNumber, String email);

    Integer deactivateRestaurantOwner(String email);

}
