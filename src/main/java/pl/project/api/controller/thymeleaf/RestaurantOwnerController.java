package pl.project.api.controller.thymeleaf;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.project.api.controller.addresses.HomeAddresses;
import pl.project.api.dto.RestaurantDTO;
import pl.project.api.dto.ServedAddressDTO;
import pl.project.api.dto.mapper.RestaurantMapper;
import pl.project.api.dto.mapper.ServedAddressMapper;
import pl.project.business.services.RestaurantOwnerService;
import pl.project.domain.model.RestaurantOwner;
import pl.project.domain.model.ServedAddress;
import pl.project.infrastructure.security.ProjectUserDetailsService;

import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
@RequestMapping(HomeAddresses.RESTAURANT_OWNER_HOME)
public class RestaurantOwnerController {

    // Restaurant management
    static final String RESTAURANT_MANAGEMENT = "/restaurant_management";
    static final String ADD_RESTAURANT = "/restaurant_management/add_restaurant";
    static final String DEACTIVATE_RESTAURANT = "/restaurant_management/deactivate_restaurant";
    static final String RESTAURANT_UPDATE = "/restaurant_management/{restaurantCode}";
    static final String RESTAURANT_ADD_SERVED_ADDRESS = "/restaurant_management/add_servedAddress";
//    static final String RESTAURANT_DELETE_SERVED_ADDRESS = "/restaurant_management/delete_servedAddress/{servedAddressId}";
    static final String RESTAURANT_DELETE_SERVED_ADDRESS = "/restaurant_management/delete_servedAddress";

    // Restaurant menu management
    static final String RESTAURANT_MENU = "/restaurant_management/restaurant_menu";
    static final String MENU_MANAGEMENT = "/menu_management";
    static final String MENU_MANAGEMENT_SHOW_DISHES = "/menu_management/show_dishes";

    // Restaurant owner profile management
    static final String USER_MANAGEMENT = "/user_management";

    // Orders management
    static final String ORDERS_SUMMARY = "/orders_summary";
    static final String ORDER_DETAILS = "/order_details/{order.orderNumber}";
    static final String NOTIFY_DELIVERY_MAN = "/notify_delivery_man/{order.orderNumber}";

    // Redirect
    static final String REDIRECT_RESTAURANT_MANAGEMENT = "redirect:%s".formatted(HomeAddresses.RESTAURANT_OWNER_HOME + RESTAURANT_MANAGEMENT);
    static final String REDIRECT_MENU_MANAGEMENT = "redirect:%s".formatted(HomeAddresses.RESTAURANT_OWNER_HOME + MENU_MANAGEMENT);


    private final ProjectUserDetailsService projectUserDetailsService;
    private final RestaurantOwnerService restaurantOwnerService;
    private final RestaurantMapper restaurantMapper;
    private final ServedAddressMapper servedAddressMapper;

    @GetMapping
    public String homePage() {
        return "restaurant_owner/restaurant_owner_main_page";
    }

    @GetMapping(USER_MANAGEMENT)
    public String userManagement() {
        return "restaurant_owner/user_management";
    }

    @GetMapping(ORDERS_SUMMARY)
    public String ordersSummary() {
        return "restaurant_owner/orders_summary";
    }

    // Menu management
    @GetMapping(MENU_MANAGEMENT)
    public String dishManagement(
            RestaurantDTO restaurantDTO,
            Model model
    ) {
        RestaurantOwner restaurantOwner = restaurantOwnerService.getRestaurantOwner(getActiveUserEmail());
        List<RestaurantDTO> restaurants = restaurantOwnerService.getRestaurantsByRestaurantOwner(restaurantOwner).stream()
                .map(restaurantMapper::mapToDTO)
                .toList();

        if (Objects.isNull(restaurantDTO.getRestaurantCode()) && restaurants.isEmpty()){
            model.addAttribute("restaurantCode", "");
        }
        if (Objects.isNull(restaurantDTO.getRestaurantCode()) && !restaurants.isEmpty()){
            model.addAttribute("restaurantCode", restaurants.getFirst().getRestaurantCode());
        }
        if(Objects.nonNull(restaurantDTO.getRestaurantCode())){
            model.addAttribute("restaurantCode", restaurantDTO.getRestaurantCode());
        }



        model.addAttribute("restaurantDTOs", restaurants);
        model.addAttribute("restaurantDTO", RestaurantDTO.builder().build());
        return "restaurant_owner/menu_management";
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

    // Restaurant management
    @GetMapping(RESTAURANT_MANAGEMENT)
    public String restaurantManagement(Model model) {
        RestaurantOwner restaurantOwner = restaurantOwnerService.getRestaurantOwner(getActiveUserEmail());
        List<RestaurantDTO> restaurants = restaurantOwnerService.getRestaurantsByRestaurantOwner(restaurantOwner).stream()
                .map(restaurantMapper::mapToDTO)
                .toList();
        model.addAttribute("restaurantDTOs", restaurants);
        return "restaurant_owner/restaurant_management";
    }

    @GetMapping(RESTAURANT_UPDATE)
    public String updateRestaurantMainPanel(
            Model model,
            @PathVariable String restaurantCode
    ) {
        RestaurantDTO restaurantDTO = restaurantMapper.mapToDTO(
                restaurantOwnerService.getRestaurantByRestaurantCode(restaurantCode));
        List<ServedAddressDTO> servedAddresses = restaurantOwnerService.getServedAddresses(restaurantCode).stream()
                .map(servedAddressMapper::mapToDTO)
                .toList();

        model.addAttribute("restaurantDTO", restaurantDTO);
        model.addAttribute("servedAddressesDTOs", servedAddresses);
//        model.addAttribute("servedAddressId", "");
        return "restaurant_owner/restaurant_update";
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
    // Restaurant management END
}
