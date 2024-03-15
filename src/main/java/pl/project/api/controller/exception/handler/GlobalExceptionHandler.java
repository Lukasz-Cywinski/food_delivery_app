package pl.project.api.controller.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import pl.project.domain.exception.restaurant_owner.OwnerResourceReadException;

@Slf4j
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        String message = String.format("Other exception occurred: [%s]", ex.getMessage());
        log.error(message, ex);
        ModelAndView modelView = new ModelAndView("error/error");
        modelView.addObject("errorMessage", message);
        return modelView;
    }

//    @ExceptionHandler(OwnerResourceReadException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ModelAndView handleResourceReadException(Exception e) {
//        String message = String.format(e.getMessage());
//        log.error(message, e);
//        ModelAndView modelView = new ModelAndView("error/restaurant_owner_error");
//        modelView.addObject("errorMessage", message);
//        modelView.addObject("statusNumber", HttpStatus.NOT_FOUND.value());
//        return modelView;
//    }
}
