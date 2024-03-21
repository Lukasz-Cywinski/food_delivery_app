package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.DishDAO;
import pl.project.domain.model.Dish;
import pl.project.domain.model.DishCategory;
import pl.project.domain.model.DishPhoto;
import pl.project.infrastructure.database.repository.jpa.DishJpaRepository;
import pl.project.infrastructure.database.repository.mapper.DishCategoryEntityMapper;
import pl.project.infrastructure.database.repository.mapper.DishEntityMapper;
import pl.project.infrastructure.database.repository.mapper.DishPhotoEntityMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class DishRepository implements DishDAO {

    private final DishJpaRepository dishJpaRepository;
    private final DishEntityMapper dishEntityMapper;
    private final DishPhotoEntityMapper dishPhotoEntityMapper;
    private final DishCategoryEntityMapper dishCategoryEntityMapper;


    @Override
    public Optional<Dish> createDish(Dish dish) {
        return Optional.ofNullable(dishEntityMapper.mapFromEntity(
                dishJpaRepository.save(
                        dishEntityMapper.mapToEntity(dish)
                )
        ));
    }

    @Override
    public Optional<Dish> findDishByDishCode(String dishCode) {
        return dishJpaRepository.findByDishCode(dishCode)
                .map(dishEntityMapper::mapFromEntity);
    }

    @Override
    public List<Dish> findActiveDishesByRestaurant(String restaurantCode) {
        return dishJpaRepository.findActiveByRestaurant(restaurantCode).stream()
                .map(dishEntityMapper::mapFromEntity)
                .toList();
    }

//    @Override
//    public List<Dish> findActiveDishes() {
//        return dishJpaRepository.findActive().stream()
//                .map(dishEntityMapper::mapFromEntity)
//                .toList();
//    }

    @Override
    public Integer changeDishName(String newDishName, String dishCode) {
        return dishJpaRepository.changeDishName(newDishName, dishCode);
    }

    @Override
    public Integer changeDishDescription(String newDishDescription, String dishCode) {
        return dishJpaRepository.changeDishDescription(newDishDescription, dishCode);
    }

    @Override
    public Integer changeDishPrice(BigDecimal newDishPrice, String dishCode) {
        return dishJpaRepository.changeDishPrice(newDishPrice, dishCode);
    }

    @Override
    public Integer changeDishPreparationTime(Integer minutes, String dishCode) {
        return dishJpaRepository.changeDishPreparationTime(minutes, dishCode);
    }

    @Override
    public Integer changeDishPhoto(DishPhoto newDishPhoto, String dishCode) {
        return dishJpaRepository.changeDishPhoto(
                dishPhotoEntityMapper.mapToEntity(newDishPhoto),
                dishCode);
    }

    @Override
    public Integer changeDishCategory(Integer newDishCategoryId, String dishCode) {
        return dishJpaRepository.changeDishCategory(newDishCategoryId, dishCode);
    }
//    @Override
//    public Integer changeDishCategory(Integer newDishCategoryId, String dishCode) {
//        return dishJpaRepository.changeDishCategory(
//                dishCategoryEntityMapper.mapToEntity(newDishCategory),
//                dishCode);
//    }

    @Override
    public Integer deactivateDish(String dishCode) {
        return dishJpaRepository.deactivate(dishCode);
    }
}
