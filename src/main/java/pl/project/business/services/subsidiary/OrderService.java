package pl.project.business.services.subsidiary;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

//    private final String ORDER_CREATION_EXCEPTION = "Fail to crete order with order code: %s";
//    private final String ORDER_READ_EXCEPTION = "Fail to found order by order code: %s";
//
//    private final OrderDAO orderDAO;
//    private final DishCompositionService dishCompositionService;
//
//    // without transactional -> methods all called from another Transactional methods
//    public Order createOrder(Customer customer, DeliveryService deliveryService){
//        String orderCode = UUID.randomUUID().toString();
//        Order order = Order.builder()
//                .orderCode(orderCode)
//                .receivedDateTime(OffsetDateTime.now())
//                .customer(customer)
//                .deliveryService(deliveryService)
//                .build();
//        return orderDAO.createOrder(order)
//                .orElseThrow(() -> new OwnerResourceCreationException(ORDER_CREATION_EXCEPTION.formatted(orderCode)));
//    }
//
//    public boolean cancelOrder(String orderCode){
//        Order order = getOrder(orderCode);
//        OffsetDateTime receivedDateTime = order.getReceivedDateTime();
//        long diff = ChronoUnit.MINUTES.between(receivedDateTime, OffsetDateTime.now());
//        if (diff >= 20) return false;
//
//        dishCompositionService.deleteDishCompositions(order);
//        orderDAO.deleteOrder(order);
//        return true;
//    }
//
//    private Order getOrder(String orderCode){
//        return orderDAO.findOrderByOrderCode(orderCode)
//                .orElseThrow(() -> new OwnerResourceReadException(ORDER_READ_EXCEPTION.formatted(orderCode)));
//    }
//
//    public boolean finishOrder(Order order) {
//        return orderDAO.reportCompletedDateTime(OffsetDateTime.now(), order.getOrderCode()) == 1;
//    }
}
