package pl.project.api.controller.thymeleaf;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@AllArgsConstructor
public class RestaurantOwnerController {

    static final String HOME = "/restaurant_owner";

    @GetMapping(value = HOME)
    public String homePage(){
        return "restaurant_owner/restaurant_owner_main_page";
    }
}
