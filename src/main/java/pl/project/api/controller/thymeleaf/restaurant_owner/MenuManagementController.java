package pl.project.api.controller.thymeleaf.restaurant_owner;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pl.project.api.controller.addresses.RestaurantOwnerAddresses;
import pl.project.api.controller.exception.OwnerIncorrectInputException;
import pl.project.api.dto.DishCategoryDTO;
import pl.project.api.dto.DishDTO;
import pl.project.api.dto.RestaurantDTO;
import pl.project.api.dto.mapper.DishCategoryMapper;
import pl.project.api.dto.mapper.DishMapper;
import pl.project.api.dto.mapper.RestaurantMapper;
import pl.project.business.services.restaurant_owner.MenuManagementService;
import pl.project.business.services.restaurant_owner.RestaurantManagementService;
import pl.project.business.services.restaurant_owner.UserOwnerManagementService;
import pl.project.domain.model.Dish;
import pl.project.infrastructure.security.ProjectUserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static pl.project.api.controller.exception.ExceptionMessages.INCORRECT_INPUT_EXCEPTION;
import static pl.project.api.controller.exception.ExceptionMessages.getFailedFields;

@Controller
@AllArgsConstructor
@RequestMapping(RestaurantOwnerAddresses.MENU_MANAGEMENT)
public class MenuManagementController {

    static final String DISH_INFO = "/{dishCode}";
    static final String REDIRECT_MENU_MANAGEMENT = "redirect:%s".formatted(RestaurantOwnerAddresses.MENU_MANAGEMENT);
    static final String REDIRECT_DISH_INFO = "redirect:%s%s".formatted(RestaurantOwnerAddresses.MENU_MANAGEMENT, DISH_INFO);

    RestaurantManagementService restaurantManagementService;
    ProjectUserDetailsService projectUserDetailsService;
    UserOwnerManagementService userOwnerManagementService;
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

    @PostMapping
    public String addDish(
            MultipartFile dishPhoto,
            @Valid DishDTO dishDTO,
            BindingResult result
    ) {
        validateDishInput(dishPhoto, result);
        menuManagementService.createDish(dishMapper.mapFromDTO(dishDTO), dishPhoto);
        return REDIRECT_MENU_MANAGEMENT;
    }

    private void validateDishInput(MultipartFile dishPhoto, BindingResult result) {
        if (result.hasErrors()) {
            throw new OwnerIncorrectInputException(INCORRECT_INPUT_EXCEPTION
                    .formatted(getFailedFields(result), Dish.class.getSimpleName()));
        }
        validateDishPhotoInputContent(dishPhoto);
    }

    private static void validateDishPhotoInputContent(MultipartFile dishPhoto) {
        if (dishPhoto.getSize() > 5_242_880){
            throw new OwnerIncorrectInputException(INCORRECT_INPUT_EXCEPTION
                    .formatted("File is too big", Dish.class.getSimpleName()));
        }

        if (!Objects.requireNonNull(dishPhoto.getOriginalFilename()).isEmpty() && dishPhoto.getSize() == 0) {
            throw new OwnerIncorrectInputException(INCORRECT_INPUT_EXCEPTION
                    .formatted("File is empty", Dish.class.getSimpleName()));
        }

        if (!dishPhoto.isEmpty()) {
            if (Objects.isNull(dishPhoto.getContentType())) {
                throw new OwnerIncorrectInputException(INCORRECT_INPUT_EXCEPTION
                        .formatted("Unable to read file extension", Dish.class.getSimpleName()));
            }
            switch (dishPhoto.getContentType()) {
                case "image/jpeg":
                case "image/jpg":
                case "image/png":
                    break;
                default:
                    throw new OwnerIncorrectInputException(INCORRECT_INPUT_EXCEPTION
                            .formatted("File extension %s is not supported".formatted(dishPhoto.getContentType()), Dish.class.getSimpleName()));
            }
        }
    }

    @DeleteMapping
    public ModelAndView deactivateDish(
            ModelMap model,
            @RequestParam(value = "dishCode") String dishCode,
            @RequestParam(value = "restaurantCode") String restaurantCode
    ) {
        model.addAttribute("restaurantCode", restaurantCode);
        menuManagementService.deactivateDish(dishCode);
        return new ModelAndView(REDIRECT_MENU_MANAGEMENT, model);
    }

    @GetMapping(DISH_INFO)
    public ModelAndView dishUpdate(
            @PathVariable String dishCode
    ) {
        return new ModelAndView("restaurant_owner/dish_info",
                populateDishInfoWithData(dishCode));
    }

    private Map<String, ?> populateDishInfoWithData(String dishCode) {
        DishDTO dishDTO = dishMapper.mapToDTO(menuManagementService.getDishByDishCode(dishCode));
        List<DishCategoryDTO> dishCategories = menuManagementService.getDishCategories().stream()
                .map(dishCategoryMapper::mapToDTO)
                .toList();
        return Map.of(
                "dishCategoryDTO", DishCategoryDTO.builder().build(),
                "dishCategoryDTOs", dishCategories,
                "dishDTO", dishDTO
        );
    }

    @PatchMapping(DISH_INFO)
    public String updateDishName(
            MultipartFile updatePhoto,
            DishDTO dishDTO,
            @PathVariable String dishCode,
            BindingResult result
    ) {
        validateDishInput(updatePhoto, result);
        menuManagementService.updateDish(dishMapper.mapFromDTO(dishDTO), updatePhoto, dishCode);
        return REDIRECT_DISH_INFO;
    }

    private String getActiveUserEmail() {
        return projectUserDetailsService.getUserEmail(
                SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
