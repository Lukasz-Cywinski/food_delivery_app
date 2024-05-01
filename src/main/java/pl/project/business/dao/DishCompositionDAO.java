package pl.project.business.dao;

import pl.project.domain.model.DishComposition;
import pl.project.domain.model.Order;
import pl.project.domain.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface DishCompositionDAO {

    List<DishComposition> findDishCompositionByOrder(String orderCode);

    Optional<DishComposition> createDishComposition(DishComposition dishComposition);

    List<Order> getActiveOrdersForRestaurant(String restaurantCode);

    List <Order> getRealizedOrdersForRestaurant(String restaurantCode);

    Integer deleteDishComposition(String orderCode);

    List<DishComposition> getActiveOrdersForCustomer(String customerEmail);
}
