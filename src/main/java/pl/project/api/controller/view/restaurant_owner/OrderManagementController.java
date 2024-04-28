package pl.project.api.controller.view.restaurant_owner;


import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.project.api.dto.DishCompositionDTO;
import pl.project.api.dto.OrderDTO;
import pl.project.api.dto.OrderDetailsDTO;
import pl.project.api.dto.RestaurantDTO;
import pl.project.api.dto.mapper.DishCompositionMapper;
import pl.project.api.dto.mapper.OrderDetailsMapper;
import pl.project.api.dto.mapper.OrderMapper;
import pl.project.api.dto.mapper.RestaurantMapper;
import pl.project.business.services.restaurant_owner.OrderManagementService;
import pl.project.business.services.restaurant_owner.RestaurantManagementService;
import pl.project.domain.formatter.Formatters;
import pl.project.domain.model.DishComposition;
import pl.project.infrastructure.security.ProjectUserDetailsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static pl.project.api.controller.addresses.RestaurantOwnerAddresses.ORDER_MANAGEMENT;
import static pl.project.domain.formatter.Formatters.*;

@Controller
@AllArgsConstructor
@RequestMapping(ORDER_MANAGEMENT)
public class OrderManagementController {

    static final String CURRENT_ORDER = "/{orderCode}";
    static final String REDIRECT_ORDER_MANAGEMENT = "redirect:%s".formatted(ORDER_MANAGEMENT);

    private final OrderManagementService orderManagementService;
    private final ProjectUserDetailsService projectUserDetailsService;
    private final RestaurantManagementService restaurantManagementService;

    private final DishCompositionMapper dishCompositionMapper;
    private final OrderDetailsMapper orderDetailsMapper;
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
                .map(order -> LocalDateTime.parse(order.getReceivedDateTime(), DATE_TIME_FORMATTER).until(LocalDateTime.now(), ChronoUnit.SECONDS))
                .toList();

        return Map.of(
                "orderDTOs", orderDTOs,
                "restaurantDTOs", restaurantDTOs,
                "restaurantCode", restaurantCode,
                "timeLeft", timeLeft
        );
    }

    @GetMapping(CURRENT_ORDER)
    public ModelAndView orderDetails(
            @PathVariable String orderCode
    ){
        return new ModelAndView("restaurant_owner/order_details",
                populateOrderDetailsWithData(orderCode));
    }

    private Map<String, ?> populateOrderDetailsWithData(String orderCode) {
        List<DishCompositionDTO> dishCompositionDTOs = orderManagementService.getDishCompositions(orderCode).stream()
                .map(dishCompositionMapper::mapToDTO)
                .toList();
        OrderDetailsDTO orderDetailsDTO = orderDetailsMapper.mapToDTO(orderManagementService.getOrder(orderCode));
        return Map.of(
                "dishCompositionDTOs", dishCompositionDTOs,
                "orderDetailsDTO", orderDetailsDTO
        );
    }

    @PostMapping(CURRENT_ORDER)
    public String notifyDeliveryMan(
            @PathVariable String orderCode
    ){
        // it is simplified - order is "delivered" immediately
        orderManagementService.notifyDeliveryService(orderCode);
        return REDIRECT_ORDER_MANAGEMENT;
    }

    private String getActiveUserEmail() {
        return projectUserDetailsService.getUserEmail(
                SecurityContextHolder.getContext().getAuthentication().getName());
    }


}
