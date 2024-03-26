package pl.project.api.controller.thymeleaf.restaurant_owner;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static pl.project.api.controller.addresses.HomeAddresses.*;

@Controller
@AllArgsConstructor
@RequestMapping(RESTAURANT_OWNER_HOME)
public class RestaurantOwnerHomeController {

    @GetMapping
    public String homePage(Model model) {
        populateModelWithData(model);
        return "restaurant_owner/restaurant_owner_home";
    }

    private void populateModelWithData(Model model) {

    }

}
