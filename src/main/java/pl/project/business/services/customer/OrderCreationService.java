package pl.project.business.services.customer;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.project.business.dao.*;
import pl.project.business.services.pageable.PageableService;
import pl.project.domain.exception.ExceptionMessages;
import pl.project.domain.exception.customer.NoAvailableDeliveryManException;
import pl.project.domain.exception.customer.CustomerResourceCreateException;
import pl.project.domain.exception.customer.CustomerResourceReadException;
import pl.project.domain.model.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.project.domain.exception.ExceptionMessages.RESOURCE_CREATION_EXCEPTION;
import static pl.project.domain.exception.ExceptionMessages.RESOURCE_READ_EXCEPTION;

@Service
@AllArgsConstructor
public class OrderCreationService {
    PageableService pageableService;
    DeliveryServiceDAO deliveryServiceDAO;
    DishCompositionDAO dishCompositionDAO;
    DeliveryManDAO deliveryManDAO;
    RestaurantDAO restaurantDAO;
    CustomerDAO customerDAO;
    OrderDAO orderDAO;
    DishDAO dishDAO;

    @Transactional
    public List<Restaurant> getRestaurantsForYourAddress(PageableProperties properties, String customerEmail) {
        try {
            DeliveryAddress deliveryAddress = customerDAO.findCustomerByEmail(customerEmail)
                    .orElseThrow(RuntimeException::new).getDeliveryAddress();
            Pageable pageable = pageableService.buildPageable(properties);
            return restaurantDAO.findActiveRestaurantsWithAddress(deliveryAddress, pageable);
        } catch (Exception e) {
            throw new CustomerResourceReadException(RESOURCE_READ_EXCEPTION
                    .formatted(Restaurant.class.getName(), properties), e);
        }
    }

    @Transactional
    public Integer countAllRestaurantsForYourAddress(String customerEmail) {
        try {
            DeliveryAddress deliveryAddress = customerDAO.findCustomerByEmail(customerEmail)
                    .orElseThrow(RuntimeException::new).getDeliveryAddress();
            return restaurantDAO.countAllActiveRestaurantsWithAddress(deliveryAddress);
        } catch (Exception e) {
            throw new CustomerResourceReadException(RESOURCE_READ_EXCEPTION
                    .formatted(Customer.class.getName(), customerEmail), e);
        }
    }

    @Transactional
    public List<Dish> getActiveDishesForRestaurant(String restaurantCode) {
        try {
            return dishDAO.findActiveDishesByRestaurant(restaurantCode);
        } catch (Exception e) {
            throw new CustomerResourceReadException(RESOURCE_READ_EXCEPTION.formatted(Dish.class.getSimpleName(), restaurantCode), e);
        }
    }

    @Transactional
    public Dish getDishByDishCode(String dishCode) {
        try {
            return dishDAO.findDishByDishCode(dishCode).orElseThrow(RuntimeException::new);
        } catch (Exception e) {
            throw new CustomerResourceReadException(RESOURCE_READ_EXCEPTION.formatted(Dish.class.getSimpleName(), dishCode), e);
        }
    }

    @Transactional
    public void registerOrder(Map<String, Integer> compositions, String orderCode, String customerEmail) {
        try {
            Map<Dish, Integer> dishCompositions = compositions.entrySet().stream()
                    .collect(Collectors.toMap(
                            entry -> dishDAO.findDishByDishCode(entry.getKey())
                                    .orElseThrow(RuntimeException::new),
                            Map.Entry::getValue
                    ));
            Customer customer = customerDAO.findCustomerByEmail(customerEmail)
                    .orElseThrow(RuntimeException::new);

            DeliveryService deliveryService = createDeliveryService();
            Order order = orderDAO.createOrder(
                            Order.builder()
                                    .orderCode(orderCode)
                                    .receivedDateTime(OffsetDateTime.now())
                                    .customer(customer)
                                    .deliveryService(deliveryService)
                                    .build()
                    )
                    .orElseThrow(RuntimeException::new);
            dishCompositions.forEach((key, value) -> dishCompositionDAO.createDishComposition(
                    DishComposition.builder()
                            .quantity(value)
                            .dish(key)
                            .order(order)
                            .build()
            ));

        } catch (Exception e) {
            if (e instanceof NoAvailableDeliveryManException){
                throw new NoAvailableDeliveryManException(ExceptionMessages.NO_AVAILABLE_DELIVERY_MAN_EXCEPTION);
            }
            throw new CustomerResourceCreateException(RESOURCE_CREATION_EXCEPTION
                    .formatted(Order.class.getSimpleName(),
                            "%s - %s".formatted(orderCode, "parameters were incorrect")), e);
        }
    }

    private DeliveryService createDeliveryService() {
        DeliveryMan deliveryMan = getAvailableDeliveryMan().withAvailable(false);
        deliveryManDAO.changeDeliveryManAvailabilityStatus(false, deliveryMan.getPersonalCode());
        return deliveryServiceDAO.createDeliveryService(
                        DeliveryService.builder()
                                .deliveryServiceCode(UUID.randomUUID().toString())
                                .receivedDateTime(OffsetDateTime.now())
                                .deliveryMan(deliveryMan)
                                .build()
                )
                .orElseThrow(RuntimeException::new);
    }

    private DeliveryMan getAvailableDeliveryMan() {
        List<DeliveryMan> deliveryMen = deliveryManDAO.getAvailableDeliveryMen();
        if (deliveryMen.isEmpty())
            throw new NoAvailableDeliveryManException();
        return deliveryMen.getFirst();
    }
}
