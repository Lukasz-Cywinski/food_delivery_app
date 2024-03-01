package pl.project.api.controller.thymeleaf;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@AllArgsConstructor
public class CustomerController {

    static final String HOME = "/customer";

    @RequestMapping(value = HOME, method = RequestMethod.GET)
    public String homePage(){
        return "customer/customer_main_page";
    }

}
