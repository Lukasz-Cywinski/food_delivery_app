package pl.project.business.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.project.business.dao.CustomerDAO;
import pl.project.business.services.subsidiary.*;
import pl.project.domain.exception.EntityCreationException;
import pl.project.domain.exception.EntityReadException;
import pl.project.domain.model.*;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final String CUSTOMER_CREATION_EXCEPTION = "Fail to crete customer: %s";
    private final String CUSTOMER_READ_EXCEPTION = "Fail to found customer by email: %s";

    private final CustomerDAO customerDAO;
    private final DishService dishService;
    private final OrderService orderService;
    private final RestaurantService restaurantService;
    private final DishOpinionService dishOpinionService;
    private final DeliveryAddressService deliveryAddressService;
    private final DishCompositionService dishCompositionService;
    private final DeliveryServiceService deliveryServiceService;


    // Customer Services
    @Transactional
    public Customer createCustomer(Customer customer) {
        return customerDAO.addCustomer(customer)
                .orElseThrow(() -> new EntityCreationException(CUSTOMER_CREATION_EXCEPTION.formatted(customer)));
    }

    @Transactional
    public boolean modifyCustomerPersonalData(Customer updatedCustomerData, String customerEmail) {
        getCustomer(customerEmail);
        return (customerDAO.changeCustomerName(updatedCustomerData.getName(), customerEmail)
                + customerDAO.changeCustomerSurname(updatedCustomerData.getSurname(), customerEmail)
                + customerDAO.changeCustomerPhoneNumber(updatedCustomerData.getPhoneNumber(), customerEmail)
                + customerDAO.changeCustomerEmail(updatedCustomerData.getEmail(), customerEmail))
                > 0;
    }

    @Transactional
    public boolean deactivateCustomer(String customerEmail){
        return customerDAO.deactivateCustomer(customerEmail) == 1;
    }

    private Customer getCustomer(String customerEmail) {
        return customerDAO.getCustomerByEmail(customerEmail)
                .orElseThrow(() -> new EntityReadException(CUSTOMER_READ_EXCEPTION.formatted(customerEmail)));
    }

    // Customer Delivery Services
    @Transactional
    public boolean modifyCustomerDeliveryAddress(DeliveryAddress deliveryAddress, String customerEmail) {
        return deliveryAddressService.modifyCustomerDeliveryAddress(deliveryAddress, getCustomer(customerEmail));
    }

    // Dish Opinion Services
    @Transactional
    public DishOpinion createDishOpinion(String customerEmail, String dishCode, String opinion, BigDecimal productEvaluation){
        Customer customer = getCustomer(customerEmail);
        Dish dish = dishService.getDish(dishCode);
        DishOpinion dishOpinion = DishOpinion.builder()
                .opinion(opinion)
                .productEvaluation(productEvaluation)
                .dish(dish)
                .customer(customer)
                .build();
        return dishOpinionService.createDishOpinion(dishOpinion);
    }

    // Order Services
    @Transactional
    public Order createOrder(String customerEmail, List<DishComposition> compositionsWithoutAssignedOrder){
        Order order = orderService.createOrder(getCustomer(customerEmail), deliveryServiceService.createDeliveryService());
        dishCompositionService.createDishCompositions(order, compositionsWithoutAssignedOrder);
        return order;
    }

    @Transactional
    public boolean cancelOrder(String orderCode){
        return orderService.cancelOrder(orderCode);
    }

    // Restaurant Service
    @Transactional
    public List<Restaurant> getRestaurants(PageableProperties pageableProperties){
        return restaurantService.getActiveRestaurants(pageableProperties);
    }

    // Dish Service
    @Transactional
    public List<Dish> getDishesFromRestaurant(Restaurant restaurant, PageableProperties pageableProperties){
        return restaurantService.getDishesFromRestaurant(restaurant, pageableProperties);
    }

}
