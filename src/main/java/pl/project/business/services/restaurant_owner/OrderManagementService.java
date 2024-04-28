package pl.project.business.services.restaurant_owner;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.project.business.dao.DeliveryManDAO;
import pl.project.business.dao.DeliveryServiceDAO;
import pl.project.business.dao.DishCompositionDAO;
import pl.project.business.dao.OrderDAO;
import pl.project.domain.exception.ExceptionMessages;
import pl.project.domain.exception.restaurant_owner.OwnerResourceReadException;
import pl.project.domain.exception.restaurant_owner.OwnerResourceUpdateException;
import pl.project.domain.model.DeliveryMan;
import pl.project.domain.model.DeliveryService;
import pl.project.domain.model.DishComposition;
import pl.project.domain.model.Order;
import pl.project.infrastructure.database.entity.DishCompositionEntity;
import pl.project.infrastructure.database.repository.mapper.DishCompositionEntityMapper;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

import static pl.project.domain.exception.ExceptionMessages.*;

@Service
@AllArgsConstructor
public class OrderManagementService {

    private final DeliveryServiceDAO deliveryServiceDAO;
    private final DishCompositionDAO dishCompositionDAO;
    private final DeliveryManDAO deliveryManDAO;
    private final OrderDAO orderDAO;

    @Transactional
    public List<Order> getOrdersForRestaurant(String restaurantCode){
        try {
            return dishCompositionDAO.getActiveOrdersForRestaurant(restaurantCode);
        }
        catch (Exception e){
            throw new OwnerResourceReadException(RESOURCE_READ_EXCEPTION
                    .formatted(Order.class.getSimpleName(), restaurantCode), e);
        }
    }

    @Transactional
    public Order getOrder(String orderCode) {
        try {
            return orderDAO.findOrderByOrderCode(orderCode).orElseThrow(RuntimeException::new);
        }
        catch (Exception e){
            throw new OwnerResourceReadException(RESOURCE_READ_EXCEPTION
                    .formatted(Order.class.getSimpleName(), orderCode), e);
        }
    }

    @Transactional
    public List <DishComposition> getDishCompositions(String orderCode){
        try {
            return dishCompositionDAO.findDishCompositionByOrder(orderCode);
        }
        catch (Exception e){
            throw new OwnerResourceReadException(RESOURCE_READ_EXCEPTION
                    .formatted(DishComposition.class.getSimpleName(), orderCode), e);
        }
    }

    @Transactional
    public void notifyDeliveryService(String orderCode) {
        // It's simplified - service and delivery man get request and "finish it" immediately
//        In future it is possible to add some new logic
        try {
            Order order = orderDAO.findOrderByOrderCode(orderCode).orElseThrow(RuntimeException::new);
            String deliveryServiceCode = order.getDeliveryService().getDeliveryServiceCode();
            String deliveryManPersonalCode = order.getDeliveryService().getDeliveryMan().getPersonalCode();
            deliveryManDAO.changeDeliveryManAvailabilityStatus(true, deliveryManPersonalCode);
            deliveryServiceDAO.reportCompletedDateTime(OffsetDateTime.now(), deliveryServiceCode);
            orderDAO.reportCompletedDateTime(OffsetDateTime.now(), orderCode);
        }
        catch (Exception e){
            throw new OwnerResourceUpdateException(RESOURCE_MODIFICATION_EXCEPTION
                    .formatted(Order.class.getSimpleName(), orderCode), e);
        }
    }
}
