package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.infrastructure.database.entity.DishCategoryEntity;
import pl.project.infrastructure.database.entity.DishEntity;
import pl.project.infrastructure.database.entity.DishPhotoEntity;
import pl.project.infrastructure.database.entity.RestaurantEntity;
import pl.project.infrastructure.database.repository.jpa.*;
import pl.project.infrastructure.security.db.UserRepository;
import pl.project.integration.configuration.Initializer;
import pl.project.integration.configuration.MyJpaConfiguration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.project.util.db.DishInstance.*;
import static pl.project.util.db.DishPhotoInstance.someDishPhoto2;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class DishRepositoryTest extends MyJpaConfiguration {

    private ServedAddressJpaRepository servedAddressJpaRepository;
    private RestaurantOwnerJpaRepository restaurantOwnerJpaRepository;
    private RestaurantJpaRepository restaurantJpaRepository;
    private DishPhotoJpaRepository dishPhotoJpaRepository;
    private DishCategoryJpaRepository dishCategoryJpaRepository;
    private DishJpaRepository dishJpaRepository;
    private DishOpinionJpaRepository dishOpinionJpaRepository;
    private DishCompositionJpaRepository dishCompositionJpaRepository;
    private OrderJpaRepository orderJpaRepository;
    private CustomerJpaRepository customerJpaRepository;
    private DeliveryServiceJpaRepository deliveryServiceJpaRepository;
    private DeliveryAddressJpaRepository deliveryAddressJpaRepository;
    private DeliveryManJpaRepository deliveryManJpaRepository;
    private UserRepository userRepository;

    private final Initializer initializer = new Initializer();

    @BeforeEach
    void initializeDbData(){
        initializer.setServedAddressJpaRepository(servedAddressJpaRepository);
        initializer.setRestaurantOwnerJpaRepository(restaurantOwnerJpaRepository);
        initializer.setRestaurantJpaRepository(restaurantJpaRepository);
        initializer.setDishPhotoJpaRepository(dishPhotoJpaRepository);
        initializer.setDishCategoryJpaRepository(dishCategoryJpaRepository);
        initializer.setDishJpaRepository(dishJpaRepository);
        initializer.setDishOpinionJpaRepository(dishOpinionJpaRepository);
        initializer.setDishCompositionJpaRepository(dishCompositionJpaRepository);
        initializer.setOrderJpaRepository(orderJpaRepository);
        initializer.setCustomerJpaRepository(customerJpaRepository);
        initializer.setDeliveryServiceJpaRepository(deliveryServiceJpaRepository);
        initializer.setDeliveryAddressJpaRepository(deliveryAddressJpaRepository);
        initializer.setDeliveryManJpaRepository(deliveryManJpaRepository);
        initializer.setUserRepository(userRepository);

        initializer.initializedBData();
    }

    @Test
    void thatDishCanBeSavedCorrectly(){
        //given
        DishEntity dish1 = someDish1();
        DishEntity dish2 = someDish2();
        DishEntity dish3 = someDish3();

        String dishCode1 = someDish1().getDishCode();
        String dishCode2 = someDish2().getDishCode();
        String dishCode3 = someDish3().getDishCode();

        //when
        DishEntity dishFromDb1 = dishJpaRepository.findByDishCode(dishCode1).orElseThrow();
        DishEntity dishFromDb2 = dishJpaRepository.findByDishCode(dishCode2).orElseThrow();
        DishEntity dishFromDb3 = dishJpaRepository.findByDishCode(dishCode3).orElseThrow();

        List<DishEntity> dishesFromDb1 = dishJpaRepository.findAll();
        List<DishEntity> dishesFromDb2 = List.of(dishFromDb1, dishFromDb2, dishFromDb3);

        //then
        assertThat(dishesFromDb1)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "restaurant", "dishPhoto", "dishCategory")
                .contains(dish1, dish2, dish3);
        assertThat(dishesFromDb2)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "restaurant", "dishPhoto", "dishCategory")
                .contains(dish1, dish2, dish3);

        assertTrue(dishesFromDb1.size() >= 3);
    }

    @Test
    void thatActiveDishesCanByFindCorrectlyByRestaurant(){
        //given
        RestaurantEntity restaurant = initializer.SAVED_RESTAURANTS.getFirst();
        String dishCode = restaurant.getRestaurantCode();
        DishEntity dish1 = initializer.SAVED_DISHES.get(0);
        DishEntity dish2 = initializer.SAVED_DISHES.get(1);
        DishEntity dish3 = initializer.SAVED_DISHES.get(2);
        dish1.setRestaurant(restaurant);
        dish2.setRestaurant(restaurant);
        dish3.setRestaurant(restaurant);

        //when
        dishJpaRepository.save(dish1);
        dishJpaRepository.save(dish2);
        dishJpaRepository.save(dish3);

        List<DishEntity> result = dishJpaRepository.findActiveByRestaurant(dishCode);

        //then
        assertEquals(3, result.size());
    }

    @Test
    void thatDishNameCanBeModifiedCorrectly(){
        //given
        String dishCode = someDish1().getDishCode();
        String newName = "new name";

        //when
        dishJpaRepository.changeDishName(newName, dishCode);
        DishEntity dishFromDb1 = dishJpaRepository.findByDishCode(dishCode).orElseThrow();

        //then
        assertEquals(newName, dishFromDb1.getName());
    }

    @Test
    void thatDishDescriptionCanBeModifiedCorrectly(){
        //given
        String dishCode = someDish1().getDishCode();
        String newDescription = "new description";

        //when
        dishJpaRepository.changeDishDescription(newDescription, dishCode);
        DishEntity dishFromDb1 = dishJpaRepository.findByDishCode(dishCode).orElseThrow();

        //then
        assertEquals(newDescription, dishFromDb1.getDescription());
    }

    @Test
    void thatDishPriceCanBeModifiedCorrectly(){
        //given
        String dishCode = someDish1().getDishCode();
        BigDecimal newPrice = BigDecimal.TEN.setScale(2, RoundingMode.HALF_UP);

        //when
        dishJpaRepository.changeDishPrice(newPrice, dishCode);
        DishEntity dishFromDb1 = dishJpaRepository.findByDishCode(dishCode).orElseThrow();

        //then
        assertEquals(newPrice, dishFromDb1.getPrice());
    }

    @Test
    void thatDishPreparationTimeCanBeModifiedCorrectly(){
        //given
        String dishCode = someDish1().getDishCode();
        Integer newPreparationTime = 100;

        //when
        dishJpaRepository.changeDishPreparationTime(newPreparationTime, dishCode);
        DishEntity dishFromDb1 = dishJpaRepository.findByDishCode(dishCode).orElseThrow();

        //then
        assertEquals(newPreparationTime, dishFromDb1.getAveragePreparationTimeMin());
    }

    @Test
    void thatDishPhotoCanBeModifiedCorrectly(){
        //given
        String dishCode = someDish1().getDishCode();
        DishPhotoEntity newDishPhoto = someDishPhoto2();
        newDishPhoto.setUrl("XXXXX");

        //when
        dishPhotoJpaRepository.save(newDishPhoto);
        dishJpaRepository.changeDishPhoto(newDishPhoto, dishCode);
        DishEntity dishFromDb1 = dishJpaRepository.findByDishCode(dishCode).orElseThrow();

        //then
        assertEquals(newDishPhoto.getUrl(), dishFromDb1.getDishPhoto().getUrl());
    }

    @Test
    void thatDishCategoryCanBeModifiedCorrectly(){
        //given
        String dishCode = someDish1().getDishCode();
        DishCategoryEntity newDishCategory = initializer.SAVED_DISH_CATEGORIES.get(1);
        Integer newDishCategoryId = newDishCategory.getId();

        //when
        dishJpaRepository.changeDishCategory(newDishCategoryId, dishCode);
        DishEntity dishFromDb1 = dishJpaRepository.findByDishCode(dishCode).orElseThrow();

        //then
        Assertions.assertThat(newDishCategory)
                .usingRecursiveComparison()
                .comparingOnlyFields("name", "description")
                .isEqualTo(dishFromDb1.getDishCategory());
    }

    @Test
    void isDishDeactivatedCorrectly(){
        //given
        String dishCode = someDish1().getDishCode();

        //when
        List<DishEntity> before = dishJpaRepository.findActive();
        dishJpaRepository.deactivate(dishCode);
        List<DishEntity> after = dishJpaRepository.findActive();
        int difference = before.size() - after.size();

        // then
        assertEquals(1, difference);
    }

}