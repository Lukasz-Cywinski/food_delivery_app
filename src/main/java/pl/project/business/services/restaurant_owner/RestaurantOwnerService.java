package pl.project.business.services.restaurant_owner;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.project.business.dao.RestaurantOwnerDAO;
import pl.project.business.services.subsidiary.*;
import pl.project.domain.exception.restaurant_owner.OwnerResourceCreationException;
import pl.project.domain.exception.restaurant_owner.OwnerResourceReadException;
import pl.project.domain.model.*;
import pl.project.infrastructure.security.ProjectUserDetailsService;
import pl.project.infrastructure.security.User;

@Service
@AllArgsConstructor
public class RestaurantOwnerService {

    private final String RESTAURANT_OWNER_CREATION_EXCEPTION = "Fail to crete restaurant owner: %s";
    private final String RESTAURANT_OWNER_READ_EXCEPTION = "Fail to found restaurant owner by email: %s";

    private final RestaurantOwnerDAO restaurantOwnerDAO;
    private final ProjectUserDetailsService projectUserDetailsService;


    // Owner Services
    @Transactional
    public RestaurantOwner createRestaurantOwner(RestaurantOwner restaurantOwner) {
        User user = projectUserDetailsService.saveUserAndAssignRoles(restaurantOwner.getUser());
        return restaurantOwnerDAO.createRestaurantOwner(restaurantOwner.withUser(user))
                .orElseThrow(() -> new OwnerResourceCreationException(RESTAURANT_OWNER_CREATION_EXCEPTION.formatted(restaurantOwner.getEmail())));
    }

    @Transactional
    public RestaurantOwner getRestaurantOwner(String restaurantOwnerEmail) {
        User user = projectUserDetailsService.getUserAndRoleByEmail(restaurantOwnerEmail);
        return restaurantOwnerDAO.findRestaurantOwnerByEmail(restaurantOwnerEmail)
                .orElseThrow(() -> new OwnerResourceReadException(RESTAURANT_OWNER_READ_EXCEPTION.formatted(restaurantOwnerEmail)))
                .withUser(user);
    }

    @Transactional
    public boolean modifyRestaurantOwnerPersonalData(RestaurantOwner updatedRestaurantOwner, String restaurantOwnerEmail) {
        getRestaurantOwner(restaurantOwnerEmail);
        return (restaurantOwnerDAO.changeRestaurantOwnerPhoneNumber(updatedRestaurantOwner.getPhoneNumber(), restaurantOwnerEmail)
                + restaurantOwnerDAO.changeRestaurantOwnerEmail(updatedRestaurantOwner.getEmail(), restaurantOwnerEmail))
                > 0;
    }



}
