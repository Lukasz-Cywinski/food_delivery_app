package pl.project.api.controller.view.customer;

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
import pl.project.api.dto.mapper.DishCompositionMapper;
import pl.project.api.dto.mapper.OrderDetailsMapper;
import pl.project.api.dto.mapper.OrderMapper;
import pl.project.business.services.customer.ActiveOrdersManagementService;
import pl.project.business.services.customer.OrderCreationService;
import pl.project.business.services.restaurant_owner.OrderManagementService;
import pl.project.infrastructure.security.ProjectUserDetailsService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.project.api.controller.addresses.CustomerAddresses.ACTIVE_ORDERS_MANAGEMENT;
import static pl.project.domain.formatter.Formatters.DATE_TIME_FORMATTER;

@Controller
@AllArgsConstructor
@RequestMapping(ACTIVE_ORDERS_MANAGEMENT)
public class ActiveOrdersManagementController {

    private final ActiveOrdersManagementService activeOrdersManagementService;
    private final ProjectUserDetailsService projectUserDetailsService;

    private final OrderMapper orderMapper;
    private final DishCompositionMapper dishCompositionMapper;

    static final String ACTIVE_ORDER = "/{orderCode}";
    static final String REDIRECT_ACTIVE_ORDERS_MANAGEMENT = "redirect:%s".formatted(ACTIVE_ORDERS_MANAGEMENT);

    @GetMapping
    public ModelAndView activeOrdersManagement(){
        return new ModelAndView("customer/active_orders_management", populateActiveOrdersManagementWithData());
    }

    private Map<String, ?> populateActiveOrdersManagementWithData() {

        Map<OrderDTO, List<DishCompositionDTO>> activeOrderDTOs = activeOrdersManagementService
                .getDishCompositionsForActiveOrders(getActiveUserEmail()).entrySet().stream()
                .collect(Collectors.toMap(
                        entity -> orderMapper.mapToDTO(entity.getKey()),
                        entity -> entity.getValue().stream()
                                .map(dishCompositionMapper::mapToDTO)
                                .toList()
                ));

        List<Long> timeLeft = activeOrderDTOs.keySet().stream()
                .map(order -> LocalDateTime.parse(order.getReceivedDateTime(), DATE_TIME_FORMATTER).until(LocalDateTime.now(), ChronoUnit.SECONDS))
                .toList();

        return Map.of(
                "activeOrderDTOs", activeOrderDTOs,
                "timeLeft", timeLeft
        );
    }

    @PostMapping(ACTIVE_ORDER)
    public String cancelOrder(
            @PathVariable String orderCode
    ){
        activeOrdersManagementService.cancelOrder(orderCode);
        return REDIRECT_ACTIVE_ORDERS_MANAGEMENT;
    }

    private String getActiveUserEmail() {
        return projectUserDetailsService.getUserEmail(
                SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
