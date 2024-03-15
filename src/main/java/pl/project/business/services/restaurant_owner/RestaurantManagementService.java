package pl.project.business.services.restaurant_owner;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.project.business.dao.RestaurantDAO;
import pl.project.business.dao.RestaurantOwnerDAO;
import pl.project.business.dao.ServedAddressDAO;
import pl.project.domain.exception.restaurant_owner.OwnerResourceCreateException;
import pl.project.domain.exception.restaurant_owner.OwnerResourceDeleteException;
import pl.project.domain.exception.restaurant_owner.OwnerResourceReadException;
import pl.project.domain.model.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static pl.project.domain.exception.ExceptionMessages.*;

@Service
@AllArgsConstructor
public class RestaurantManagementService {

    private final RestaurantDAO restaurantDAO;
    private final RestaurantOwnerDAO restaurantOwnerDAO;
    private final ServedAddressDAO servedAddressDAO;

    @Transactional
    public Restaurant createRestaurant(String restaurantName, String restaurantOwnerEmail){
        try{
            RestaurantOwner restaurantOwner = restaurantOwnerDAO.findRestaurantOwnerByEmail(restaurantOwnerEmail)
                    .orElseThrow(RuntimeException::new);
            Restaurant restaurant = Restaurant.builder()
                    .restaurantCode(UUID.randomUUID().toString())
                    .name(restaurantName)
                    .added(OffsetDateTime.now())
                    .restaurantOwner(restaurantOwner)
                    .isActive(true)
                    .build();
            return restaurantDAO.createRestaurant(restaurant).orElseThrow(RuntimeException::new);
        }
        catch (Exception e){
            throw new OwnerResourceCreateException(RESOURCE_CREATION_EXCEPTION
                    .formatted(Restaurant.class.getName(), restaurantName), e);
        }
    }

    @Transactional
    public List<Restaurant> getRestaurantsByRestaurantOwner(String restaurantOwnerEmail) {
        try {
            RestaurantOwner restaurantOwner = restaurantOwnerDAO.findRestaurantOwnerByEmail(restaurantOwnerEmail)
                    .orElseThrow(RuntimeException::new);
            return restaurantDAO.findByRestaurantOwner(restaurantOwner);
        }
        catch (Exception e){
            throw new OwnerResourceReadException(RESOURCE_READ_EXCEPTION
                    .formatted(Restaurant.class.getName(), restaurantOwnerEmail), e);
        }
    }

    @Transactional
    public Restaurant getRestaurantByRestaurantCode(String restaurantCode){
        try {
            return restaurantDAO.findRestaurantByRestaurantCode(restaurantCode)
                    .orElseThrow(RuntimeException::new);
        }
        catch (Exception e){
            throw new OwnerResourceReadException(RESOURCE_READ_EXCEPTION
                    .formatted(Restaurant.class.getName(), restaurantCode), e);
        }
    }

    @Transactional
    public boolean deactivateRestaurant(String restaurantCode){
        try {
            return restaurantDAO.deactivateRestaurant(restaurantCode) == 1;
        }
        catch (Exception e){
            throw new OwnerResourceDeleteException(RESOURCE_DELETE_EXCEPTION
                    .formatted(Restaurant.class.getName(), restaurantCode), e);
        }
    }

    @Transactional
    public ServedAddress createServedAddress(ServedAddress servedAddress){
        try {
            return servedAddressDAO.createServedAddress(servedAddress).orElseThrow(RuntimeException::new);
        }
        catch (Exception e){
            throw new OwnerResourceCreateException(RESOURCE_CREATION_EXCEPTION
                    .formatted(ServedAddress.class.getName(), servedAddress), e);
        }
    }

    @Transactional
    public List<ServedAddress> getServedAddresses(String restaurantCode){
        try {
            Restaurant restaurant = restaurantDAO.findRestaurantByRestaurantCode(restaurantCode)
                    .orElseThrow(RuntimeException::new);
            return servedAddressDAO.getServedAddresses(restaurant);
        }
        catch (Exception e){
            throw new OwnerResourceReadException(RESOURCE_READ_EXCEPTION
                    .formatted(ServedAddress.class.getName(), restaurantCode), e);
        }
    }

    @Transactional
    public void deleteServedAddress(Integer servedAddressId){
        try {
            servedAddressDAO.deleteServedAddress(servedAddressId);
        }
        catch (Exception e){
            throw new OwnerResourceDeleteException(RESOURCE_DELETE_EXCEPTION
                    .formatted(ServedAddress.class.getName(), servedAddressId), e);
        }
    }
}

// TO DEAL LATER
//



//    private final PageableService pageableService;
//
//    // without transactional -> methods all called from another Transactional methods
//    public List<Restaurant> getActiveRestaurants(PageableProperties pageableProperties){
//        return restaurantDAO.findActiveRestaurants(pageableService.buildPageable(pageableProperties));
//    }
//
//    public List<Dish> getDishesFromRestaurant(Restaurant restaurant, PageableProperties pageableProperties){
//        return restaurantDAO.findActiveDishesForRestaurant(restaurant, pageableService.buildPageable(pageableProperties));
//    }

//    public Restaurant createRestaurant(Restaurant restaurant){
//        return restaurantDAO.createRestaurant(restaurant)
//                .orElseThrow(() -> new EntityCreationException(RESTAURANT_CREATION_EXCEPTION.formatted(restaurant)));
//    }

//    public boolean changeRestaurantName(String updatedRestaurantName, String restaurantCode) {
//        return restaurantDAO.changeRestaurantName(updatedRestaurantName, restaurantCode) == 1;
//    }
//
//    public boolean changeRestaurantOwner(RestaurantOwner newOwner, String restaurantCode) {
//        return restaurantDAO.changeRestaurantOwner(newOwner, restaurantCode) == 1;
//    }
