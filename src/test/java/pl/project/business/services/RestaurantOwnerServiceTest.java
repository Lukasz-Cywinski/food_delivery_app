package pl.project.business.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.project.business.dao.RestaurantOwnerDAO;
import pl.project.business.services.subsidiary.*;
import pl.project.domain.exception.EntityCreationException;
import pl.project.domain.exception.EntityReadException;
import pl.project.domain.model.*;
import pl.project.infrastructure.security.ProjectUserDetailsService;
import pl.project.util.domain.InstanceMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static pl.project.util.db.DishInstance.someDish1;
import static pl.project.util.db.OrderInstance.someOrder1;
import static pl.project.util.db.RestaurantInstance.someRestaurant1;
import static pl.project.util.db.RestaurantOwnerInstance.someRestaurantOwner1;
import static pl.project.util.db.RestaurantOwnerInstance.someRestaurantOwner2;
import static pl.project.util.db.ServedAddressInstance.someServedAddress1;
import static pl.project.util.db.ServedAddressInstance.someServedAddress2;

@ExtendWith(MockitoExtension.class)
class RestaurantOwnerServiceTest {

    @InjectMocks
    private RestaurantOwnerService restaurantOwnerService;

    @Mock
    private RestaurantOwnerDAO restaurantOwnerDAO;
    @Mock
    private DishService dishService;
    @Mock
    private RestaurantService restaurantService;
    @Mock
    private ServedAddressService servedAddressService;
    @Mock
    private DishCompositionService dishCompositionService;
    @Mock
    private DeliveryServiceService deliveryServiceService;
    @Mock
    private ProjectUserDetailsService projectUserDetailsService;

    private final InstanceMapper instanceMapper = new InstanceMapper();

    @Test
    void createRestaurantOwnerTest() {
        //given
        RestaurantOwner restaurantOwner = instanceMapper.mapFromEntity(someRestaurantOwner1());
        RestaurantOwner anotherOwner = instanceMapper.mapFromEntity(someRestaurantOwner2());
        when(restaurantOwnerDAO.createRestaurantOwner(restaurantOwner)).thenReturn(Optional.of(restaurantOwner));

        //when
        RestaurantOwner result = restaurantOwnerService.createRestaurantOwner(restaurantOwner);
        Exception exception = assertThrows(EntityCreationException.class, () -> restaurantOwnerService.createRestaurantOwner(anotherOwner));

        //then
        assertEquals(restaurantOwner, result);
        assertInstanceOf(EntityCreationException.class, exception);
        assertEquals("Fail to crete restaurant owner: email2@mail.com", exception.getMessage());
    }

    @Test
    void modifyRestaurantOwnerPersonalDataTest() {
        //given
        RestaurantOwner restaurantOwner = instanceMapper.mapFromEntity(someRestaurantOwner1());
        RestaurantOwner updatedRestaurantOwner = instanceMapper.mapFromEntity(someRestaurantOwner2());

        String oldEmail = restaurantOwner.getEmail();
        String newPhoneNumber = updatedRestaurantOwner.getPhoneNumber();
        String newEmail = updatedRestaurantOwner.getEmail();

        when(restaurantOwnerDAO.changeRestaurantOwnerPhoneNumber(newPhoneNumber, oldEmail)).thenReturn(1);
        when(restaurantOwnerDAO.changeRestaurantOwnerEmail(newEmail, oldEmail)).thenReturn(1);
        when(restaurantOwnerDAO.findRestaurantOwnerByEmail(oldEmail)).thenReturn(Optional.of(restaurantOwner));

        //when
        boolean result = restaurantOwnerService.modifyRestaurantOwnerPersonalData(updatedRestaurantOwner, oldEmail);
        Exception exception = assertThrows(EntityReadException.class, () -> restaurantOwnerService.modifyRestaurantOwnerPersonalData(updatedRestaurantOwner, "fakemail@mail.com"));

        //then
        assertTrue(result);
        assertInstanceOf(EntityReadException.class, exception);
        assertEquals("Fail to found restaurant owner by email: fakemail@mail.com", exception.getMessage());
    }

    @Test
    void createRestaurantTest() {
        //given
        Restaurant restaurant = instanceMapper.mapFromEntity(someRestaurant1());
        when(restaurantService.createRestaurant(restaurant)).thenReturn(restaurant);

        //when
        Restaurant result = restaurantService.createRestaurant(restaurant);

        //then
        assertEquals(restaurant, result);
    }

    @Test
    void modifyRestaurantNameTest() {
        //given
        Restaurant restaurant = instanceMapper.mapFromEntity(someRestaurant1());
        String newRestaurantName = "new name";
        when(restaurantService.changeRestaurantName(newRestaurantName, restaurant.getRestaurantCode())).thenReturn(true);

        //when
        boolean result = restaurantService.changeRestaurantName(newRestaurantName, restaurant.getRestaurantCode());

        //then
        assertTrue(result);
    }

