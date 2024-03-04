package pl.project.business.services.subsidiary;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.project.business.dao.RestaurantDAO;
import pl.project.business.services.subsidiary.pageable.PageableService;
import pl.project.domain.exception.EntityCreationException;
import pl.project.domain.exception.EntityReadException;
import pl.project.domain.model.Dish;
import pl.project.domain.model.PageableProperties;
import pl.project.domain.model.Restaurant;
import pl.project.domain.model.RestaurantOwner;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final String RESTAURANT_CREATION_EXCEPTION = "Fail to crete restaurant: %s";
    private final String RESTAURANT_READ_EXCEPTION = "Fail to found restaurant by code: %s";

    private final RestaurantDAO restaurantDAO;
    private final PageableService pageableService;

    // without transactional -> methods all called from another Transactional methods
    public List<Restaurant> getActiveRestaurants(PageableProperties pageableProperties){
        return restaurantDAO.findActiveRestaurants(pageableService.buildPageable(pageableProperties));
    }

    public List<Dish> getDishesFromRestaurant(Restaurant restaurant, PageableProperties pageableProperties){
        return restaurantDAO.findActiveDishesForRestaurant(restaurant, pageableService.buildPageable(pageableProperties));
    }

//    public Restaurant createRestaurant(Restaurant restaurant){
//        return restaurantDAO.createRestaurant(restaurant)
//                .orElseThrow(() -> new EntityCreationException(RESTAURANT_CREATION_EXCEPTION.formatted(restaurant)));
//    }

    public Restaurant createRestaurant(String restaurantName, RestaurantOwner restaurantOwner){
                Restaurant restaurant = Restaurant.builder()
                .restaurantCode(UUID.randomUUID().toString())
                .name(restaurantName)
                .added(OffsetDateTime.now())
                .restaurantOwner(restaurantOwner)
                .isActive(true)
                .build();
        return restaurantDAO.createRestaurant(restaurant)
                .orElseThrow(() -> new EntityCreationException(RESTAURANT_CREATION_EXCEPTION.formatted(restaurant.getName())));
    }

    public boolean deactivateRestaurant(String restaurantCode) {
        return restaurantDAO.deactivateRestaurant(restaurantCode) == 1;
    }

    public boolean changeRestaurantName(String updatedRestaurantName, String restaurantCode) {
        return restaurantDAO.changeRestaurantName(updatedRestaurantName, restaurantCode) == 1;
    }

    public boolean changeRestaurantOwner(RestaurantOwner newOwner, String restaurantCode) {
        return restaurantDAO.changeRestaurantOwner(newOwner, restaurantCode) == 1;
    }

    public List<Restaurant> getRestaurantsByRestaurantOwner(RestaurantOwner restaurantOwner) {
        return restaurantDAO.findByRestaurantOwner(restaurantOwner);
    }

    //TODO - tests
    public Restaurant getRestaurantByRestaurantCode(String restaurantCode){
        return restaurantDAO.findRestaurantByRestaurantCode(restaurantCode)
                .orElseThrow(() -> new EntityReadException(RESTAURANT_READ_EXCEPTION.formatted(restaurantCode)));
    }
}
