package pl.project.api.controller.view.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static pl.project.api.controller.addresses.CustomerAddresses.OPINION_MANAGEMENT;

@Controller
@AllArgsConstructor
@RequestMapping(OPINION_MANAGEMENT)
public class OpinionManagementController {
    @GetMapping
    public static ModelAndView orderCreation(){
        return new ModelAndView("customer/opinion_management");
    }
}
