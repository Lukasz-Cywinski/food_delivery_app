package pl.project.api.controller.thymeleaf.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static pl.project.api.controller.addresses.HomeAddresses.CUSTOMER_HOME;
import static pl.project.api.controller.addresses.HomeAddresses.RESTAURANT_OWNER_HOME;

@Controller
@AllArgsConstructor
@RequestMapping(CUSTOMER_HOME)
public class CustomerHomeController {

    @GetMapping
    public String homePage(){
        return "customer/customer_home";
    }

}
