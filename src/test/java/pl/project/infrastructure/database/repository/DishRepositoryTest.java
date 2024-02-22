package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.infrastructure.database.entity.DishCategoryEntity;
import pl.project.infrastructure.database.entity.DishEntity;
import pl.project.infrastructure.database.entity.DishPhotoEntity;
import pl.project.infrastructure.database.entity.RestaurantEntity;
import pl.project.integration.configuration.MyJpaConfiguration;
import pl.project.infrastructure.database.repository.jpa.DishCategoryJpaRepository;
import pl.project.infrastructure.database.repository.jpa.DishJpaRepository;
import pl.project.infrastructure.database.repository.jpa.DishPhotoJpaRepository;
import pl.project.infrastructure.database.repository.jpa.RestaurantJpaRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.project.util.db.DishCategoryInstance.*;
import static pl.project.util.db.DishInstance.*;
import static pl.project.util.db.DishPhotoInstance.*;
import static pl.project.util.db.RestaurantInstance.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class DishRepositoryTest extends MyJpaConfiguration {

    private DishJpaRepository dishJpaRepository;
    private DishPhotoJpaRepository dishPhotoJpaRepository;
    private DishCategoryJpaRepository dishCategoryJpaRepository;
    private RestaurantJpaRepository restaurantJpaRepository;

    @Test
    void thatDishCanBeSavedCorrectly(){
        //given
        RestaurantEntity restaurant1 = someRestaurant1();
        RestaurantEntity restaurant2 = someRestaurant2();
        RestaurantEntity restaurant3 = someRestaurant3();

        DishPhotoEntity dishPhoto1 = someDishPhoto1();
        DishPhotoEntity dishPhoto2 = someDishPhoto2();
        DishPhotoEntity dishPhoto3 = someDishPhoto3();

        DishCategoryEntity dishCategory1 = someDishCategory1();
        DishCategoryEntity dishCategory2 = someDishCategory2();
        DishCategoryEntity dishCategory3 = someDishCategory3();

        DishEntity dish1 = someDish1();
        DishEntity dish2 = someDish2();
        DishEntity dish3 = someDish3();

        dish1.setRestaurant(restaurant1);
        dish1.setDishPhoto(dishPhoto1);
        dish1.setDishCategory(dishCategory1);

        dish2.setRestaurant(restaurant2);
        dish2.setDishPhoto(dishPhoto2);
        dish2.setDishCategory(dishCategory2);

        dish3.setRestaurant(restaurant3);
        dish3.setDishPhoto(dishPhoto3);
        dish3.setDishCategory(dishCategory3);

        //when
        restaurantJpaRepository.save(restaurant1);
        restaurantJpaRepository.save(restaurant2);
        restaurantJpaRepository.save(restaurant3);

        dishPhotoJpaRepository.save(dishPhoto1);
        dishPhotoJpaRepository.save(dishPhoto2);
        dishPhotoJpaRepository.save(dishPhoto3);

        dishCategoryJpaRepository.save(dishCategory1);
        dishCategoryJpaRepository.save(dishCategory2);
        dishCategoryJpaRepository.save(dishCategory3);

        dishJpaRepository.save(dish1);
        dishJpaRepository.save(dish2);
        dishJpaRepository.save(dish3);

        DishEntity dishFromDb1 = dishJpaRepository.findByDishCode(dish1.getDishCode()).orElseThrow();
        DishEntity dishFromDb2 = dishJpaRepository.findByDishCode(dish2.getDishCode()).orElseThrow();
        DishEntity dishFromDb3 = dishJpaRepository.findByDishCode(dish3.getDishCode()).orElseThrow();

        List<DishEntity> dishes = dishJpaRepository.findAll();

        //then
                //first
        assertThat(dishFromDb1)
                .usingRecursiveComparison()
                .ignoringFields("id", "restaurant", "dishPhoto", "dishCategory")
                .isEqualTo(dish1);

        assertThat(dishFromDb1)
                .usingRecursiveComparison()
                .comparingOnlyFields("restaurant", "dishPhoto", "dishCategory")
                .isNotNull();

                //second
        assertThat(dishFromDb2)
                .usingRecursiveComparison()
                .ignoringFields("id", "restaurant", "dishPhoto", "dishCategory")
                .isEqualTo(dish2);

        assertThat(dishFromDb2)
                .usingRecursiveComparison()
                .comparingOnlyFields("restaurant", "dishPhoto", "dishCategory")
                .isNotNull();

                //third
        assertThat(dishFromDb3)
                .usingRecursiveComparison()
                .ignoringFields("id", "restaurant", "dishPhoto", "dishCategory")
                .isEqualTo(dish3);

        assertThat(dishFromDb3)
                .usingRecursiveComparison()
                .comparingOnlyFields("restaurant", "dishPhoto", "dishCategory")
                .isNotNull();

                //all
        assertEquals(3, dishes.size());
    }

    @Test
    void thatDishNameCanBeModifiedCorrectly(){
        //given
        RestaurantEntity restaurant1 = someRestaurant1();
        DishPhotoEntity dishPhoto1 = someDishPhoto1();
        DishCategoryEntity dishCategory1 = someDishCategory1();
        DishEntity dish1 = someDish1();

        dish1.setRestaurant(restaurant1);
        dish1.setDishPhoto(dishPhoto1);
        dish1.setDishCategory(dishCategory1);

        String newName = "new name";

        //when
        restaurantJpaRepository.save(restaurant1);
        dishPhotoJpaRepository.save(dishPhoto1);
        dishCategoryJpaRepository.save(dishCategory1);
        dishJpaRepository.save(dish1);

        dishJpaRepository.changeDishName(newName, dish1.getDishCode());

        DishEntity dishFromDb1 = dishJpaRepository.findByDishCode(dish1.getDishCode()).orElseThrow();

        //then
        assertEquals(newName, dishFromDb1.getName());
    }

    @Test
    void thatDishDescriptionCanBeModifiedCorrectly(){
        //given
        RestaurantEntity restaurant1 = someRestaurant1();
        DishPhotoEntity dishPhoto1 = someDishPhoto1();
        DishCategoryEntity dishCategory1 = someDishCategory1();
        DishEntity dish1 = someDish1();

        dish1.setRestaurant(restaurant1);
        dish1.setDishPhoto(dishPhoto1);
        dish1.setDishCategory(dishCategory1);

        String newDescription = "new description";

        //when
        restaurantJpaRepository.save(restaurant1);
        dishPhotoJpaRepository.save(dishPhoto1);
        dishCategoryJpaRepository.save(dishCategory1);
        dishJpaRepository.save(dish1);

        dishJpaRepository.changeDishDescription(newDescription, dish1.getDishCode());

        DishEntity dishFromDb1 = dishJpaRepository.findByDishCode(dish1.getDishCode()).orElseThrow();

        //then
        assertEquals(newDescription, dishFromDb1.getDescription());
    }

    @Test
    void thatDishPriceCanBeModifiedCorrectly(){
        //given
        RestaurantEntity restaurant1 = someRestaurant1();
        DishPhotoEntity dishPhoto1 = someDishPhoto1();
        DishCategoryEntity dishCategory1 = someDishCategory1();
        DishEntity dish1 = someDish1();

        dish1.setRestaurant(restaurant1);
        dish1.setDishPhoto(dishPhoto1);
        dish1.setDishCategory(dishCategory1);

        BigDecimal newPrice = BigDecimal.TEN.setScale(2, RoundingMode.HALF_UP);

        //when
        restaurantJpaRepository.save(restaurant1);
        dishPhotoJpaRepository.save(dishPhoto1);
        dishCategoryJpaRepository.save(dishCategory1);
        dishJpaRepository.save(dish1);

        dishJpaRepository.changeDishPrice(newPrice, dish1.getDishCode());

        DishEntity dishFromDb1 = dishJpaRepository.findByDishCode(dish1.getDishCode()).orElseThrow();

        //then
        assertEquals(newPrice, dishFromDb1.getPrice());
    }

    @Test
    void thatDishPreparationTimeCanBeModifiedCorrectly(){
        //given
        RestaurantEntity restaurant1 = someRestaurant1();
        DishPhotoEntity dishPhoto1 = someDishPhoto1();
        DishCategoryEntity dishCategory1 = someDishCategory1();
        DishEntity dish1 = someDish1();

        dish1.setRestaurant(restaurant1);
        dish1.setDishPhoto(dishPhoto1);
        dish1.setDishCategory(dishCategory1);

        Integer newPreparationTime = 100;

        //when
        restaurantJpaRepository.save(restaurant1);
        dishPhotoJpaRepository.save(dishPhoto1);
        dishCategoryJpaRepository.save(dishCategory1);
        dishJpaRepository.save(dish1);

        dishJpaRepository.changeDishPreparationTime(newPreparationTime, dish1.getDishCode());

        DishEntity dishFromDb1 = dishJpaRepository.findByDishCode(dish1.getDishCode()).orElseThrow();

        //then
        assertEquals(newPreparationTime, dishFromDb1.getAveragePreparationTimeMin());
    }

    @Test
    void thatDishPhotoCanBeModifiedCorrectly(){
        //given
        RestaurantEntity restaurant1 = someRestaurant1();
        DishPhotoEntity dishPhoto1 = someDishPhoto1();
        DishCategoryEntity dishCategory1 = someDishCategory1();
        DishEntity dish1 = someDish1();

        dish1.setRestaurant(restaurant1);
        dish1.setDishPhoto(dishPhoto1);
        dish1.setDishCategory(dishCategory1);

        DishPhotoEntity newDishPhoto = someDishPhoto2();

        //when
        restaurantJpaRepository.save(restaurant1);
        dishPhotoJpaRepository.save(dishPhoto1);
        dishCategoryJpaRepository.save(dishCategory1);
        dishJpaRepository.save(dish1);

        dishPhotoJpaRepository.save(newDishPhoto);
        dishJpaRepository.changeDishPhoto(newDishPhoto, dish1.getDishCode());

        DishEntity dishFromDb1 = dishJpaRepository.findByDishCode(dish1.getDishCode()).orElseThrow();

        //then
        assertEquals(newDishPhoto.getUrl(), dishFromDb1.getDishPhoto().getUrl());
    }

    @Test
    void thatDishCategoryCanBeModifiedCorrectly(){
        //given
        RestaurantEntity restaurant1 = someRestaurant1();
        DishPhotoEntity dishPhoto1 = someDishPhoto1();
        DishCategoryEntity dishCategory1 = someDishCategory1();
        DishEntity dish1 = someDish1();

        dish1.setRestaurant(restaurant1);
        dish1.setDishPhoto(dishPhoto1);
        dish1.setDishCategory(dishCategory1);

        DishCategoryEntity newDishCategory = someDishCategory2();

        //when
        restaurantJpaRepository.save(restaurant1);
        dishPhotoJpaRepository.save(dishPhoto1);
        dishCategoryJpaRepository.save(dishCategory1);
        dishJpaRepository.save(dish1);

        dishCategoryJpaRepository.save(newDishCategory);
        dishJpaRepository.changeDishCategory(newDishCategory, dish1.getDishCode());

        DishEntity dishFromDb1 = dishJpaRepository.findByDishCode(dish1.getDishCode()).orElseThrow();

        //then
        Assertions.assertThat(newDishCategory)
                .usingRecursiveComparison()
                .comparingOnlyFields("name", "description")
                .isEqualTo(dishFromDb1.getDishCategory());
    }

    @Test
    void isDishDeactivatedCorrectly(){

        //given
        RestaurantEntity restaurant1 = someRestaurant1();
        RestaurantEntity restaurant2 = someRestaurant2();
        RestaurantEntity restaurant3 = someRestaurant3();

        DishPhotoEntity dishPhoto1 = someDishPhoto1();
        DishPhotoEntity dishPhoto2 = someDishPhoto2();
        DishPhotoEntity dishPhoto3 = someDishPhoto3();

        DishCategoryEntity dishCategory1 = someDishCategory1();
        DishCategoryEntity dishCategory2 = someDishCategory2();
        DishCategoryEntity dishCategory3 = someDishCategory3();

        DishEntity dish1 = someDish1();
        DishEntity dish2 = someDish2();
        DishEntity dish3 = someDish3();

        dish1.setRestaurant(restaurant1);
        dish1.setDishPhoto(dishPhoto1);
        dish1.setDishCategory(dishCategory1);

        dish2.setRestaurant(restaurant2);
        dish2.setDishPhoto(dishPhoto2);
        dish2.setDishCategory(dishCategory2);

        dish3.setRestaurant(restaurant3);
        dish3.setDishPhoto(dishPhoto3);
        dish3.setDishCategory(dishCategory3);

        //when
        restaurantJpaRepository.save(restaurant1);
        restaurantJpaRepository.save(restaurant2);
        restaurantJpaRepository.save(restaurant3);

        dishPhotoJpaRepository.save(dishPhoto1);
        dishPhotoJpaRepository.save(dishPhoto2);
        dishPhotoJpaRepository.save(dishPhoto3);

        dishCategoryJpaRepository.save(dishCategory1);
        dishCategoryJpaRepository.save(dishCategory2);
        dishCategoryJpaRepository.save(dishCategory3);

        dishJpaRepository.save(dish1);
        dishJpaRepository.save(dish2);
        dishJpaRepository.save(dish3);

        dishJpaRepository.deactivate(dish3.getDishCode());

        List<DishEntity> activeDishes = dishJpaRepository.findActive();

        // then
        assertEquals(2, activeDishes.size());
    }

}