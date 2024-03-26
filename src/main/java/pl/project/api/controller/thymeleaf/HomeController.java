package pl.project.api.controller.thymeleaf;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.project.api.controller.addresses.HomeAddresses;

@Controller
@AllArgsConstructor
@RequestMapping(HomeAddresses.HOME)
public class HomeController {
    @GetMapping
    public String homePage(){
        return "home";
    }

}
