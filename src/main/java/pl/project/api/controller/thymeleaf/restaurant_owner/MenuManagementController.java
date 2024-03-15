package pl.project.api.controller.thymeleaf.restaurant_owner;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pl.project.api.controller.addresses.RestaurantOwnerAddresses;
import pl.project.api.controller.exception.ExceptionMessages;
import pl.project.api.controller.exception.OwnerIncorrectInputException;
import pl.project.api.dto.DishCategoryDTO;
import pl.project.api.dto.DishDTO;
import pl.project.api.dto.RestaurantDTO;
import pl.project.api.dto.mapper.DishCategoryMapper;
import pl.project.api.dto.mapper.DishMapper;
import pl.project.api.dto.mapper.RestaurantMapper;
import pl.project.business.services.restaurant_owner.MenuManagementService;
import pl.project.business.services.restaurant_owner.RestaurantManagementService;
import pl.project.business.services.restaurant_owner.RestaurantOwnerService;
import pl.project.domain.model.Dish;
import pl.project.domain.model.RestaurantOwner;
import pl.project.infrastructure.security.ProjectUserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static pl.project.api.controller.exception.ExceptionMessages.*;

@Controller
@AllArgsConstructor
@RequestMapping(RestaurantOwnerAddresses.MENU_MANAGEMENT)
public class MenuManagementController {

    static final String ADD_DISH = "/add_dish";
    static final String DEACTIVATE_DISH = "/deactivate_dish";
    static final String DISH_UPDATE = "/{dishCode}";
    static final String REDIRECT_MENU_MANAGEMENT = "redirect:%s".formatted(RestaurantOwnerAddresses.MENU_MANAGEMENT);

    RestaurantManagementService restaurantManagementService;
    ProjectUserDetailsService projectUserDetailsService;
    RestaurantOwnerService restaurantOwnerService;
    MenuManagementService menuManagementService;

    DishCategoryMapper dishCategoryMapper;
    RestaurantMapper restaurantMapper;
    DishMapper dishMapper;

    @GetMapping
    public ModelAndView dishManagement(
            String restaurantCode
    ) {
        return new ModelAndView("restaurant_owner/menu_management",
                populateMenuManagementWithData(restaurantCode));
    }

    private Map<String, ?> populateMenuManagementWithData(String restaurantCode) {
        List<RestaurantDTO> restaurants = restaurantManagementService
                .getRestaurantsByRestaurantOwner(getActiveUserEmail()).stream()
                .map(restaurantMapper::mapToDTO)
                .toList();
        List<DishCategoryDTO> dishCategories = menuManagementService.getDishCategories().stream()
                .map(dishCategoryMapper::mapToDTO)
                .toList();
        List<DishDTO> dishes = List.of();

        if (Objects.isNull(restaurantCode)) restaurantCode = "";
        else {
            dishes = menuManagementService.getActiveDishesForRestaurants(restaurantCode).stream()
                    .map(a -> dishMapper.mapToDTO(a))
                    .toList();
        }
        return Map.of(
                "dishDTOs", dishes,
                "dishDTO", DishDTO.builder().build(),
                "restaurantDTOs", restaurants,
                "dishCategoryDTO", DishCategoryDTO.builder().build(),
                "dishCategoryDTOs", dishCategories,
                "restaurantCode", restaurantCode
        );
    }

    @GetMapping(DISH_UPDATE)
    public ModelAndView dishUpdate(
            @PathVariable String dishCode
    ){
        System.out.println();
        return new ModelAndView("restaurant_owner/dishUpdate",
                populateDishUpdateWithData());
    }

    private Map<String, ?> populateDishUpdateWithData() {
        return Map.of(

        );
    }

    @PostMapping(ADD_DISH)
    public String addDish(
            MultipartFile dishPhoto,
            @Valid DishDTO dishDTO,
            BindingResult result
    ) {
        if(result.hasErrors()){
            throw new OwnerIncorrectInputException(OWNER_INCORRECT_INPUT_EXCEPTION
                    .formatted(getFailedFields(result), Dish.class.getSimpleName()));
        }
        menuManagementService.createDish(dishMapper.mapFromDTO(dishDTO), dishPhoto);
        return REDIRECT_MENU_MANAGEMENT;
    }

    @PostMapping(DEACTIVATE_DISH)
    public ModelAndView deactivateDish(
            ModelMap model,
            @RequestParam(value = "dishCode") String dishCode,
            @RequestParam(value = "restaurantCode") String restaurantCode
    ) {
        model.addAttribute("restaurantCode", restaurantCode);
        menuManagementService.deactivateDish(dishCode);
        return new ModelAndView(REDIRECT_MENU_MANAGEMENT, model);
    }

//    @PostMapping(MENU_MANAGEMENT_SHOW_DISHES)
//    public String showDishesForRestaurant(
////            String restaurantCode
////           @Valid @RequestParam RestaurantDTO restaurantDTO
////           @Valid @ModelAttribute("restaurantDTO") RestaurantDTO restaurantDTO,
//           @Valid RestaurantDTO restaurantDTO,
//           BindingResult result
//    ){
//        if(result.hasErrors()){
//            System.out.println(">>>>>>>>>>>>>>");
//            System.out.println(">>>>>>>>>>>>>>");
//            System.out.println("problem walidacji");
//            System.out.println(">>>>>>>>>>>>>>");
//            System.out.println(">>>>>>>>>>>>>>");
//        }
//        System.out.println();
//        return REDIRECT_MENU_MANAGEMENT;
//    }

    private String getActiveUserEmail() {
        return projectUserDetailsService.getUserEmail(
                SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
