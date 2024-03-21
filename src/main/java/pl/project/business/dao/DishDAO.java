package pl.project.business.dao;

import pl.project.domain.model.Dish;
import pl.project.domain.model.DishCategory;
import pl.project.domain.model.DishPhoto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface DishDAO {

    Optional<Dish> createDish(Dish dish);

    Optional<Dish> findDishByDishCode(String dishCode);

//    List<Dish> findActiveDishes();

    List<Dish> findActiveDishesByRestaurant(String restaurantCode);

    Integer changeDishName(String newDishName, String dishCode);

    Integer changeDishDescription(String newDishDescription, String dishCode);

    Integer changeDishPrice(BigDecimal newDishPrice, String dishCode);

    Integer changeDishPreparationTime(Integer minutes, String dishCode);

    Integer changeDishPhoto(DishPhoto newDishPhoto, String dishCode);

    Integer changeDishCategory(Integer newDishCategoryId, String dishCode);

    Integer deactivateDish(String dishCode);
}
