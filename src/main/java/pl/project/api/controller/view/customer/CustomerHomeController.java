package pl.project.api.controller.view.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static pl.project.api.controller.addresses.HomeAddresses.CUSTOMER_HOME;

@Controller
@AllArgsConstructor
@RequestMapping(CUSTOMER_HOME)
public class CustomerHomeController {

    @GetMapping
    public String homePage(){
        return "customer/home";
    }

}
