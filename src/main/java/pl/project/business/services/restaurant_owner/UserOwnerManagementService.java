package pl.project.business.services.restaurant_owner;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.project.business.dao.RestaurantOwnerDAO;
import pl.project.domain.exception.restaurant_owner.OwnerResourceReadException;
import pl.project.domain.exception.restaurant_owner.OwnerResourceUpdateException;
import pl.project.domain.model.RestaurantOwner;
import pl.project.infrastructure.security.ProjectUserDetailsService;

import java.util.Objects;

import static pl.project.domain.exception.ExceptionMessages.*;

@Service
@AllArgsConstructor
public class UserOwnerManagementService {

    private final RestaurantOwnerDAO restaurantOwnerDAO;
    private final ProjectUserDetailsService projectUserDetailsService;

    @Transactional
    public RestaurantOwner getRestaurantOwner(String restaurantOwnerEmail) {
        try {
            return restaurantOwnerDAO.findRestaurantOwnerByEmail(restaurantOwnerEmail)
                    .orElseThrow(RuntimeException::new);
        }
        catch (Exception e){
            throw new OwnerResourceReadException(RESOURCE_READ_EXCEPTION.formatted(RestaurantOwner.class.getSimpleName(), restaurantOwnerEmail), e);
        }
    }

    @Transactional
    public void modifyRestaurantOwnerPersonalData(RestaurantOwner updatedRestaurantOwner, String restaurantOwnerEmail) {
        try {
            String newEmail = updatedRestaurantOwner.getEmail();
            String newPhoneNumber = updatedRestaurantOwner.getPhoneNumber();

                if (Objects.nonNull(newEmail) && !newEmail.isEmpty()) {
                    restaurantOwnerDAO.changeRestaurantOwnerEmail(newEmail, restaurantOwnerEmail);
                    projectUserDetailsService.changeEmail(newEmail, restaurantOwnerEmail);

                }
                if (Objects.nonNull(newPhoneNumber) && !newPhoneNumber.isEmpty()) {
                    restaurantOwnerDAO.changeRestaurantOwnerPhoneNumber(newPhoneNumber, restaurantOwnerEmail);
                }
        }
        catch (Exception e){
            throw new OwnerResourceUpdateException(RESOURCE_MODIFICATION_EXCEPTION.formatted(RestaurantOwner.class.getSimpleName(), restaurantOwnerEmail), e);
        }
    }
}
