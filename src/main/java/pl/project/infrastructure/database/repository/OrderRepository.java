package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.OrderDAO;
import pl.project.domain.model.Order;
import pl.project.infrastructure.database.entity.OrderEntity;
import pl.project.infrastructure.database.repository.jpa.OrderJpaRepository;
import pl.project.infrastructure.database.repository.mapper.OrderEntityMapper;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OrderRepository implements OrderDAO {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderEntityMapper orderEntityMapper;

    @Override
    public Optional<Order> createOrder(Order order) {
        return Optional.ofNullable(orderEntityMapper.mapFromEntity(
                orderJpaRepository.save(
                        orderEntityMapper.mapToEntity(order)
                )
        ));
    }

    @Override
    public Optional<Order> findOrderByOrderCode(String deliveryServiceCode) {
        return orderJpaRepository.findByOrderCode(deliveryServiceCode)
                .map(orderEntityMapper::mapFromEntity);
    }

    @Override
    public Integer reportCompletedDateTime(OffsetDateTime deliveryDateTime, String orderCode) {
        return orderJpaRepository.reportCompletedDateTime(deliveryDateTime, orderCode);
    }
}
