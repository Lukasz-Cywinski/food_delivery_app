package pl.project.business.dao;

import pl.project.domain.model.Dish;
import pl.project.domain.model.Restaurant;
import pl.project.domain.model.RestaurantOwner;
import pl.project.domain.model.ServedAddress;

import java.util.List;
import java.util.Optional;

public interface RestaurantDAO {

    Optional<Restaurant> createRestaurant(Restaurant restaurant);

    Optional<Restaurant> findRestaurantByRestaurantCode(String restaurantCode);

    List<Restaurant> findActiveRestaurants();

    List<Dish> findActiveDishesForRestaurant(Restaurant restaurant);

    List<ServedAddress> findServedAddressesForRestaurant(Restaurant restaurant);

    Integer changeRestaurantName(String newRestaurantName, String restaurantCode);

    Integer changeRestaurantOwner(RestaurantOwner newRestaurantOwner, String restaurantCode);

    Integer deactivateRestaurant(String restaurantCode);


}
