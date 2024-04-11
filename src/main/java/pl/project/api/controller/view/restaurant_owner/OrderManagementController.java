package pl.project.api.controller.view.restaurant_owner;


import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.project.api.dto.OrderDTO;
import pl.project.api.dto.RestaurantDTO;
import pl.project.api.dto.mapper.OrderMapper;
import pl.project.api.dto.mapper.RestaurantMapper;
import pl.project.business.services.restaurant_owner.OrderManagementService;
import pl.project.business.services.restaurant_owner.RestaurantManagementService;
import pl.project.domain.model.Order;
import pl.project.infrastructure.security.ProjectUserDetailsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final RestaurantMapper restaurantMapper;
    private final OrderMapper orderMapper;

    @GetMapping
    public ModelAndView orderManagement(
            String restaurantCode
    ) {
        return new ModelAndView("restaurant_owner/order_management",
                populateOrderManagementWithData(restaurantCode));
    }

    private Map<String, ?> populateOrderManagementWithData(String restaurantCode) {
        List<OrderDTO> orderDTOs = orderManagementService.getOrdersForRestaurant(restaurantCode).stream()
                .map(orderMapper::mapToDTO)
                .toList();
        List<RestaurantDTO> restaurantDTOs = restaurantManagementService
                .getRestaurantsByRestaurantOwner(getActiveUserEmail()).stream()
                .map(restaurantMapper::mapToDTO)
                .toList();
        if(Objects.isNull(restaurantCode)) restaurantCode = "";


        List<Long> timeLeft = orderDTOs.stream()
                .map(order -> LocalDateTime.parse(order.getReceivedDateTime(), FORMATTER).until(LocalDateTime.now(), ChronoUnit.SECONDS))
                .toList();

        return Map.of(
                "orderDTOs", orderDTOs,
                "restaurantDTOs", restaurantDTOs,
                "restaurantCode", restaurantCode,
                "timeLeft", timeLeft
        );
    }

    private String getActiveUserEmail() {
        return projectUserDetailsService.getUserEmail(
                SecurityContextHolder.getContext().getAuthentication().getName());
    }


}
