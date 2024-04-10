package pl.project.api.controller.view.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static pl.project.api.controller.addresses.CustomerAddresses.ACTIVE_ORDERS_MANAGEMENT;

@Controller
@AllArgsConstructor
@RequestMapping(ACTIVE_ORDERS_MANAGEMENT)
public class ActiveOrdersManagementController {

    @GetMapping
    public static ModelAndView activeOrdersManagement(){
        return new ModelAndView("customer/active_orders_management");
    }
}
