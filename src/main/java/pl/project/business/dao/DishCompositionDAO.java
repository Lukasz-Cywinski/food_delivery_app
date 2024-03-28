package pl.project.business.dao;

import pl.project.domain.model.DishComposition;
import pl.project.domain.model.Order;
import pl.project.domain.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface DishCompositionDAO {

    List<DishComposition> findDishCompositionByOrder(Order order);

    Optional<DishComposition> createDishComposition(DishComposition dishComposition);

    List<Order> getActiveOrdersForRestaurant(String restaurantCode);

    List <Order> getRealizedOrdersForRestaurant(String restaurantCode);

    void deleteDishComposition(DishComposition dishComposition);
}
