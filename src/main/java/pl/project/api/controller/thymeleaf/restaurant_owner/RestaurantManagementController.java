package pl.project.api.controller.thymeleaf.restaurant_owner;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.project.api.controller.addresses.RestaurantOwnerAddresses;
import pl.project.api.controller.exception.ExceptionMessages;
import pl.project.api.controller.exception.OwnerIncorrectInputException;
import pl.project.api.dto.RestaurantDTO;
import pl.project.api.dto.ServedAddressDTO;
import pl.project.api.dto.mapper.RestaurantMapper;
import pl.project.api.dto.mapper.ServedAddressMapper;
import pl.project.business.services.restaurant_owner.RestaurantManagementService;
import pl.project.business.services.restaurant_owner.RestaurantOwnerService;
import pl.project.domain.exception.restaurant_owner.OwnerResourceCreateException;
import pl.project.domain.model.Restaurant;
import pl.project.domain.model.ServedAddress;
import pl.project.infrastructure.security.ProjectUserDetailsService;

import java.util.List;
import java.util.Map;

import static pl.project.api.controller.exception.ExceptionMessages.getFailedFields;
import static pl.project.domain.exception.ExceptionMessages.*;

@Controller
@AllArgsConstructor
@RequestMapping(RestaurantOwnerAddresses.RESTAURANT_MANAGEMENT)
public class RestaurantManagementController {

    static final String ADD_RESTAURANT = "/add_restaurant";
    static final String DEACTIVATE_RESTAURANT = "/deactivate_restaurant";
    static final String RESTAURANT_UPDATE = "/{restaurantCode}";
    static final String RESTAURANT_ADD_SERVED_ADDRESS = "/add_servedAddress";
    static final String RESTAURANT_DELETE_SERVED_ADDRESS = "/delete_servedAddress";
    // Redirect
    static final String REDIRECT_RESTAURANT_MANAGEMENT = "redirect:%s".formatted(RestaurantOwnerAddresses.RESTAURANT_MANAGEMENT);

    private final ProjectUserDetailsService projectUserDetailsService;
    RestaurantOwnerService restaurantOwnerService;
    RestaurantManagementService restaurantManagementService;
    RestaurantMapper restaurantMapper;
    ServedAddressMapper servedAddressMapper;

    @GetMapping
    public ModelAndView restaurantManagement() {
        return new ModelAndView("restaurant_owner/restaurant_management",
                populateRestaurantManagementWithData());
    }

    private Map<String, ?> populateRestaurantManagementWithData() {
        List<RestaurantDTO> restaurants = restaurantManagementService.getRestaurantsByRestaurantOwner(getActiveUserEmail()).stream()
                .map(restaurantMapper::mapToDTO)
                .toList();
        return Map.of(
                "restaurantDTOs", restaurants
        );
    }

    @GetMapping(RESTAURANT_UPDATE)
    public ModelAndView updateRestaurant(
            @PathVariable String restaurantCode
    ) {
        return new ModelAndView("restaurant_owner/restaurant_update",
                populateRestaurantUpdateWithData(restaurantCode));
    }

    private Map<String, ?> populateRestaurantUpdateWithData(String restaurantCode) {
        RestaurantDTO restaurantDTO = restaurantMapper.mapToDTO(
                restaurantManagementService.getRestaurantByRestaurantCode(restaurantCode));
        List<ServedAddressDTO> servedAddresses = restaurantManagementService.getServedAddresses(restaurantCode).stream()
                .map(servedAddressMapper::mapToDTO)
                .toList();
        return Map.of(
                "restaurantDTO", restaurantDTO,
                "servedAddressesDTOs", servedAddresses
        );
    }

    @PostMapping(RESTAURANT_ADD_SERVED_ADDRESS)
    public String addServedAddress(
           @Valid ServedAddressDTO servedAddressDTO,
            BindingResult result,
            String restaurantCode
    ){
        if(result.hasErrors()){
            throw new OwnerIncorrectInputException(ExceptionMessages.OWNER_INCORRECT_INPUT_EXCEPTION
                    .formatted(getFailedFields(result), ServedAddress.class.getSimpleName()));
        }
        Restaurant restaurant = restaurantManagementService.getRestaurantByRestaurantCode(restaurantCode);
        ServedAddress servedAddress = servedAddressMapper.mapFromDTO(servedAddressDTO).withRestaurant(restaurant);
        restaurantManagementService.createServedAddress(servedAddress);
        return REDIRECT_RESTAURANT_MANAGEMENT + "/%s".formatted(restaurantCode);
    }

    @DeleteMapping(RESTAURANT_DELETE_SERVED_ADDRESS)
    public String deleteServedAddress(
            Integer servedAddressId,
            String restaurantCode
    ){
        restaurantManagementService.deleteServedAddress(servedAddressId);
        return REDIRECT_RESTAURANT_MANAGEMENT + "/%s".formatted(restaurantCode);
    }

    @PostMapping(ADD_RESTAURANT)
    public String addRestaurant(
            @RequestParam(value = "restaurantName") String restaurantName
    ) {
        restaurantManagementService.createRestaurant(restaurantName, getActiveUserEmail());
        return REDIRECT_RESTAURANT_MANAGEMENT;
    }

    @PostMapping(DEACTIVATE_RESTAURANT)
    public String deactivateRestaurant(
            @RequestParam(value = "restaurantCode") String restaurantCode
    ) {
        restaurantManagementService.deactivateRestaurant(restaurantCode);
        return REDIRECT_RESTAURANT_MANAGEMENT;
    }

    private String getActiveUserEmail() {
        return projectUserDetailsService.getUserEmail(
                SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
