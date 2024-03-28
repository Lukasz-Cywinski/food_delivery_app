package pl.project.business.services.restaurant_owner;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.project.business.dao.DishCompositionDAO;
import pl.project.business.dao.OrderDAO;
import pl.project.domain.exception.ExceptionMessages;
import pl.project.domain.exception.restaurant_owner.OwnerResourceReadException;
import pl.project.domain.model.Order;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderManagementService {

    private final DishCompositionDAO dishCompositionDAO;

    @Transactional
    public List<Order> getOrdersForRestaurant(String restaurantCode){
        try {
            return dishCompositionDAO.getActiveOrdersForRestaurant(restaurantCode);
        }
        catch (Exception e){
            throw new OwnerResourceReadException(ExceptionMessages.RESOURCE_READ_EXCEPTION
                    .formatted(Order.class.getSimpleName(), restaurantCode), e);
        }
    }
}
