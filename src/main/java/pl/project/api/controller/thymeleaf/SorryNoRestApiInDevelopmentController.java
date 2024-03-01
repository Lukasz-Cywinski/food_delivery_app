package pl.project.api.controller.thymeleaf;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/rest_api")
public class SorryNoRestApiInDevelopmentController {

    @GetMapping
    public String inDevelopment(){
        return "registration/rest_api_in_development";
    }
}
