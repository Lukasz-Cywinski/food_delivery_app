package pl.project.api.controller.thymeleaf.restaurant_owner;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.project.api.controller.exception.OwnerIncorrectInputException;
import pl.project.api.dto.RestaurantOwnerDTO;
import pl.project.api.dto.mapper.RestaurantOwnerMapper;
import pl.project.business.services.restaurant_owner.UserManagementService;
import pl.project.domain.model.RestaurantOwner;
import pl.project.infrastructure.security.ProjectUserDetailsService;

import java.util.Map;

import static pl.project.api.controller.addresses.RestaurantOwnerAddresses.USER_MANAGEMENT;
import static pl.project.api.controller.exception.ExceptionMessages.INCORRECT_INPUT_EXCEPTION;
import static pl.project.api.controller.exception.ExceptionMessages.getFailedFields;

@Controller
@AllArgsConstructor
@RequestMapping(USER_MANAGEMENT)
public class UserManagementController {

    static final String REDIRECT_MENU_MANAGEMENT = "redirect:%s".formatted(USER_MANAGEMENT);

    ProjectUserDetailsService projectUserDetailsService;
    UserManagementService userManagementService;
    RestaurantOwnerMapper restaurantOwnerMapper;

    @GetMapping
    public ModelAndView userManagement() {
        return new ModelAndView("restaurant_owner/user_management",
                populateUserManagementWithData());
    }

    private Map<String, ?> populateUserManagementWithData() {
        RestaurantOwnerDTO restaurantOwnerDTO = restaurantOwnerMapper.mapToDTO(userManagementService.getRestaurantOwner(getActiveUserEmail()));
        return Map.of(
                "restaurantOwnerDTO", restaurantOwnerDTO
        );
    }

    @PatchMapping
    public String updateUserPersonalData(
            String oldEmail,
            RestaurantOwnerDTO restaurantOwnerDTO,
            BindingResult result
    ){
        validateRestaurantOwnerInput(result);
        userManagementService.modifyRestaurantOwnerPersonalData(restaurantOwnerMapper.mapFromDTO(restaurantOwnerDTO), oldEmail);
        return REDIRECT_MENU_MANAGEMENT;
    }

    private void validateRestaurantOwnerInput(BindingResult result) {
        if (result.hasErrors()) {
            throw new OwnerIncorrectInputException(INCORRECT_INPUT_EXCEPTION
                    .formatted(getFailedFields(result), RestaurantOwner.class.getSimpleName()));
        }
        // maybe some validation - checking existing entities in DB
    }

    private String getActiveUserEmail() {
        return projectUserDetailsService.getUserEmail(
                SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
