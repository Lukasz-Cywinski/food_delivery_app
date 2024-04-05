package pl.project.business.dao;

import org.springframework.data.domain.Pageable;
import pl.project.domain.model.*;

import java.util.List;
import java.util.Optional;

public interface RestaurantDAO {

    Optional<Restaurant> createRestaurant(Restaurant restaurant);

    Optional<Restaurant> findRestaurantByRestaurantCode(String restaurantCode);

    List<Restaurant> findActiveRestaurants(Pageable pageable);

    List<Restaurant> findActiveRestaurantsWithAddress(DeliveryAddress deliveryAddress, Pageable pageable);

    List<Restaurant> findByRestaurantOwner(RestaurantOwner restaurantOwner);

    List<Dish> findActiveDishesForRestaurant(Restaurant restaurant, Pageable pageable);

    List<ServedAddress> findServedAddressesForRestaurant(Restaurant restaurant);

    Integer changeRestaurantName(String newRestaurantName, String restaurantCode);

    Integer changeRestaurantOwner(RestaurantOwner newRestaurantOwner, String restaurantCode);

    Integer deactivateRestaurant(String restaurantCode);

    Integer countAllActiveRestaurantsWithAddress(DeliveryAddress deliveryAddress);
}
