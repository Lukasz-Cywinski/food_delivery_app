package pl.project.business.dao;

import pl.project.domain.model.DishPhoto;
import pl.project.domain.model.Order;
import pl.project.infrastructure.database.entity.OrderEntity;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface OrderDAO {

    Optional<Order> createOrder(Order order);

    Optional<Order> findOrderByOrderCode(String deliveryServiceCode);

    Integer reportCompletedDateTime(OffsetDateTime deliveryDateTime, String orderCode);
}
