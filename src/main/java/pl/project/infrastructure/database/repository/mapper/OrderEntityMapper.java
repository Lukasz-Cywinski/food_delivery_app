package pl.project.infrastructure.database.repository.mapper;

import pl.project.domain.model.Order;
import pl.project.infrastructure.database.entity.OrderEntity;


public interface OrderEntityMapper {

    Order mapFromEntity(OrderEntity entity);

    OrderEntity mapToEntity(Order domainObj);
}
