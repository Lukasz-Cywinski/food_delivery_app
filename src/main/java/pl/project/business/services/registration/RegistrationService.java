package pl.project.business.services.registration;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.project.business.dao.CustomerDAO;
import pl.project.business.dao.DeliveryManDAO;
import pl.project.business.dao.RestaurantOwnerDAO;
import pl.project.domain.exception.registration.RegistrationException;
import pl.project.domain.model.*;
import pl.project.infrastructure.security.ProjectUserDetailsService;
import pl.project.infrastructure.security.Role;
import pl.project.infrastructure.security.User;

import java.util.Set;
import java.util.UUID;

import static pl.project.domain.exception.ExceptionMessages.REGISTRATION_EXCEPTION;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final CustomerDAO customerDAO;
    private final DeliveryManDAO deliveryManDAO;
    private final RestaurantOwnerDAO restaurantOwnerDAO;
    private final ProjectUserDetailsService projectUserDetailsService;

    @Transactional
    public RestaurantOwner createRestaurantOwner(RestaurantOwner restaurantOwner, User user) {
        try {
            user = projectUserDetailsService.saveUserAndAssignRoles(user
                    .withPassword(encoder.encode(user.getPassword()))
                    .withRoles(Set.of(Role.RESTAURANT_OWNER))
                    .withActive(true));
            return restaurantOwnerDAO.createRestaurantOwner(restaurantOwner
                            .withUser(user)
                            .withActive(true))
                    .orElseThrow(RuntimeException::new);
        } catch (Exception e) {
            throw new RegistrationException(REGISTRATION_EXCEPTION.formatted(user.getEmail()), e);
        }
    }

    @Transactional
    public Customer createCustomer(Customer customer, User user, DeliveryAddress deliveryAddress){
        try {
            user = projectUserDetailsService.saveUserAndAssignRoles(user
                    .withPassword(encoder.encode(user.getPassword()))
                    .withRoles(Set.of(Role.CUSTOMER))
                    .withActive(true));
            return customerDAO.createCustomer(customer
                            .withUser(user)
                            .withDeliveryAddress(deliveryAddress)
                            .withActive(true))
                    .orElseThrow(RuntimeException::new);
        }
        catch (Exception e){
            throw new RegistrationException(REGISTRATION_EXCEPTION.formatted(user.getEmail()), e);
        }
    }

    @Transactional
    public DeliveryMan createDeliveryMan(DeliveryMan deliveryMan, User user){
        try {
            user = projectUserDetailsService.saveUserAndAssignRoles(user
                    .withPassword(encoder.encode(user.getPassword()))
                    .withRoles(Set.of(Role.DELIVERY_MAN))
                    .withActive(true));
            return deliveryManDAO.createDeliveryMan(deliveryMan
                            .withPersonalCode(UUID.randomUUID().toString())
                            .withUser(user)
                            .withAvailable(true)
                            .withActive(true))
                    .orElseThrow(RuntimeException::new);
        } catch (Exception e) {
            throw new RegistrationException(REGISTRATION_EXCEPTION.formatted(user.getEmail()), e);
        }
    }

    @Transactional
    public User createRestApiUser(User user){
        try {
            return projectUserDetailsService.saveUserAndAssignRoles(user
                    .withPassword(encoder.encode(user.getPassword()))
                    .withRoles(Set.of(Role.REST_API))
                    .withActive(true));
        }
        catch (Exception e){
            throw new RegistrationException(REGISTRATION_EXCEPTION.formatted(user.getEmail()), e);
        }
    }
}
