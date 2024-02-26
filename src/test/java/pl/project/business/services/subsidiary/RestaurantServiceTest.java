package pl.project.business.services.subsidiary;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pl.project.business.dao.RestaurantDAO;
import pl.project.business.services.subsidiary.pageable.PageableService;
import pl.project.domain.exception.EntityCreationException;
import pl.project.domain.model.Dish;
import pl.project.domain.model.PageableProperties;
import pl.project.domain.model.Restaurant;
import pl.project.domain.model.RestaurantOwner;
import pl.project.util.domain.InstanceMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static pl.project.util.db.DishInstance.someDish1;
import static pl.project.util.db.DishInstance.someDish2;
import static pl.project.util.db.RestaurantInstance.someRestaurant1;
import static pl.project.util.db.RestaurantInstance.someRestaurant2;
import static pl.project.util.db.RestaurantOwnerInstance.someRestaurantOwner2;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantDAO restaurantDAO;
    @Mock
    private PageableService pageableService;

    @InjectMocks
    private RestaurantService restaurantService;

    InstanceMapper instanceMapper = new InstanceMapper();

    @Test
    void getActiveRestaurantsTest() {
        //given
        Restaurant restaurant1 = instanceMapper.mapFromEntity(someRestaurant1());
        Restaurant restaurant2 = instanceMapper.mapFromEntity(someRestaurant2());
        List<Restaurant> restaurants = List.of(restaurant1, restaurant2);
        PageableProperties pageableProperties = PageableProperties.builder()
                .pageNumber(0)
                .objectsPerPage(5)
                .sortedBy("name")
                .isAscending(true)
                .build();
        Pageable pageable = PageRequest.of(0, 5, Sort.by("name").ascending());
        when(pageableService.buildPageable(pageableProperties)).thenReturn(pageable);
        when(restaurantDAO.findActiveRestaurants(pageable)).thenReturn(restaurants);

        //when
        List<Restaurant> result = restaurantService.getActiveRestaurants(pageableProperties);

        //then
        assertEquals(2, result.size());
        Assertions.assertThat(result).contains(restaurant1, restaurant2);
    }

    @Test
    void getDishesFromRestaurantTest() {
        //given
        Restaurant restaurant = instanceMapper.mapFromEntity(someRestaurant1());
        Dish dish1 = instanceMapper.mapFromEntity(someDish1()).withRestaurant(restaurant);
        Dish dish2 = instanceMapper.mapFromEntity(someDish2()).withRestaurant(restaurant);
        List<Dish> dishes = List.of(dish1, dish2);
        PageableProperties pageableProperties = PageableProperties.builder()
                .pageNumber(0)
                .objectsPerPage(5)
                .sortedBy("name")
                .isAscending(true)
                .build();
        Pageable pageable = PageRequest.of(0, 5, Sort.by("name").ascending());
        when(pageableService.buildPageable(pageableProperties)).thenReturn(pageable);
        when(restaurantDAO.findActiveDishesForRestaurant(restaurant, pageable)).thenReturn(dishes);

        //when
        List<Dish> result = restaurantService.getDishesFromRestaurant(restaurant, pageableProperties);

        //then
        assertEquals(2, result.size());
        Assertions.assertThat(result).contains(dish1, dish2);
    }

    @Test
    void createRestaurantTest() {
        //given
        Restaurant restaurant = instanceMapper.mapFromEntity(someRestaurant1());
        Restaurant anotherRestaurant = instanceMapper.mapFromEntity(someRestaurant2());
        when(restaurantDAO.createRestaurant(restaurant)).thenReturn(Optional.of(restaurant));

        //when
        Restaurant result = restaurantService.createRestaurant(restaurant);
        Exception exception = assertThrows(EntityCreationException.class, () -> restaurantService.createRestaurant(anotherRestaurant));

        //then
        assertEquals(restaurant, result);
        assertInstanceOf(EntityCreationException.class, exception);
        assertEquals("Fail to crete restaurant: Restaurant(name=name2, added=2024-02-10T16:30:10.000000010Z)",
                exception.getMessage());
    }

    @Test
    void deactivateRestaurantTest() {
        //given
        Restaurant restaurant = instanceMapper.mapFromEntity(someRestaurant1());
        when(restaurantDAO.deactivateRestaurant(restaurant.getRestaurantCode())).thenReturn(1);

        //when
        boolean result = restaurantService.deactivateRestaurant(restaurant);

        //then
        assertTrue(result);
    }

    @Test
    void changeRestaurantNameTest() {
        //given
        Restaurant restaurant = instanceMapper.mapFromEntity(someRestaurant1());
        Restaurant updatedRestaurant = instanceMapper.mapFromEntity(someRestaurant2());
        when(restaurantDAO.changeRestaurantName(updatedRestaurant.getName(), restaurant.getRestaurantCode())).thenReturn(1);

        //when
        boolean result = restaurantService.changeRestaurantName(updatedRestaurant.getName(), restaurant.getRestaurantCode());

        //then
        assertTrue(result);
    }

    @Test
    void changeRestaurantOwnerTest() {
        //given
        RestaurantOwner restaurantOwner = instanceMapper.mapFromEntity(someRestaurantOwner2());
        Restaurant restaurant = instanceMapper.mapFromEntity(someRestaurant1());
        when(restaurantDAO.changeRestaurantOwner(restaurantOwner, restaurant.getRestaurantCode())).thenReturn(1);

        //when
        boolean result = restaurantService.changeRestaurantOwner(restaurantOwner, restaurant.getRestaurantCode());

        //then
        assertTrue(result);
    }
}