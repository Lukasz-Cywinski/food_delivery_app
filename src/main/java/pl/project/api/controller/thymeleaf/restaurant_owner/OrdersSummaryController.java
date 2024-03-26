package pl.project.api.controller.thymeleaf.restaurant_owner;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.project.api.controller.addresses.RestaurantOwnerAddresses;

import static pl.project.api.controller.addresses.RestaurantOwnerAddresses.*;

@Controller
@AllArgsConstructor
@RequestMapping(ORDERS_SUMMARY)
public class OrdersSummaryController {

    @GetMapping
    public String ordersSummary() {
        return "restaurant_owner/orders_summary";
    }
}
