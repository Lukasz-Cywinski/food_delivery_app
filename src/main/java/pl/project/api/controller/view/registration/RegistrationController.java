package pl.project.api.controller.view.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.project.api.controller.exception.RegistrationIncorrectInputException;
import pl.project.api.dto.*;
import pl.project.api.dto.mapper.*;
import pl.project.business.services.registration.RegistrationService;
import pl.project.infrastructure.security.Role;

import static pl.project.api.controller.addresses.HomeAddresses.*;
import static pl.project.api.controller.exception.ExceptionMessages.INCORRECT_INPUT_EXCEPTION;
import static pl.project.api.controller.exception.ExceptionMessages.getFailedFields;

@Controller
@AllArgsConstructor
@RequestMapping(REGISTRATION_HOME)
public class RegistrationController {

    private final RegistrationService registrationService;
    private final RestaurantOwnerMapper restaurantOwnerMapper;
    private final UserMapper userMapper;
    private final CustomerMapper customerMapper;
    private final DeliveryManMapper deliveryManMapper;
    private final DeliveryAddressMapper deliveryAddressMapper;

    static final String NEW_RESTAURANT_OWNER = "/new_restaurant_owner";
    static final String NEW_CUSTOMER = "/new_customer";
    static final String NEW_DELIVERY_MAN = "/new_delivery_man";
    static final String NEW_REST_API_USER = "/new_rest_api_user";

    static final String REDIRECT_RESTAURANT_OWNER = "redirect:%s".formatted(RESTAURANT_OWNER_HOME);
    static final String REDIRECT_CUSTOMER = "redirect:%s".formatted(CUSTOMER_HOME);
    static final String REDIRECT_DELIVERY_MAN = "redirect:%s".formatted(DELIVERY_MAN_HOME);
    static final String REDIRECT_REST_API = "redirect:%s".formatted(REST_API_HOME);


    @GetMapping()
    public String registrationHome() {
        return "registration/registration_home";
    }

    @GetMapping(value = NEW_RESTAURANT_OWNER)
    public String registryNewRestaurantOwnerPanel() {
        return "registration/new_restaurant_owner";
    }

    @GetMapping(value = NEW_CUSTOMER)
    public String registryNewCustomerPanel() {
        return "registration/new_customer";
    }

    @GetMapping(value = NEW_DELIVERY_MAN)
    public String registryNewDeliveryManPanel() {
        return "registration/new_delivery_man";
    }

    @GetMapping(value = NEW_REST_API_USER)
    public String registryNewRestApiUserPanel() {
        return "registration/new_rest_api_user";
    }


    @PostMapping(value = NEW_CUSTOMER)
    public String registryNewCustomer(
            UserDTO userDTO,
            BindingResult resultUser,
            CustomerDTO customerDTO,
            BindingResult resultCustomer,
            DeliveryAddressDTO deliveryAddressDTO,
            BindingResult resultDeliveryAddress
    ) {
        validateCustomerInput(resultUser, resultCustomer, resultDeliveryAddress);
        registrationService.createCustomer(
                customerMapper.mapFromDTO(customerDTO),
                userMapper.mapFromDTO(userDTO),
                deliveryAddressMapper.mapFromDTO(deliveryAddressDTO));
        return REDIRECT_CUSTOMER;
    }

    private void validateCustomerInput(BindingResult resultUser, BindingResult resultCustomer, BindingResult resultDeliveryAddress) {
        if (resultCustomer.hasErrors()) {
            throw new RegistrationIncorrectInputException(INCORRECT_INPUT_EXCEPTION
                    .formatted(getFailedFields(resultCustomer), Role.CUSTOMER.name()));
        }
        if (resultUser.hasErrors()) {
            throw new RegistrationIncorrectInputException(INCORRECT_INPUT_EXCEPTION
                    .formatted(getFailedFields(resultUser), Role.CUSTOMER.name()));
        }
        if (resultDeliveryAddress.hasErrors()) {
            throw new RegistrationIncorrectInputException(INCORRECT_INPUT_EXCEPTION
                    .formatted(getFailedFields(resultDeliveryAddress), Role.CUSTOMER.name()));
        }
    }

    @PostMapping(value = NEW_RESTAURANT_OWNER)
    public String registryNewRestaurantOwner(
            UserDTO userDTO,
            BindingResult resultUser,
            RestaurantOwnerDTO restaurantOwnerDTO,
            BindingResult resultRestaurantOwner
    ) {
        validateRestaurantOwnerInput(resultUser, resultRestaurantOwner);
        registrationService.createRestaurantOwner(restaurantOwnerMapper.mapFromDTO(restaurantOwnerDTO),
                userMapper.mapFromDTO(userDTO));
        return REDIRECT_RESTAURANT_OWNER;
    }

    private void validateRestaurantOwnerInput(BindingResult resultUser, BindingResult resultRestaurantOwner) {
        if (resultRestaurantOwner.hasErrors()) {
            throw new RegistrationIncorrectInputException(INCORRECT_INPUT_EXCEPTION
                    .formatted(getFailedFields(resultRestaurantOwner), Role.RESTAURANT_OWNER.name()));
        }
        if (resultUser.hasErrors()) {
            throw new RegistrationIncorrectInputException(INCORRECT_INPUT_EXCEPTION
                    .formatted(getFailedFields(resultUser), Role.RESTAURANT_OWNER.name()));
        }
    }

    @PostMapping(value = NEW_DELIVERY_MAN)
    public String registryNewDeliveryMan(
            UserDTO userDTO,
            BindingResult resultUser,
            DeliveryManDTO deliveryManDTO,
            BindingResult resultDeliveryMan

    ){
        validateDeliveryManUser(resultUser, resultDeliveryMan);
        registrationService.createDeliveryMan(
                deliveryManMapper.mapFromDTO(deliveryManDTO),
                userMapper.mapFromDTO(userDTO));
        return REDIRECT_DELIVERY_MAN;
    }

    private void validateDeliveryManUser(BindingResult resultUser, BindingResult resultDeliveryMan) {
        if (resultDeliveryMan.hasErrors()) {
            throw new RegistrationIncorrectInputException(INCORRECT_INPUT_EXCEPTION
                    .formatted(getFailedFields(resultDeliveryMan), Role.DELIVERY_MAN.name()));
        }
        if (resultUser.hasErrors()) {
            throw new RegistrationIncorrectInputException(INCORRECT_INPUT_EXCEPTION
                    .formatted(getFailedFields(resultUser), Role.DELIVERY_MAN.name()));
        }
    }

    @PostMapping(value = NEW_REST_API_USER)
    public String registryNewRestApiUser(
            UserDTO userDTO,
            BindingResult resultUser
    ) {
        validateRestApiUserInput(resultUser);
        registrationService.createRestApiUser(userMapper.mapFromDTO(userDTO));
        return REDIRECT_REST_API;
    }

    private void validateRestApiUserInput(BindingResult resultUser) {
        if (resultUser.hasErrors()) {
            throw new RegistrationIncorrectInputException(INCORRECT_INPUT_EXCEPTION
                    .formatted(getFailedFields(resultUser), Role.REST_API.name()));
        }
    }
}
