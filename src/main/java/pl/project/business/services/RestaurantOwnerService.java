package pl.project.business.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.project.api.controller.addresses.ResourcePaths;
import pl.project.business.dao.RestaurantOwnerDAO;
import pl.project.business.services.subsidiary.*;
import pl.project.domain.exception.EntityCreationException;
import pl.project.domain.exception.EntityReadException;
import pl.project.domain.model.*;
import pl.project.infrastructure.security.ProjectUserDetailsService;
import pl.project.infrastructure.security.User;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
    private final DishCategoryService dishCategoryService;

    private final ProjectUserDetailsService projectUserDetailsService;


    // Owner Services
    @Transactional
    public RestaurantOwner createRestaurantOwner(RestaurantOwner restaurantOwner) {
        User user = projectUserDetailsService.saveUserAndAssignRoles(restaurantOwner.getUser());
        return restaurantOwnerDAO.createRestaurantOwner(restaurantOwner.withUser(user))
                .orElseThrow(() -> new EntityCreationException(RESTAURANT_OWNER_CREATION_EXCEPTION.formatted(restaurantOwner.getEmail())));
    }

    @Transactional
    public RestaurantOwner getRestaurantOwner(String restaurantOwnerEmail) {
        User user = projectUserDetailsService.getUserAndRoleByEmail(restaurantOwnerEmail);
        return restaurantOwnerDAO.findRestaurantOwnerByEmail(restaurantOwnerEmail)
                .orElseThrow(() -> new EntityReadException(RESTAURANT_OWNER_READ_EXCEPTION.formatted(restaurantOwnerEmail)))
                .withUser(user);
    }

    @Transactional
    public boolean modifyRestaurantOwnerPersonalData(RestaurantOwner updatedRestaurantOwner, String restaurantOwnerEmail) {
        getRestaurantOwner(restaurantOwnerEmail);
        return (restaurantOwnerDAO.changeRestaurantOwnerPhoneNumber(updatedRestaurantOwner.getPhoneNumber(), restaurantOwnerEmail)
                + restaurantOwnerDAO.changeRestaurantOwnerEmail(updatedRestaurantOwner.getEmail(), restaurantOwnerEmail))
                > 0;
    }

    @Transactional
    // TODO - tests
    public Restaurant getRestaurantByRestaurantCode(String restaurantCode) {
        return restaurantService.getRestaurantByRestaurantCode(restaurantCode);
    }

    @Transactional
    public Restaurant createRestaurant(String restaurantName, String restaurantOwnerEmail) {
        return restaurantService.createRestaurant(restaurantName,
                restaurantOwnerDAO.findRestaurantOwnerByEmail(restaurantOwnerEmail)
                        .orElseThrow(() -> new EntityReadException(RESTAURANT_OWNER_READ_EXCEPTION.formatted(restaurantOwnerEmail))));
    }

    @Transactional
    public boolean modifyRestaurantName(String updatedRestaurantName, String restaurantCode) {
        return restaurantService.changeRestaurantName(updatedRestaurantName, restaurantCode);
    }

    @Transactional
    public boolean changeRestaurantOwner(RestaurantOwner newOwner, String restaurantCode) {
        return restaurantService.changeRestaurantOwner(newOwner, restaurantCode);
    }

    @Transactional
    public boolean deactivateRestaurant(String restaurantCode) {
        return restaurantService.deactivateRestaurant(restaurantCode);
    }

    @Transactional
    public List<Restaurant> getRestaurantsByRestaurantOwner(RestaurantOwner restaurantOwner) {
        return restaurantService.getRestaurantsByRestaurantOwner(restaurantOwner);
    }

    // Served Address Services
    @Transactional
    public ServedAddress createServedAddress(ServedAddress servedAddress) {
        return servedAddressService.createServedAddress(servedAddress);
    }

    @Transactional
    public List<ServedAddress> getServedAddresses(String restaurantCode) {
        Restaurant restaurant = restaurantService.getRestaurantByRestaurantCode(restaurantCode);
        return servedAddressService.getServedAddresses(restaurant);
    }

    @Transactional
    public boolean deleteServedAddress(ServedAddress servedAddress) {
        return servedAddressService.deleteServedAddress(servedAddress);
    }

    // Order Services
    @Transactional
    public List<Order> getActiveOrdersForRestaurant(Restaurant restaurant) {
        return dishCompositionService.getActiveOrdersForRestaurant(restaurant);
    }

    @Transactional
    public List<Order> getRealizedOrdersForRestaurant(Restaurant restaurant) {
        return dishCompositionService.getRealizedOrdersForRestaurant(restaurant);
    }

    // Dish Services
    @Transactional
    public Dish createDish(Dish dish, MultipartFile dishPhotoContent) {
//        TODO - test
        DishPhoto dishPhoto = dishPhotoService.createDishPhoto(dishPhotoContent);
        return dishService.createDish(dish
                .withDishPhoto(dishPhoto)
                .withDishCode(UUID.randomUUID().toString())
                .withActive(true));
    }

    @Transactional
    public List<Dish> getActiveDishesForRestaurants(String restaurantCode) {
        //TODO -test
        return dishService.getActiveDishesForRestaurants(restaurantCode);
    }

    @Transactional
    public boolean modifyDishData(Dish updatedDish, String dishCode) {
        return dishService.modifyDishData(updatedDish, dishCode);
    }

    @Transactional
    public boolean deactivateDish(String dishCode) {
        return dishService.deactivateDish(dishCode);
    }

    // Delivery Service
    @Transactional
    public boolean notifyFinishedOrderTimeForDeliveryService(Order order) {
        return deliveryServiceService.deliverOrder(order);
    }

    @Transactional
    public List<DishCategory> getDishCategories() {
        //TODO -test
        return dishCategoryService.getDishCategories();
    }


}