    @Test
    void changeRestaurantOwnerTest() {
        //given
        Restaurant restaurant = instanceMapper.mapFromEntity(someRestaurant1());
        RestaurantOwner newOwner = instanceMapper.mapFromEntity(someRestaurantOwner2());
        when(restaurantService.changeRestaurantOwner(newOwner, restaurant.getRestaurantCode())).thenReturn(true);

        //when
        boolean result = restaurantService.changeRestaurantOwner(newOwner, restaurant.getRestaurantCode());

        //then
        assertTrue(result);
    }

    @Test
    void deactivateRestaurantTest() {
        //given
        Restaurant restaurant = instanceMapper.mapFromEntity(someRestaurant1());
        when(restaurantService.deactivateRestaurant(restaurant)).thenReturn(true);

        //when
        boolean result = restaurantService.deactivateRestaurant(restaurant);

        //then
        assertTrue(result);
    }

    @Test
    void createServedAddressTest() {
        //given
        ServedAddress servedAddress = instanceMapper.mapFromEntity(someServedAddress1());
        when(servedAddressService.createServedAddress(servedAddress)).thenReturn(servedAddress);

        //when
        ServedAddress result = servedAddressService.createServedAddress(servedAddress);

        //then
        assertEquals(servedAddress, result);

    }

    @Test
    void getServedAddressesTest() {
        //given
        Restaurant restaurant = instanceMapper.mapFromEntity(someRestaurant1());
        ServedAddress servedAddress1 = instanceMapper.mapFromEntity(someServedAddress1()).withRestaurant(restaurant);
        ServedAddress servedAddress2 = instanceMapper.mapFromEntity(someServedAddress2()).withRestaurant(restaurant);
        when(servedAddressService.getServedAddresses(restaurant)).thenReturn(List.of(servedAddress1, servedAddress2));

        //when
        List<ServedAddress> servedAddresses = servedAddressService.getServedAddresses(restaurant);

        //then
        assertEquals(2, servedAddresses.size());
        assertThat(servedAddresses).contains(servedAddress1, servedAddress2);

    }

    @Test
    void deleteServedAddressTest() {
        //given
        ServedAddress servedAddress = instanceMapper.mapFromEntity(someServedAddress1());
        when(servedAddressService.deleteServedAddress(servedAddress)).thenReturn(true);

        //when
        boolean result = servedAddressService.deleteServedAddress(servedAddress);

        //then
        assertTrue(result);

    }

    @Test
    void getActiveOrdersForRestaurantTest() {
        //given
        Restaurant restaurant = instanceMapper.mapFromEntity(someRestaurant1());
        Order order1 = instanceMapper.mapFromEntity(someOrder1()).withCompletedDateTime(null);
        Order order2 = order1.withOrderCode("differentCode").withId(100).withCompletedDateTime(null);
        when(dishCompositionService.getActiveOrdersForRestaurant(restaurant))
                .thenReturn(List.of(order1, order2));

        //when
        List<Order> activeOrders = dishCompositionService.getActiveOrdersForRestaurant(restaurant);

        //then
        assertEquals(2, activeOrders.size());
        assertThat(activeOrders).contains(order1, order2);
    }

    @Test
    void getRealizedOrdersForRestaurantTest() {
        //given
        Restaurant restaurant = instanceMapper.mapFromEntity(someRestaurant1());
        Order order1 = instanceMapper.mapFromEntity(someOrder1());
        Order order2 = order1.withOrderCode("differentCode").withId(100);
        when(dishCompositionService.getActiveOrdersForRestaurant(restaurant))
                .thenReturn(List.of(order1, order2));

        //when
        List<Order> completedOrders = dishCompositionService.getActiveOrdersForRestaurant(restaurant);

        //then
        assertEquals(2, completedOrders.size());
        assertThat(completedOrders).contains(order1, order2);
    }

    @Test
    void createDishTest() {
        //given
        Dish dish = instanceMapper.mapFromEntity(someDish1());
        when(dishService.createDish(dish)).thenReturn(dish);

        //when
        Dish result = dishService.createDish(dish);

        //then
        assertEquals(dish, result);

    }

    @Test
    void modifyDishDataTest() {
        //given
        Dish dish = instanceMapper.mapFromEntity(someDish1());
        Dish updatedDish = dish.withDishCode("new code").withName("new name");
        when(dishService.modifyDishData(updatedDish, dish.getDishCode())).thenReturn(true);

        //when
        boolean result = dishService.modifyDishData(updatedDish, dish.getDishCode());

        //then
        assertTrue(result);

    }

    @Test
    void deactivateDishTest() {
        //given
        Dish dish = instanceMapper.mapFromEntity(someDish1());
        when(dishService.deactivateDish(dish.getDishCode())).thenReturn(true);

        //when
        boolean result = dishService.deactivateDish(dish.getDishCode());

        //then
        assertTrue(result);

    }

    @Test
    void notifyFinishedOrderTimeForDeliveryServiceTest() {
        //given
        Order order = instanceMapper.mapFromEntity(someOrder1());
        when(deliveryServiceService.deliverOrder(order)).thenReturn(true);

        //when
        boolean result = deliveryServiceService.deliverOrder(order);

        //then
        assertTrue(result);
    }
}