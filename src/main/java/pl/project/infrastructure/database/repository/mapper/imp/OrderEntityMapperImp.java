package pl.project.infrastructure.database.repository.mapper.imp;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.project.domain.model.Order;
import pl.project.infrastructure.database.entity.OrderEntity;
import pl.project.infrastructure.database.repository.mapper.CustomerEntityMapper;
import pl.project.infrastructure.database.repository.mapper.DeliveryServiceEntityMapper;
import pl.project.infrastructure.database.repository.mapper.OrderEntityMapper;

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
                .customer(customerEntityMapper.mapFromEntity(entity.getCustomer()))
                .deliveryService(deliveryServiceEntityMapper.mapFromEntity(entity.getDeliveryService()))
                .build();
    }

    public OrderEntity mapToEntity(Order domainObj){
        return OrderEntity.builder()
                .id(domainObj.getId())
                .orderCode(domainObj.getOrderCode())
                .receivedDateTime(domainObj.getReceivedDateTime())
                .completedDateTime(domainObj.getCompletedDateTime())
                .customer(customerEntityMapper.mapToEntity(domainObj.getCustomer()))
                .deliveryService(deliveryServiceEntityMapper.mapToEntity(domainObj.getDeliveryService()))
                .build();
    }
}
