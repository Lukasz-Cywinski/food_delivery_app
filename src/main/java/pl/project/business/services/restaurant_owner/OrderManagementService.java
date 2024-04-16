package pl.project.business.services.restaurant_owner;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.project.business.dao.DishCompositionDAO;
import pl.project.business.dao.OrderDAO;
import pl.project.domain.exception.ExceptionMessages;
import pl.project.domain.exception.restaurant_owner.OwnerResourceReadException;
import pl.project.domain.model.DishComposition;
import pl.project.domain.model.Order;
import pl.project.infrastructure.database.repository.mapper.DishCompositionEntityMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderManagementService {

    private final DishCompositionDAO dishCompositionDAO;
    private final OrderDAO orderDAO;

    private final DishCompositionEntityMapper dishCompositionEntityMapper;

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

    @Transactional
    public Order getOrder(String orderCode) {
        try {
            return orderDAO.findOrderByOrderCode(orderCode).orElseThrow(RuntimeException::new);
        }
        catch (Exception e){
            throw new OwnerResourceReadException(ExceptionMessages.RESOURCE_READ_EXCEPTION
                    .formatted(Order.class.getSimpleName(), orderCode), e);
        }
    }

    @Transactional
    public List <DishComposition> getDishCompositions(String orderCode){
        try {
            return dishCompositionDAO.findDishCompositionByOrder(orderCode);
        }
        catch (Exception e){
            throw new OwnerResourceReadException(ExceptionMessages.RESOURCE_READ_EXCEPTION
                    .formatted(DishComposition.class.getSimpleName(), orderCode), e);
        }
    }
}
