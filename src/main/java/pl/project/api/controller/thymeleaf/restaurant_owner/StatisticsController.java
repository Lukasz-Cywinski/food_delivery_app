package pl.project.api.controller.thymeleaf.restaurant_owner;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static pl.project.api.controller.addresses.RestaurantOwnerAddresses.STATISTICS;

@Controller
@AllArgsConstructor
@RequestMapping(STATISTICS)
public class StatisticsController {

    @GetMapping
    public String statistics() {
        return "restaurant_owner/statistics";
    }
}
