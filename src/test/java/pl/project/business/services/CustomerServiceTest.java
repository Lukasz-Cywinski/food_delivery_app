package pl.project.business.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.project.business.dao.CustomerDAO;
import pl.project.business.services.subsidiary.*;
import pl.project.domain.exception.EntityCreationException;
import pl.project.domain.exception.EntityReadException;
import pl.project.domain.model.*;
import pl.project.util.db.DeliveryServiceInstance;
import pl.project.util.domain.InstanceMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static pl.project.util.db.CustomerInstance.*;
import static pl.project.util.db.DeliveryAddressInstance.someDeliveryAddress2;
import static pl.project.util.db.DishCompositionInstance.*;
import static pl.project.util.db.DishInstance.*;
import static pl.project.util.db.DishOpinionInstance.someDishOpinion1;
import static pl.project.util.db.OrderInstance.someOrder1;
import static pl.project.util.db.RestaurantInstance.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;

    @Mock
    private CustomerDAO customerDAO;
    @Mock
    private DishService dishService;
    @Mock
    private OrderService orderService;
    @Mock
    private RestaurantService restaurantService;
    @Mock
    private DishOpinionService dishOpinionService;
    @Mock
    private DeliveryAddressService deliveryAddressService;
    @Mock
    private DeliveryServiceService deliveryServiceService;
    @Mock
    private DishCompositionService dishCompositionService;

    private final InstanceMapper instanceMapper = new InstanceMapper();

    @Test
    void createCustomerTest() {
        //given
        Customer customer = instanceMapper.mapFromEntity(someCustomer1());
        Customer anotherCustomer = instanceMapper.mapFromEntity(someCustomer2());
        when(customerDAO.addCustomer(customer)).thenReturn(Optional.of(customer));

        //when
        Customer result = customerService.createCustomer(customer);
        Exception exception = assertThrows(EntityCreationException.class, () -> customerService.createCustomer(anotherCustomer));

        //then
        assertEquals(customer, result);
        assertInstanceOf(EntityCreationException.class, exception);
        assertEquals("Fail to crete customer: Customer(name=name2, surname=surname2, phoneNumber=222, email=email2@mail.com)"
                , exception.getMessage());
    }

    @Test
    void modifyCustomerDeliveryAddressTest() {
        //given
        Customer customer = instanceMapper.mapFromEntity(someCustomer1());
        DeliveryAddress updatedDeliveryAddress = instanceMapper.mapFromEntity(someDeliveryAddress2());
        when(deliveryAddressService.modifyCustomerDeliveryAddress(updatedDeliveryAddress, customer)).thenReturn(true);
        when(customerDAO.getCustomerByEmail(customer.getEmail())).thenReturn(Optional.of(customer)).thenReturn(Optional.of(customer));

        //when
        boolean result = customerService.modifyCustomerDeliveryAddress(updatedDeliveryAddress, customer.getEmail());

        //then
        assertTrue(result);
    }

    @Test
    void modifyCustomerPersonalDataTest() {
        //given
        Customer customer = instanceMapper.mapFromEntity(someCustomer1());
        Customer updatedCustomer = instanceMapper.mapFromEntity(someCustomer2());
        Customer notExpected = instanceMapper.mapFromEntity(someCustomer3());

        when(customerDAO.getCustomerByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        when(customerDAO.changeCustomerName(updatedCustomer.getName(), customer.getEmail())).thenReturn(1);
        when(customerDAO.changeCustomerSurname(updatedCustomer.getSurname(), customer.getEmail())).thenReturn(1);
        when(customerDAO.changeCustomerPhoneNumber(updatedCustomer.getPhoneNumber(), customer.getEmail())).thenReturn(1);
        when(customerDAO.changeCustomerEmail(updatedCustomer.getEmail(), customer.getEmail())).thenReturn(1);

        //when
        boolean result = customerService.modifyCustomerPersonalData(updatedCustomer, customer.getEmail());
        Exception exception = assertThrows(EntityReadException.class, () -> customerService.modifyCustomerPersonalData(notExpected, "someEmail@mail.com"));

        //then
        assertTrue(result);
        assertInstanceOf(EntityReadException.class, exception);
        assertEquals("Fail to found customer by email: someEmail@mail.com", exception.getMessage());
    }

    @Test
    void createDishOpinionTest() {
        //given
        Customer customer = instanceMapper.mapFromEntity(someCustomer1());
        Dish dish = instanceMapper.mapFromEntity(someDish1());
        DishOpinion dishOpinion = instanceMapper.mapFromEntity(someDishOpinion1()).withCustomer(customer).withDish(dish);

        when(customerDAO.getCustomerByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        when(dishService.getDish(dish.getDishCode())).thenReturn(dish);
        when(dishOpinionService.createDishOpinion(dishOpinion)).thenReturn(dishOpinion);

        //when
        DishOpinion result = customerService
                .createDishOpinion(customer.getEmail(), dish.getDishCode(), dishOpinion.getOpinion(), dishOpinion.getProductEvaluation());

        //then
        assertEquals(dishOpinion, result);
    }

    @Test
    void createOrderTest() {
        //given
        Customer customer = instanceMapper.mapFromEntity(someCustomer1());
        DeliveryService deliveryService = instanceMapper.mapFromEntity(DeliveryServiceInstance.someDeliveryService1());
        Order order = instanceMapper.mapFromEntity(someOrder1()).withDeliveryService(deliveryService);
        DishComposition dishComposition1 = instanceMapper.mapFromEntity(someDishComposition1()).withOrder(null);
        DishComposition dishComposition2 = instanceMapper.mapFromEntity(someDishComposition2()).withOrder(null);
        DishComposition dishComposition3 = instanceMapper.mapFromEntity(someDishComposition3()).withOrder(null);
        List<DishComposition> dishCompositions = List.of(dishComposition1, dishComposition2, dishComposition3);

        when(customerDAO.getCustomerByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        when(deliveryServiceService.createDeliveryService()).thenReturn(deliveryService);
        when(orderService.createOrder(customer, deliveryService)).thenReturn(order);

        //when
        Order result = customerService.createOrder(customer.getEmail(), dishCompositions);

        //then
        assertEquals(order, result);
    }

    @Test
    void cancelOrderTest() {
        //given
        Order order = instanceMapper.mapFromEntity(someOrder1());
        when(orderService.cancelOrder(order.getOrderCode())).thenReturn(true);

        //when
        boolean result = orderService.cancelOrder(order.getOrderCode());

        //then
        assertTrue(result);
    }

    @Test
    void getRestaurantsTest() {
        //given
        Restaurant restaurant1 = instanceMapper.mapFromEntity(someRestaurant1());
        Restaurant restaurant2 = instanceMapper.mapFromEntity(someRestaurant2());
        Restaurant restaurant3 = instanceMapper.mapFromEntity(someRestaurant3());
        List<Restaurant> restaurants = List.of(restaurant1, restaurant2, restaurant3);
        PageableProperties pageableProperties = PageableProperties.builder()
                .pageNumber(0)
                .objectsPerPage(5)
                .sortedBy("name")
                .isAscending(true)
                .build();
        when(restaurantService.getActiveRestaurants(pageableProperties)).thenReturn(restaurants);

        //when
        List<Restaurant> result = customerService.getRestaurants(pageableProperties);

        //then
        assertEquals(3, result.size());
        assertThat(result)
                .contains(restaurant1, restaurant2, restaurant3);
    }

    @Test
    void getDishesFromRestaurantTest() {
        //given
        Restaurant restaurant = instanceMapper.mapFromEntity(someRestaurant1());
        Dish dish1 = instanceMapper.mapFromEntity(someDish1()).withRestaurant(restaurant);
        Dish dish2 = instanceMapper.mapFromEntity(someDish2()).withRestaurant(restaurant);
        Dish dish3 = instanceMapper.mapFromEntity(someDish3()).withRestaurant(restaurant);
        List<Dish> dishes = List.of(dish1, dish2, dish3);
        PageableProperties pageableProperties = PageableProperties.builder()
                .pageNumber(0)
                .objectsPerPage(5)
                .sortedBy("price")
                .isAscending(true)
                .build();
        when(restaurantService.getDishesFromRestaurant(restaurant, pageableProperties)).thenReturn(dishes);

        //when
        List<Dish> result = customerService.getDishesFromRestaurant(restaurant, pageableProperties);

        //then
        assertEquals(3, result.size());
        assertThat(result)
                .contains(dish1, dish2, dish3);
    }

    @Test
    void deactivateCustomerTest() {
        //given
        Customer customer = instanceMapper.mapFromEntity(someCustomer1());
        when(customerDAO.deactivateCustomer(customer.getEmail())).thenReturn(1);

        //when
        boolean result = customerService.deactivateCustomer(customer.getEmail());

        //then
        assertTrue(result);
    }
}