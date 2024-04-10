package pl.project.api.controller.view.delivery_man;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static pl.project.api.controller.addresses.HomeAddresses.DELIVERY_MAN_HOME;

@Controller
@AllArgsConstructor
@RequestMapping(DELIVERY_MAN_HOME)
public class DeliveryManHomeController {

    @GetMapping
    public String homePage(){
        return "delivery_man/delivery_man_home";
    }
}
