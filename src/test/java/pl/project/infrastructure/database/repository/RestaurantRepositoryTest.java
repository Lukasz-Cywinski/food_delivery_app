package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pl.project.infrastructure.database.entity.*;
import pl.project.integration.configuration.MyJpaConfiguration;
import pl.project.infrastructure.database.repository.jpa.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.project.util.db.DishCategoryInstance.*;
import static pl.project.util.db.DishInstance.*;
import static pl.project.util.db.DishPhotoInstance.*;
import static pl.project.util.db.RestaurantInstance.*;
import static pl.project.util.db.RestaurantOwnerInstance.someRestaurantOwner2;
import static pl.project.util.db.ServedAddressInstance.someServedAddress1;
import static pl.project.util.db.ServedAddressInstance.someServedAddress2;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class RestaurantRepositoryTest extends MyJpaConfiguration {

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private RestaurantJpaRepository restaurantJpaRepository;
    private RestaurantOwnerJpaRepository restaurantOwnerJpaRepository;
    private ServedAddressJpaRepository servedAddressJpaRepository;
    private DishPhotoJpaRepository dishPhotoJpaRepository;
    private DishCategoryJpaRepository dishCategoryJpaRepository;
    private DishJpaRepository dishJpaRepository;

    @Test
    void thatRestaurantCanBeSavedCorrectly() {

        // given
        RestaurantEntity restaurant1 = someRestaurant1();
        RestaurantEntity restaurant2 = someRestaurant2();
        RestaurantEntity restaurant3 = someRestaurant3();

        // when
        restaurantJpaRepository.save(restaurant1);
        restaurantJpaRepository.save(restaurant2);
        restaurantJpaRepository.save(restaurant3);

        RestaurantEntity restaurantFromDb1 = restaurantJpaRepository.findByRestaurantCode(restaurant1.getRestaurantCode()).orElseThrow();
        RestaurantEntity restaurantFromDb2 = restaurantJpaRepository.findByRestaurantCode(restaurant2.getRestaurantCode()).orElseThrow();
        RestaurantEntity restaurantFromDb3 = restaurantJpaRepository.findByRestaurantCode(restaurant3.getRestaurantCode()).orElseThrow();

        Sort sort = Sort.by("added").descending();
        Pageable pageable = PageRequest.of(0, 3, sort);

        Page<RestaurantEntity> page = restaurantJpaRepository.findActive(pageable);
        List<RestaurantEntity> activeRestaurants = page.getContent();

        //then
                // first
        assertThat(restaurant1)
                .usingRecursiveComparison()
                .comparingOnlyFields("restaurantCode", "name", "isActive")
                .isEqualTo(restaurantFromDb1);

        assertThat(restaurant1.getAdded().format(FORMATTER))
                .isEqualTo(restaurantFromDb1.getAdded().format(FORMATTER));

        assertThat(restaurant1.getRestaurantOwner())
                .usingRecursiveComparison()
                .comparingOnlyFields("email")
                .isEqualTo(restaurantFromDb1.getRestaurantOwner());

                //second
        assertThat(restaurant2)
                .usingRecursiveComparison()
                .comparingOnlyFields("restaurantCode", "name", "isActive")
                .isEqualTo(restaurantFromDb2);

        assertThat(restaurant2.getAdded().format(FORMATTER))
                .isEqualTo(restaurantFromDb2.getAdded().format(FORMATTER));

        assertThat(restaurant2.getRestaurantOwner())
                .usingRecursiveComparison()
                .comparingOnlyFields("email")
                .isEqualTo(restaurantFromDb2.getRestaurantOwner());

                //third
        assertThat(restaurant3)
                .usingRecursiveComparison()
                .comparingOnlyFields("restaurantCode", "name", "isActive")
                .isEqualTo(restaurantFromDb3);

        assertThat(restaurant3.getAdded().format(FORMATTER))
                .isEqualTo(restaurantFromDb3.getAdded().format(FORMATTER));

        assertThat(restaurant3.getRestaurantOwner())
                .usingRecursiveComparison()
                .comparingOnlyFields("email")
                .isEqualTo(restaurantFromDb3.getRestaurantOwner());

                //all
        assertEquals(3, activeRestaurants.size());
    }

    @Test
    void thatRestaurantNameCanBeModifiedCorrectly(){

        //given
        RestaurantEntity restaurant1 = someRestaurant1();
        String newRestaurantName = "New Name";

        //when
        restaurantJpaRepository.save(restaurant1);
        restaurantJpaRepository.changeName(newRestaurantName, restaurant1.getRestaurantCode());
        RestaurantEntity restaurantFromDb1 = restaurantJpaRepository.findByRestaurantCode(restaurant1.getRestaurantCode()).orElseThrow();

        //then
        assertEquals(newRestaurantName, restaurantFromDb1.getName());
    }

    @Test
    void thatRestaurantOwnerCanBeModifiedCorrectly(){

        //given
        RestaurantEntity restaurant1 = someRestaurant1();
        RestaurantOwnerEntity newOwner = someRestaurantOwner2();

        //when
        restaurantOwnerJpaRepository.save(newOwner);
        restaurantJpaRepository.save(restaurant1);

        restaurantJpaRepository.changeOwner(newOwner, restaurant1.getRestaurantCode());

        RestaurantEntity restaurantFromDb1 = restaurantJpaRepository.findByRestaurantCode(restaurant1.getRestaurantCode()).orElseThrow();

        //then
        assertEquals(newOwner.getEmail(), restaurantFromDb1.getRestaurantOwner().getEmail());
    }

    @Test
    void isRestaurantDeactivatedCorrectly(){

        // given
        RestaurantEntity restaurant1 = someRestaurant1();
        RestaurantEntity restaurant2 = someRestaurant2();
        RestaurantEntity restaurant3 = someRestaurant3();

        // when
        restaurantJpaRepository.save(restaurant1);
        restaurantJpaRepository.save(restaurant2);
        restaurantJpaRepository.save(restaurant3);

        restaurantJpaRepository.deactivate(restaurant1.getRestaurantCode());

        Sort sort = Sort.by("added").descending();
        Pageable pageable = PageRequest.of(0, 3, sort);

        Page<RestaurantEntity> page = restaurantJpaRepository.findActive(pageable);
        List<RestaurantEntity> activeRestaurants = page.getContent();

        // then
        assertEquals(2, activeRestaurants.size());
    }

    @Test
    void isServedAddressesSelectedCorrectly(){

        //given
        RestaurantEntity restaurant1 = someRestaurant1();
        ServedAddressEntity address1 = someServedAddress1();
        ServedAddressEntity address2 = someServedAddress2();

        address1.setRestaurant(restaurant1);
        address2.setRestaurant(restaurant1);

        //when
        restaurantJpaRepository.save(restaurant1);
        servedAddressJpaRepository.save(address1);
        servedAddressJpaRepository.save(address2);

        RestaurantEntity restaurantFromDb1 = restaurantJpaRepository.findByRestaurantCode(restaurant1.getRestaurantCode()).orElseThrow();
        List<ServedAddressEntity> addresses = restaurantJpaRepository.findServedAddresses(restaurantFromDb1);

        //then
        assertEquals(2, addresses.size());

        assertThat(addresses)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(address1, address2);
    }

    @Test
    void isDishesSelectedCorrectly(){
        //given
            //dishes
        RestaurantEntity restaurant1 = someRestaurant1();

        DishPhotoEntity dishPhoto1 = someDishPhoto1();
        DishPhotoEntity dishPhoto2 = someDishPhoto2();
        DishPhotoEntity dishPhoto3 = someDishPhoto3();

        DishCategoryEntity dishCategory1 = someDishCategory1();
        DishCategoryEntity dishCategory2 = someDishCategory2();
        DishCategoryEntity dishCategory3 = someDishCategory3();

        DishEntity dish1 = someDish1();
        DishEntity dish2 = someDish2();
        DishEntity dish3 = someDish3();

        dish1.setDishPhoto(dishPhoto1);
        dish1.setDishCategory(dishCategory1);
        dish2.setDishPhoto(dishPhoto2);
        dish2.setDishCategory(dishCategory2);
        dish3.setDishPhoto(dishPhoto3);
        dish3.setDishCategory(dishCategory3);

        dish1.setRestaurant(restaurant1);
        dish2.setRestaurant(restaurant1);
        dish3.setRestaurant(restaurant1);

        //when
        restaurantJpaRepository.save(restaurant1);

        dishPhotoJpaRepository.save(dishPhoto1);
        dishPhotoJpaRepository.save(dishPhoto2);
        dishPhotoJpaRepository.save(dishPhoto3);

        dishCategoryJpaRepository.save(dishCategory1);
        dishCategoryJpaRepository.save(dishCategory2);
        dishCategoryJpaRepository.save(dishCategory3);

        dishJpaRepository.save(dish1);
        dishJpaRepository.save(dish2);
        dishJpaRepository.save(dish3);

        Sort sort = Sort.by("price").descending();
        Pageable pageable = PageRequest.of(0, 3, sort);

        Page<DishEntity> page = restaurantJpaRepository.findActiveDishes(restaurant1, pageable);
        List<DishEntity> activeDishes = page.getContent();

        //then
        assertEquals(3, activeDishes.size());

        assertThat(activeDishes)
                .usingRecursiveFieldByFieldElementComparatorOnFields("dishCode")
                .contains(dish1, dish2, dish3);
    }

}