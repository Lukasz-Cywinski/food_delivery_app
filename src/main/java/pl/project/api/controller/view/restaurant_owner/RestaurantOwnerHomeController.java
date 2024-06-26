package pl.project.api.controller.view.restaurant_owner;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static pl.project.api.controller.addresses.HomeAddresses.RESTAURANT_OWNER_HOME;

@Controller
@AllArgsConstructor
@RequestMapping(RESTAURANT_OWNER_HOME)
public class RestaurantOwnerHomeController {

    @GetMapping
    public String homePage(){
        return "restaurant_owner/home";
    }
}
