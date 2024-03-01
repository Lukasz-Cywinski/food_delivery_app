package pl.project.api.controller.thymeleaf.registration;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.project.api.controller.addresses.HomeAddresses;
import pl.project.api.dto.CustomerDTO;
import pl.project.api.dto.DeliveryAddressDTO;
import pl.project.api.dto.RestaurantOwnerDTO;
import pl.project.api.dto.UserDTO;
import pl.project.api.dto.mapper.CustomerMapper;
import pl.project.api.dto.mapper.DeliveryAddressMapper;
import pl.project.api.dto.mapper.RestaurantOwnerMapper;
import pl.project.api.dto.mapper.UserMapper;
import pl.project.business.services.CustomerService;
import pl.project.business.services.RestaurantOwnerService;
import pl.project.domain.model.Customer;
import pl.project.domain.model.DeliveryAddress;
import pl.project.domain.model.RestaurantOwner;
import pl.project.infrastructure.security.ProjectUserDetailsService;
import pl.project.infrastructure.security.Role;
import pl.project.infrastructure.security.User;

import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping(HomeAddresses.REGISTRATION_HOME)
public class RegistrationController {

    private final CustomerService customerService;
    private final RestaurantOwnerService restaurantOwnerService;
    private final CustomerMapper customerMapper;
    private final RestaurantOwnerMapper restaurantOwnerMapper;
    private final UserMapper userMapper;
    private final DeliveryAddressMapper deliveryAddressMapper;
    private final ProjectUserDetailsService projectUserDetailsService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    static final String CREATE_NEW_RESTAURANT_OWNER = "/create_new_restaurant_owner";
    static final String CREATE_NEW_CUSTOMER = "/create_new_customer";
    static final String CREATE_NEW_DELIVERY_MAN = "/create_new_delivery_man";
    static final String CREATE_NEW_REST_API_USER = "/create_new_rest_api_user";
    static final String ADD = "/add";

    @GetMapping()
    public String registrationHome() {
        return "registration/registration_main_page";
    }

    @GetMapping(value = CREATE_NEW_RESTAURANT_OWNER)
    public String registryNewRestaurantOwnerPanel(Model model) {
        return "registration/create_new_restaurant_owner";
    }

    @GetMapping(value = CREATE_NEW_CUSTOMER)
    public String registryNewCustomerPanel(Model model) {
        return "registration/create_new_customer";
    }

    @GetMapping(value = CREATE_NEW_DELIVERY_MAN)
    public String registryNewDeliveryManPanel() {
        return "registration/create_new_delivery_man";
    }

    @GetMapping(value = CREATE_NEW_REST_API_USER)
    public String registryNewRestApiUserPanel() {
        return "registration/create_new_rest_api_user";
    }


    @PostMapping(value = CREATE_NEW_CUSTOMER + ADD)
    public String registryNewCustomer(
            UserDTO userDTO,
            CustomerDTO customerDTO,
            DeliveryAddressDTO deliveryAddressDTO
    ) {
        User user = userMapper.mapFromDTO(userDTO)
                .withPassword(encoder.encode(userDTO.getPassword()))
                .withRoles(Set.of(Role.CUSTOMER))
                .withActive(true);
        DeliveryAddress deliveryAddress = deliveryAddressMapper.mapFromDTO(deliveryAddressDTO);
        Customer customer = customerMapper.mapFromDTO(customerDTO)
                .withUser(user)
                .withDeliveryAddress(deliveryAddress)
                .withActive(true);
        customerService.createCustomer(customer);
        return "redirect:/customer";
    }

    @PostMapping(value = CREATE_NEW_RESTAURANT_OWNER + ADD)
    public String registryNewRestaurantOwner(
            UserDTO userDTO,
            RestaurantOwnerDTO restaurantOwnerDTO
    ) {
        User user = userMapper.mapFromDTO(userDTO)
                .withPassword(encoder.encode(userDTO.getPassword()))
                .withRoles(Set.of(Role.RESTAURANT_OWNER))
                .withActive(true);
        RestaurantOwner restaurantOwner = restaurantOwnerMapper.mapFromDTO(restaurantOwnerDTO)
                .withUser(user)
                .withActive(true);
        restaurantOwnerService.createRestaurantOwner(restaurantOwner);
        return "redirect:/restaurant_owner";
    }

    @PostMapping(value = CREATE_NEW_REST_API_USER + ADD)
    public String registryNewRestApiUser(
            UserDTO userDTO
    ) {
        User user = userMapper.mapFromDTO(userDTO)
                .withRoles(Set.of(Role.REST_API))
                .withPassword(encoder.encode(userDTO.getPassword()))
                .withActive(true);
        projectUserDetailsService.saveUserAndAssignRoles(user);
        return "redirect:/rest_api";
    }
}
