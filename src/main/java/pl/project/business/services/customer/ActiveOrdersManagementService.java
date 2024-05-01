package pl.project.business.services.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.project.business.dao.DeliveryManDAO;
import pl.project.business.dao.DishCompositionDAO;
import pl.project.business.dao.OrderDAO;
import pl.project.domain.exception.customer.CustomerResourceDeleteException;
import pl.project.domain.exception.customer.CustomerResourceReadException;
import pl.project.domain.model.DeliveryMan;
import pl.project.domain.model.DishComposition;
import pl.project.domain.model.Order;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.project.domain.exception.ExceptionMessages.RESOURCE_DELETE_EXCEPTION;
import static pl.project.domain.exception.ExceptionMessages.RESOURCE_READ_EXCEPTION;

@Service
@AllArgsConstructor
public class ActiveOrdersManagementService {

    private final DishCompositionDAO dishCompositionDAO;
    private final DeliveryManDAO deliveryManDAO;
    private final OrderDAO orderDAO;

    @Transactional
    public Map<Order, List<DishComposition>> getDishCompositionsForActiveOrders(String customerEmail){
        try {
            List<DishComposition> dishComposition = dishCompositionDAO.getActiveOrdersForCustomer(customerEmail);
            return dishComposition.stream().collect(Collectors.groupingBy((DishComposition::getOrder)));
        }
        catch (Exception e){
            throw new CustomerResourceReadException(RESOURCE_READ_EXCEPTION
                    .formatted(DishComposition.class.getSimpleName(), customerEmail), e);
        }
    }

    @Transactional
    public void cancelOrder(String orderCode) {
        try {
            // Delivery MAn is realised there (for now it's simplified)
            DeliveryMan deliveryMan = orderDAO.findOrderByOrderCode(orderCode)
                    .orElseThrow(RuntimeException::new).getDeliveryService().getDeliveryMan();
            deliveryManDAO.changeDeliveryManAvailabilityStatus(true, deliveryMan.getPersonalCode());
            dishCompositionDAO.deleteDishComposition(orderCode);
            orderDAO.deleteOrder(orderCode);
        }
        catch (Exception e){
            throw new CustomerResourceDeleteException(RESOURCE_DELETE_EXCEPTION
                    .formatted(Order.class.getSimpleName(), orderCode), e);
        }
    }
}
