package pl.project.business.dao;

import pl.project.domain.model.DishComposition;
import pl.project.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface DishCompositionDAO {

    List<DishComposition> findDishCompositionByOrder(Order order);

    Optional<DishComposition> createDishCategory(DishComposition dishComposition);

}
