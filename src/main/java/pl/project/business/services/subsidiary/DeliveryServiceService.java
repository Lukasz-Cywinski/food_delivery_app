package pl.project.business.services.subsidiary;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.project.business.dao.DeliveryManDAO;
import pl.project.business.dao.DeliveryServiceDAO;
import pl.project.domain.exception.EntityCreationException;
import pl.project.domain.exception.NoAvailableDeliveryMan;
import pl.project.domain.model.DeliveryMan;
import pl.project.domain.model.DeliveryService;
import pl.project.domain.model.Order;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DeliveryServiceService {

    private final String NO_AVAILABLE_DELIVERY_MAN_EXCEPTION = "No available delivery man please try later";
    private final String DELIVERY_SERVICE_CREATION_EXCEPTION = "Fail to crete delivery service with code: %s";

    private final DeliveryServiceDAO deliveryServiceDAO;
    private final DeliveryManDAO deliveryManDAO;
    private final OrderService orderService;

    // without transactional -> methods all called from another Transactional methods
    // for now simplified - e.g. delivery man is chosen automatically, delivery man cannot log in and other...
    public DeliveryService createDeliveryService(){

        String identifier = UUID.randomUUID().toString();
        DeliveryMan deliveryMan = deliveryManDAO.getAvailableDeliveryMen().stream()
                .findAny()
                .orElseThrow(() -> new NoAvailableDeliveryMan(NO_AVAILABLE_DELIVERY_MAN_EXCEPTION));
        DeliveryService deliveryService = DeliveryService.builder()
                .deliveryServiceCode(identifier)
                .receivedDateTime(OffsetDateTime.now())
                .deliveryMan(deliveryMan)
                .build();

        return deliveryServiceDAO.createDeliveryService(deliveryService)
                .orElseThrow(() -> new EntityCreationException(DELIVERY_SERVICE_CREATION_EXCEPTION.formatted(identifier)));
    }


    public boolean deliverOrder(Order order){
        return orderService.finishOrder(order)
                && (deliveryServiceDAO.reportCompletedDateTime(OffsetDateTime.now(), order.getOrderCode())) == 1;
    }

}
