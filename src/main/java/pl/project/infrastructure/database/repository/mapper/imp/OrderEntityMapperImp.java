package pl.project.infrastructure.database.repository.mapper.imp;

import org.springframework.stereotype.Component;
import pl.project.domain.model.Order;
import pl.project.infrastructure.database.entity.OrderEntity;
import pl.project.infrastructure.database.repository.mapper.OrderEntityMapper;

@Component
public class OrderEntityMapperImp implements OrderEntityMapper {

    public Order mapFromEntity(OrderEntity entity){
        return null;
    }

    public OrderEntity mapToEntity(Order domainObj){
        return null;
    }
}
