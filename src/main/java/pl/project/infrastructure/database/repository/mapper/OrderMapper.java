package pl.project.infrastructure.database.repository.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.project.domain.model.Order;
import pl.project.infrastructure.database.entity.OrderEntity;


public interface OrderMapper {

    Order mapFromEntity(OrderEntity entity);

    OrderEntity mapToEntity(Order domainObj);
}
