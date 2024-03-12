package pl.project.api.controller.thymeleaf.restaurant_owner;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pl.project.api.controller.addresses.RestaurantOwnerAddresses;
import pl.project.api.dto.DishCategoryDTO;
import pl.project.api.dto.DishDTO;
import pl.project.api.dto.RestaurantDTO;
import pl.project.api.dto.mapper.DishCategoryMapper;
import pl.project.api.dto.mapper.DishMapper;
import pl.project.api.dto.mapper.RestaurantMapper;
import pl.project.business.services.RestaurantOwnerService;
import pl.project.domain.model.RestaurantOwner;
import pl.project.infrastructure.security.ProjectUserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@AllArgsConstructor
@RequestMapping(RestaurantOwnerAddresses.MENU_MANAGEMENT)
public class MenuManagementController {

    static final String ADD_DISH = "/add_dish";
    static final String REDIRECT_MENU_MANAGEMENT = "redirect:%s".formatted(RestaurantOwnerAddresses.MENU_MANAGEMENT);

    ProjectUserDetailsService projectUserDetailsService;
    RestaurantOwnerService restaurantOwnerService;
    RestaurantMapper restaurantMapper;
    DishCategoryMapper dishCategoryMapper;
    DishMapper dishMapper;

    @GetMapping
    public ModelAndView dishManagement(
            String restaurantCode
    ) {
        return new ModelAndView("restaurant_owner/menu_management",
                populateMenuManagementWithData(restaurantCode));
    }

    private Map<String, ?> populateMenuManagementWithData(String restaurantCode) {
        RestaurantOwner restaurantOwner = restaurantOwnerService.getRestaurantOwner(getActiveUserEmail());
        List<RestaurantDTO> restaurants = restaurantOwnerService.getRestaurantsByRestaurantOwner(restaurantOwner).stream()
                .map(restaurantMapper::mapToDTO)
                .toList();
        List<DishCategoryDTO> dishCategories = restaurantOwnerService.getDishCategories().stream()
                .map(dishCategoryMapper::mapToDTO)
                .toList();
        List<DishDTO> dishes = List.of();

        if (Objects.isNull(restaurantCode)) restaurantCode = "";
        else {
            dishes = restaurantOwnerService.getActiveDishesForRestaurants(restaurantCode).stream()
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

    @PostMapping(ADD_DISH)
    public String addDish(
            MultipartFile dishPhoto,
            DishDTO dishDTO
    ){
        restaurantOwnerService.createDish(dishMapper.mapFromDTO(dishDTO), dishPhoto);
        return REDIRECT_MENU_MANAGEMENT;
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
