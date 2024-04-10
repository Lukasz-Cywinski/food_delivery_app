package pl.project.api.controller.view.rest_api;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static pl.project.api.controller.addresses.HomeAddresses.REST_API_HOME;

@Controller
@AllArgsConstructor
@RequestMapping(REST_API_HOME)
public class RestApiHomeController {

    @GetMapping
    public String homePage(){
        return "rest_api/rest_api_home";
    }
}
