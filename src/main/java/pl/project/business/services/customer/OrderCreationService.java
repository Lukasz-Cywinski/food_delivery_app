package pl.project.business.services.customer;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.project.business.dao.CustomerDAO;
import pl.project.business.dao.RestaurantDAO;
import pl.project.business.services.pageable.PageableService;
import pl.project.domain.exception.customer.CustomerResourceReadException;
import pl.project.domain.model.Customer;
import pl.project.domain.model.DeliveryAddress;
import pl.project.domain.model.PageableProperties;
import pl.project.domain.model.Restaurant;

import java.util.List;

import static pl.project.domain.exception.ExceptionMessages.RESOURCE_READ_EXCEPTION;

@Service
@AllArgsConstructor
public class OrderCreationService {

    PageableService pageableService;
    RestaurantDAO restaurantDAO;
    CustomerDAO customerDAO;

    @Transactional
    public List<Restaurant> getRestaurantsForYourAddress(PageableProperties properties, String customerEmail){
        try {
            DeliveryAddress deliveryAddress = customerDAO.findCustomerByEmail(customerEmail)
                    .orElseThrow(RuntimeException::new).getDeliveryAddress();
            Pageable pageable = pageableService.buildPageable(properties);
            return restaurantDAO.findActiveRestaurantsWithAddress(deliveryAddress, pageable);
        }
        catch (Exception e) {
            throw new CustomerResourceReadException(RESOURCE_READ_EXCEPTION
                    .formatted(Restaurant.class.getName(), properties), e);
        }
    }

    @Transactional
    public Integer countAllRestaurantsForYourAddress(String customerEmail) {
        try {
            DeliveryAddress deliveryAddress = customerDAO.findCustomerByEmail(customerEmail)
                    .orElseThrow(RuntimeException::new).getDeliveryAddress();
            return restaurantDAO.countAllActiveRestaurantsWithAddress(deliveryAddress);
        }
        catch (Exception e){
            throw new CustomerResourceReadException(RESOURCE_READ_EXCEPTION
                    .formatted(Customer.class.getName(), customerEmail), e);
        }
    }
}
