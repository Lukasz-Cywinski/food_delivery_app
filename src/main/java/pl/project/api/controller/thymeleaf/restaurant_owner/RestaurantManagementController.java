package pl.project.api.controller.thymeleaf.restaurant_owner;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.project.api.controller.exception.ExceptionMessages;
import pl.project.api.controller.exception.OwnerIncorrectInputException;
import pl.project.api.dto.RestaurantDTO;
import pl.project.api.dto.ServedAddressDTO;
import pl.project.api.dto.mapper.RestaurantMapper;
import pl.project.api.dto.mapper.ServedAddressMapper;
import pl.project.business.services.restaurant_owner.RestaurantManagementService;
import pl.project.business.services.restaurant_owner.UserManagementService;
import pl.project.domain.model.Restaurant;
import pl.project.domain.model.ServedAddress;
import pl.project.infrastructure.security.ProjectUserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pl.project.api.controller.addresses.RestaurantOwnerAddresses.*;
import static pl.project.api.controller.exception.ExceptionMessages.INCORRECT_INPUT_EXCEPTION;
import static pl.project.api.controller.exception.ExceptionMessages.getFailedFields;

@Controller
@AllArgsConstructor
@RequestMapping(RESTAURANT_MANAGEMENT)
public class RestaurantManagementController {

    static final String RESTAURANT_INFO = "/{restaurantCode}";
    static final String REDIRECT_RESTAURANT_MANAGEMENT = "redirect:%s".formatted(RESTAURANT_MANAGEMENT);

    private final ProjectUserDetailsService projectUserDetailsService;
    UserManagementService userManagementService;
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

    @GetMapping(RESTAURANT_INFO)
    public ModelAndView updateRestaurant(
            @PathVariable String restaurantCode
    ) {
        return new ModelAndView("restaurant_owner/restaurant_info",
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

    @PostMapping(RESTAURANT_INFO)
    public String addServedAddress(
           @Valid ServedAddressDTO servedAddressDTO,
            BindingResult result,
            @PathVariable String restaurantCode
    ){
        if(result.hasErrors()){
            throw new OwnerIncorrectInputException(ExceptionMessages.INCORRECT_INPUT_EXCEPTION
                    .formatted(getFailedFields(result), ServedAddress.class.getSimpleName()));
        }
        Restaurant restaurant = restaurantManagementService.getRestaurantByRestaurantCode(restaurantCode);
        ServedAddress servedAddress = servedAddressMapper.mapFromDTO(servedAddressDTO).withRestaurant(restaurant);
        restaurantManagementService.createServedAddress(servedAddress);
        return REDIRECT_RESTAURANT_MANAGEMENT + "/%s".formatted(restaurantCode);
    }

    @DeleteMapping(RESTAURANT_INFO)
    public String deleteServedAddress(
            Integer servedAddressId,
            @PathVariable String restaurantCode
    ){
        restaurantManagementService.deleteServedAddress(servedAddressId);
        return REDIRECT_RESTAURANT_MANAGEMENT + "/%s".formatted(restaurantCode);
    }

    @PostMapping
    public String addRestaurant(
            @RequestParam(value = "restaurantName") String restaurantName
    ) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9_]{1,32}$");
        Matcher matcher = pattern.matcher(restaurantName);
        if(!matcher.find()){
            throw new OwnerIncorrectInputException(INCORRECT_INPUT_EXCEPTION
                    .formatted(restaurantName, Restaurant.class.getSimpleName()));
        }
        restaurantManagementService.createRestaurant(restaurantName, getActiveUserEmail());
        return REDIRECT_RESTAURANT_MANAGEMENT;
    }

    @DeleteMapping
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
