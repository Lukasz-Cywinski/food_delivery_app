package pl.project.api.controller.view.restaurant_owner;


import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.project.api.dto.RestaurantDTO;
import pl.project.api.dto.mapper.RestaurantMapper;
import pl.project.business.services.restaurant_owner.OrderManagementService;
import pl.project.business.services.restaurant_owner.RestaurantManagementService;
import pl.project.domain.model.Order;
import pl.project.infrastructure.security.ProjectUserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static pl.project.api.controller.addresses.RestaurantOwnerAddresses.ORDER_MANAGEMENT;

@Controller
@AllArgsConstructor
@RequestMapping(ORDER_MANAGEMENT)
public class OrderManagementController {

    private final OrderManagementService orderManagementService;
    private final ProjectUserDetailsService projectUserDetailsService;
    private final RestaurantManagementService restaurantManagementService;

    private final RestaurantMapper restaurantMapper;

    @GetMapping
    public ModelAndView orderManagement(
            String restaurantCode
    ) {
        return new ModelAndView("restaurant_owner/order_management",
                populateOrderManagementWithData(restaurantCode));
    }

    private Map<String, ?> populateOrderManagementWithData(String restaurantCode) {
        List<Order> orderDTOs = orderManagementService.getOrdersForRestaurant(restaurantCode);
        List<RestaurantDTO> restaurantDTOs = restaurantManagementService
                .getRestaurantsByRestaurantOwner(getActiveUserEmail()).stream()
                .map(restaurantMapper::mapToDTO)
                .toList();
        if(Objects.isNull(restaurantCode)) restaurantCode = "";
        return Map.of(
                "orderDTOs", orderDTOs,
                "restaurantDTOs", restaurantDTOs,
                "restaurantCode", restaurantCode
        );
    }

    private String getActiveUserEmail() {
        return projectUserDetailsService.getUserEmail(
                SecurityContextHolder.getContext().getAuthentication().getName());
    }

//    @PostMapping
//    public String test(
//            String restaurantCode
//    ) {
//        System.out.println();
//        return "restaurant_owner/restaurant_owner_home";
//    }
}
