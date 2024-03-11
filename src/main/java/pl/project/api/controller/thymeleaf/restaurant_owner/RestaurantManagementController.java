package pl.project.api.controller.thymeleaf.restaurant_owner;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.project.api.controller.addresses.RestaurantOwnerAddresses;
import pl.project.api.dto.RestaurantDTO;
import pl.project.api.dto.ServedAddressDTO;
import pl.project.api.dto.mapper.RestaurantMapper;
import pl.project.api.dto.mapper.ServedAddressMapper;
import pl.project.business.services.RestaurantOwnerService;
import pl.project.domain.model.RestaurantOwner;
import pl.project.infrastructure.security.ProjectUserDetailsService;

import java.util.List;
import java.util.Map;

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
    RestaurantMapper restaurantMapper;
    ServedAddressMapper servedAddressMapper;

    @GetMapping
    public ModelAndView restaurantManagement() {
        return new ModelAndView("restaurant_owner/restaurant_management",
                populateRestaurantManagementWithData());
    }

    private Map<String, ?> populateRestaurantManagementWithData() {
        RestaurantOwner restaurantOwner = restaurantOwnerService.getRestaurantOwner(getActiveUserEmail());
        List<RestaurantDTO> restaurants = restaurantOwnerService.getRestaurantsByRestaurantOwner(restaurantOwner).stream()
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
                restaurantOwnerService.getRestaurantByRestaurantCode(restaurantCode));
        List<ServedAddressDTO> servedAddresses = restaurantOwnerService.getServedAddresses(restaurantCode).stream()
                .map(servedAddressMapper::mapToDTO)
                .toList();
        return Map.of(
                "restaurantDTO", restaurantDTO,
                "servedAddressesDTOs", servedAddresses
        );
    }

    @PostMapping(RESTAURANT_ADD_SERVED_ADDRESS)
    public String addServedAddress(
            ServedAddressDTO servedAddressDTO,
            RestaurantDTO restaurantDTO
    ){
        servedAddressDTO.setRestaurant(restaurantDTO);
        restaurantOwnerService.createServedAddress(servedAddressMapper.mapFromDTO(servedAddressDTO));
        return REDIRECT_RESTAURANT_MANAGEMENT + "/%s".formatted(restaurantDTO.getRestaurantCode());
    }

    @DeleteMapping(RESTAURANT_DELETE_SERVED_ADDRESS)
    public String deleteServedAddress(
            ServedAddressDTO servedAddressDTO,
            String restaurantCode
    ){
        restaurantOwnerService.deleteServedAddress(servedAddressMapper.mapFromDTO(servedAddressDTO));
        return REDIRECT_RESTAURANT_MANAGEMENT + "/%s".formatted(restaurantCode);
    }

    @PostMapping(ADD_RESTAURANT)
    public String addRestaurant(
            @RequestParam(value = "restaurantName") String restaurantName
    ) {
        restaurantOwnerService.createRestaurant(restaurantName, getActiveUserEmail());
        return REDIRECT_RESTAURANT_MANAGEMENT;
    }

    @PostMapping(DEACTIVATE_RESTAURANT)
    public String deactivateRestaurant(
            @RequestParam(value = "restaurantCode") String restaurantCode
    ) {
        restaurantOwnerService.deactivateRestaurant(restaurantCode);
        return REDIRECT_RESTAURANT_MANAGEMENT;
    }

    private String getActiveUserEmail() {
        return projectUserDetailsService.getUserEmail(
                SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
