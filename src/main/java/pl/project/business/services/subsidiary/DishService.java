package pl.project.business.services.subsidiary;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.project.business.dao.DishDAO;
import pl.project.domain.exception.EntityCreationException;
import pl.project.domain.exception.EntityReadException;
import pl.project.domain.model.Dish;

import java.util.List;

@Service
@AllArgsConstructor
public class DishService {

    private final String DISH_CREATION_EXCEPTION = "Fail to crete dish: %s";
    private final String DISH_READ_EXCEPTION = "Fail to found dish by dish code: %s";

    private final DishDAO dishDAO;
    private final DishPhotoService dishPhotoService;

    // without transactional -> methods all called from another Transactional methods
    public Dish getDish(String dishCode) {
        return dishDAO.findDishByDishCode(dishCode)
                .orElseThrow(() -> new EntityReadException(DISH_READ_EXCEPTION.formatted(dishCode)));
    }

    public Dish createDish(Dish dish) {
        return dishDAO.createDish(dish)
                .orElseThrow(() -> new EntityCreationException(DISH_CREATION_EXCEPTION.formatted(dish)));
    }

    public boolean modifyDishData(Dish dish, String dishCode) {
        return (dishDAO.changeDishName(dish.getName(), dishCode)
                + dishDAO.changeDishDescription(dish.getDescription(), dishCode)
                + dishDAO.changeDishPrice(dish.getPrice(), dishCode)
                + dishDAO.changeDishPreparationTime(dish.getAveragePreparationTimeMin(), dishCode)
                + dishDAO.changeDishCategory(dish.getDishCategory(), dishCode))
                > 0;
    }

    public boolean deactivateDish(String dishCode) {
        return dishDAO.deactivateDish(dishCode) == 1;
    }

    public List<Dish> getActiveDishesForRestaurants(String restaurantCode) {
        //TODO test
        return dishDAO.findActiveDishesByRestaurant(restaurantCode);
    }
}
