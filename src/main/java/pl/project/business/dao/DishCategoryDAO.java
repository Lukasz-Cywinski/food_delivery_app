package pl.project.business.dao;

import pl.project.domain.model.DishCategory;

import java.util.List;
import java.util.Optional;

public interface DishCategoryDAO {

    List<DishCategory> getAllDishCategories();

    Optional<DishCategory> getDishCategoryByDishCategoryId(Integer dishCategoryID);

    Optional<DishCategory> createDishCategory(DishCategory dishCategory);

    Integer changeDishCategoryDescription(String newDescription, Integer dishCategoryId);
}
