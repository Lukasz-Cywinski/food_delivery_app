package pl.project.infrastructure.database.repository.mapper.imp;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.project.domain.model.Order;
import pl.project.infrastructure.database.entity.OrderEntity;
import pl.project.infrastructure.database.repository.mapper.CustomerEntityMapper;
import pl.project.infrastructure.database.repository.mapper.DeliveryServiceEntityMapper;
import pl.project.infrastructure.database.repository.mapper.OrderEntityMapper;

import java.util.Objects;

@Component
@AllArgsConstructor
public class OrderEntityMapperImp implements OrderEntityMapper {

    private final CustomerEntityMapper customerEntityMapper;
    private final DeliveryServiceEntityMapper deliveryServiceEntityMapper;

    public Order mapFromEntity(OrderEntity entity){
        return Order.builder()
                .id(entity.getId())
                .orderCode(entity.getOrderCode())
                .receivedDateTime(entity.getReceivedDateTime())
                .completedDateTime(entity.getCompletedDateTime())
                .customer(Objects.isNull(entity.getCustomer()) ?
                        null : customerEntityMapper.mapFromEntity(entity.getCustomer()))
                .deliveryService(Objects.isNull(entity.getDeliveryService()) ?
                        null : deliveryServiceEntityMapper.mapFromEntity(entity.getDeliveryService()))
                .build();
    }

    public OrderEntity mapToEntity(Order domainObj){
        return OrderEntity.builder()
                .id(domainObj.getId())
                .orderCode(domainObj.getOrderCode())
                .receivedDateTime(domainObj.getReceivedDateTime())
                .completedDateTime(domainObj.getCompletedDateTime())
                .customer(Objects.isNull(domainObj.getCustomer()) ?
                        null : customerEntityMapper.mapToEntity(domainObj.getCustomer()))
                .deliveryService(Objects.isNull(domainObj.getDeliveryService()) ?
                        null : deliveryServiceEntityMapper.mapToEntity(domainObj.getDeliveryService()))
                .build();
    }
}
