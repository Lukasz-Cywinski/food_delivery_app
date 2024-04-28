package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pl.project.domain.formatter.Formatters;
import pl.project.infrastructure.database.entity.*;
import pl.project.infrastructure.database.repository.jpa.*;
import pl.project.infrastructure.security.db.UserRepository;
import pl.project.integration.configuration.Initializer;
import pl.project.integration.configuration.MyJpaConfiguration;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static pl.project.domain.formatter.Formatters.*;
import static pl.project.util.db.DishInstance.*;
import static pl.project.util.db.RestaurantInstance.*;
import static pl.project.util.db.ServedAddressInstance.someServedAddress1;
import static pl.project.util.db.ServedAddressInstance.someServedAddress2;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class RestaurantRepositoryTest extends MyJpaConfiguration {

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
    void initializeDbData() {
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
    void thatRestaurantCanBeSavedCorrectly() {

        // given
        RestaurantEntity restaurant1 = someRestaurant1();
        RestaurantEntity restaurant2 = someRestaurant2();
        RestaurantEntity restaurant3 = someRestaurant3();
        String restaurantCode1 = restaurant1.getRestaurantCode();
        String restaurantCode2 = restaurant2.getRestaurantCode();
        String restaurantCode3 = restaurant3.getRestaurantCode();
        String sortBy = "added";
        Pageable pageable = createPageable(0, 1000, sortBy);

        // when
        RestaurantEntity restaurantFromDb1 = restaurantJpaRepository.findByRestaurantCode(restaurantCode1).orElseThrow();
        RestaurantEntity restaurantFromDb2 = restaurantJpaRepository.findByRestaurantCode(restaurantCode2).orElseThrow();
        RestaurantEntity restaurantFromDb3 = restaurantJpaRepository.findByRestaurantCode(restaurantCode3).orElseThrow();

        Page<RestaurantEntity> page = restaurantJpaRepository.findActive(pageable);
        List<RestaurantEntity> activeSortedRestaurants = page.getContent();
        List<RestaurantEntity> restaurantsByRestaurantCode = List.of(restaurantFromDb1, restaurantFromDb2, restaurantFromDb3);

        //then
        assertThat(restaurantsByRestaurantCode)
                .usingRecursiveFieldByFieldElementComparatorOnFields("restaurantCode", "name", "isActive")
                .contains(restaurant1, restaurant2, restaurant3);
        assertThat(activeSortedRestaurants)
                .usingRecursiveFieldByFieldElementComparatorOnFields("restaurantCode", "name", "isActive")
                .contains(restaurant1, restaurant2, restaurant3);

        assertThat(restaurant1.getAdded().format(DATE_TIME_FORMATTER))
                .isEqualTo(restaurantFromDb1.getAdded().format(DATE_TIME_FORMATTER));
        assertThat(restaurant2.getAdded().format(DATE_TIME_FORMATTER))
                .isEqualTo(restaurantFromDb2.getAdded().format(DATE_TIME_FORMATTER));
        assertThat(restaurant3.getAdded().format(DATE_TIME_FORMATTER))
                .isEqualTo(restaurantFromDb3.getAdded().format(DATE_TIME_FORMATTER));

        assertTrue(activeSortedRestaurants.size() >= 3);
    }

    @Test
    void thatRestaurantNameCanBeModifiedCorrectly() {

        //given
        String restaurantCode = someRestaurant1().getRestaurantCode();
        String newRestaurantName = "New Name";

        //when
        restaurantJpaRepository.changeName(newRestaurantName, restaurantCode);
        RestaurantEntity restaurantFromDb1 = restaurantJpaRepository.findByRestaurantCode(restaurantCode).orElseThrow();

        //then
        assertEquals(newRestaurantName, restaurantFromDb1.getName());
    }

    @Test
    void thatRestaurantOwnerCanBeModifiedCorrectly() {

        //given
        String restaurantCode = someRestaurant1().getRestaurantCode();
        RestaurantOwnerEntity newOwner = initializer.SAVED_RESTAURANT_OWNERS.get(1);

        //when
        restaurantJpaRepository.changeOwner(newOwner, restaurantCode);
        RestaurantEntity restaurantFromDb1 = restaurantJpaRepository.findByRestaurantCode(restaurantCode).orElseThrow();

        //then
        assertEquals(newOwner.getEmail(), restaurantFromDb1.getRestaurantOwner().getEmail());
    }

    @Test
    void isRestaurantDeactivatedCorrectly() {

        // given
        String restaurantCode = someRestaurant1().getRestaurantCode();
        String sortBy = "added";
        Pageable pageable = createPageable(0, 1000, sortBy);

        // when
        Page<RestaurantEntity> pageBefore = restaurantJpaRepository.findActive(pageable);
        restaurantJpaRepository.deactivate(restaurantCode);

        Page<RestaurantEntity> pageAfter = restaurantJpaRepository.findActive(pageable);
        List<RestaurantEntity> before = pageBefore.getContent();
        List<RestaurantEntity> after = pageAfter.getContent();
        Integer difference = before.size() - after.size();

        // then
        assertEquals(1, difference);
    }

    @Test
    void isServedAddressesSelectedCorrectly() {

        //given
        RestaurantEntity restaurant = initializer.SAVED_RESTAURANTS.getFirst();
        ServedAddressEntity address1 = someServedAddress1();
        ServedAddressEntity address2 = someServedAddress2();
        address2.setRestaurant(initializer.SAVED_RESTAURANTS.getFirst());

        //when
        servedAddressJpaRepository.save(address2);
        List<ServedAddressEntity> addresses = restaurantJpaRepository.findServedAddresses(restaurant);

        //then
        assertEquals(2, addresses.size());
        assertThat(addresses)
                .usingRecursiveFieldByFieldElementComparatorOnFields("city", "street")
                .contains(address1, address2);
    }

    @Test
    void isDishesSelectedCorrectly() {
        //given
        String sortBy = "price";
        Pageable pageable = createPageable(0, 5, sortBy);
        DishEntity dish1 = someDish1();
        DishEntity dish2 = someDish2();
        DishEntity dish3 = someDish3();

        RestaurantEntity restaurant = initializer.SAVED_RESTAURANTS.getFirst();
        DishCategoryEntity dishCategory = initializer.SAVED_DISH_CATEGORIES.getFirst();
        DishPhotoEntity dishPhoto1 = initializer.SAVED_DISH_PHOTOS.get(1);
        DishPhotoEntity dishPhoto2 = initializer.SAVED_DISH_PHOTOS.get(1);
        DishPhotoEntity dishPhoto3 = initializer.SAVED_DISH_PHOTOS.get(2);
        dish1.setRestaurant(restaurant);
        dish1.setDishCategory(dishCategory);
        dish1.setDishPhoto(dishPhoto1);
        dish2.setRestaurant(restaurant);
        dish2.setDishCategory(dishCategory);
        dish2.setDishPhoto(dishPhoto2);
        dish3.setRestaurant(restaurant);
        dish3.setDishCategory(dishCategory);
        dish3.setDishPhoto(dishPhoto3);

        dish1.setDishCode("XXX");
        dish2.setDishCode("YYY");
        dish3.setDishCode("ZZZ");

        //when
        dishJpaRepository.save(dish1);
        dishJpaRepository.save(dish2);
        dishJpaRepository.save(dish3);
        Page<DishEntity> page = restaurantJpaRepository.findActiveDishes(restaurant, pageable);
        List<DishEntity> activeDishes = page.getContent();

        //then
        assertTrue(activeDishes.size() >= 3);

        assertThat(activeDishes)
                .usingRecursiveFieldByFieldElementComparatorOnFields("dishCode")
                .contains(dish1, dish2, dish3);
    }

    @Test
    void isRestaurantsByRestaurantOwnerSelectedCorrectly() {
        // given
        RestaurantOwnerEntity restaurantOwner = initializer.SAVED_RESTAURANT_OWNERS.getFirst();

        // when
        List<RestaurantEntity> restaurants = restaurantJpaRepository.findByRestaurantOwner(restaurantOwner);

        //then
        assertFalse(restaurants.isEmpty());
    }

    private Pageable createPageable(Integer pageNumber, Integer objectsPerPage, String sortBy) {
        Sort sort = Sort.by(sortBy).descending();
        return PageRequest.of(pageNumber, objectsPerPage, sort);
    }

}