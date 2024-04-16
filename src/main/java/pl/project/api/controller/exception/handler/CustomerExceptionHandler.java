package pl.project.api.controller.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import pl.project.api.controller.exception.CustomerIncorrectInputException;
import pl.project.domain.exception.customer.CustomerResourceCreateException;
import pl.project.domain.exception.customer.CustomerResourceDeleteException;
import pl.project.domain.exception.customer.CustomerResourceReadException;
import pl.project.domain.exception.customer.CustomerResourceUpdateException;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomerExceptionHandler {

    @ExceptionHandler(CustomerIncorrectInputException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ModelAndView handleIncorrectInputException(Exception e) {
        String message = String.format(e.getMessage());
        log.error(message, e);
        ModelAndView modelView = new ModelAndView("error/restaurant_owner_error");
        modelView.addObject("errorMessage", message);
        modelView.addObject("statusNumber", HttpStatus.UNPROCESSABLE_ENTITY.value());
        return modelView;
    }

    @ExceptionHandler(CustomerResourceCreateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleResourceCreateException(Exception e) {
        String message = String.format(e.getMessage());
        log.error(message, e);
        ModelAndView modelView = new ModelAndView("error/restaurant_owner_error");
        modelView.addObject("errorMessage", message);
        modelView.addObject("statusNumber", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return modelView;
    }

    @ExceptionHandler(CustomerResourceReadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleResourceReadException(Exception e) {
        String message = String.format(e.getMessage());
        log.error(message, e);
        ModelAndView modelView = new ModelAndView("error/restaurant_owner_error");
        modelView.addObject("errorMessage", message);
        modelView.addObject("statusNumber", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return modelView;
    }

    @ExceptionHandler(CustomerResourceUpdateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleResourceUpdateException(Exception e) {
        String message = String.format(e.getMessage());
        log.error(message, e);
        ModelAndView modelView = new ModelAndView("error/restaurant_owner_error");
        modelView.addObject("errorMessage", message);
        modelView.addObject("statusNumber", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return modelView;
    }

    @ExceptionHandler(CustomerResourceDeleteException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleResourceDeleteException(Exception e) {
        String message = String.format(e.getMessage());
        log.error(message, e);
        ModelAndView modelView = new ModelAndView("error/restaurant_owner_error");
        modelView.addObject("errorMessage", message);
        modelView.addObject("statusNumber", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return modelView;
    }
}
