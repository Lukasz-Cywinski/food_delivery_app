package pl.project.business.dao;

import pl.project.domain.model.Order;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface OrderDAO {

    Optional<Order> createOrder(Order order);

    Optional<Order> findOrderByOrderCode(String orderCode);

    Integer reportCompletedDateTime(OffsetDateTime deliveryDateTime, String orderCode);

    void deleteOrder(Order order);
}
