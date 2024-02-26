package pl.project.business.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.project.business.dao.RestaurantOwnerDAO;
import pl.project.business.services.subsidiary.*;
import pl.project.domain.exception.EntityCreationException;
import pl.project.domain.exception.EntityReadException;
import pl.project.domain.model.*;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class RestaurantOwnerService {

    private final String RESTAURANT_OWNER_CREATION_EXCEPTION = "Fail to crete restaurant owner: %s";
    private final String RESTAURANT_OWNER_READ_EXCEPTION = "Fail to found restaurant owner by email: %s";

    private final RestaurantOwnerDAO restaurantOwnerDAO;
    private final DishService dishService;
    private final DishPhotoService dishPhotoService;
    private final RestaurantService restaurantService;
    private final ServedAddressService servedAddressService;
    private final DishCompositionService dishCompositionService;
    private final DeliveryServiceService deliveryServiceService;


    // Owner Services
    @Transactional
    public RestaurantOwner createRestaurantOwner(RestaurantOwner restaurantOwner){
        return restaurantOwnerDAO.createRestaurantOwner(restaurantOwner)
                .orElseThrow(() -> new EntityCreationException(RESTAURANT_OWNER_CREATION_EXCEPTION.formatted(restaurantOwner.getEmail())));
    }

    private RestaurantOwner getRestaurantOwner(String restaurantOwnerEmail) {
        return restaurantOwnerDAO.findRestaurantOwnerByEmail(restaurantOwnerEmail)
                .orElseThrow(() -> new EntityReadException(RESTAURANT_OWNER_READ_EXCEPTION.formatted(restaurantOwnerEmail)));
    }

    @Transactional
    public boolean modifyRestaurantOwnerPersonalData(RestaurantOwner updatedRestaurantOwner, String restaurantOwnerEmail){
        getRestaurantOwner(restaurantOwnerEmail);
        return (restaurantOwnerDAO.changeRestaurantOwnerPhoneNumber(updatedRestaurantOwner.getPhoneNumber(), restaurantOwnerEmail)
                + restaurantOwnerDAO.changeRestaurantOwnerEmail(updatedRestaurantOwner.getEmail(), restaurantOwnerEmail))
                > 0;
    }

    // Restaurant Services
    @Transactional
    public Restaurant createRestaurant(Restaurant restaurant){
        return restaurantService.createRestaurant(restaurant);
    }

    @Transactional
    public boolean modifyRestaurantName(String updatedRestaurantName, String restaurantCode){
        return restaurantService.changeRestaurantName(updatedRestaurantName, restaurantCode);
    }

    @Transactional
    public  boolean changeRestaurantOwner(RestaurantOwner newOwner, String restaurantCode){
        return restaurantService.changeRestaurantOwner(newOwner, restaurantCode);
    }

    @Transactional
    public boolean deactivateRestaurant(Restaurant restaurant){
        return  restaurantService.deactivateRestaurant(restaurant);
    }

    // Served Address Services
    @Transactional
    public ServedAddress createServedAddress(ServedAddress servedAddress){
        return servedAddressService.createServedAddress(servedAddress);
    }

    @Transactional
    public List<ServedAddress> getServedAddresses(Restaurant restaurant){
        return servedAddressService.getServedAddresses(restaurant);
    }

    @Transactional
    public boolean deleteServedAddress(ServedAddress servedAddress){
        return servedAddressService.deleteServedAddress(servedAddress);
    }

    // Order Services
    @Transactional
    public List<Order> getActiveOrdersForRestaurant(Restaurant restaurant){
        return dishCompositionService.getActiveOrdersForRestaurant(restaurant);
    }

    @Transactional
    public List<Order> getRealizedOrdersForRestaurant(Restaurant restaurant){
        return dishCompositionService.getRealizedOrdersForRestaurant(restaurant);
    }

    // Dish Services
    @Transactional
    public Dish createDish(Dish dish){
        if(Objects.nonNull(dish.getDishPhoto())){
            dishPhotoService.createDishPhoto(dish.getDishPhoto());
        }
        return dishService.createDish(dish);
    }

    @Transactional
    public boolean modifyDishData(Dish updatedDish, String dishCode){
        return dishService.modifyDishData(updatedDish, dishCode);
    }

    @Transactional
    public boolean deactivateDish(String dishCode){
        return dishService.deactivateDish(dishCode);
    }

    // Delivery Service
    @Transactional
    public boolean notifyFinishedOrderTimeForDeliveryService(Order order){
        return deliveryServiceService.deliverOrder(order);
    }
}
