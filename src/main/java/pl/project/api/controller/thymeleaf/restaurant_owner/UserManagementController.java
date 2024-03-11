package pl.project.api.controller.thymeleaf.restaurant_owner;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.project.api.controller.addresses.RestaurantOwnerAddresses;

@Controller
@AllArgsConstructor
@RequestMapping(RestaurantOwnerAddresses.USER_MANAGEMENT)
public class UserManagementController {

    @GetMapping
    public String userManagement() {
        return "restaurant_owner/user_management";
    }
}
