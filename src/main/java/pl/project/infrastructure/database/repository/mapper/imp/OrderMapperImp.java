package pl.project.infrastructure.database.repository.mapper.imp;

import org.springframework.stereotype.Component;
import pl.project.domain.model.Order;
import pl.project.infrastructure.database.entity.OrderEntity;
import pl.project.infrastructure.database.repository.mapper.OrderMapper;

@Component
public class OrderMapperImp implements OrderMapper {

    public Order mapFromEntity(OrderEntity entity){
        return null;
    }

    public OrderEntity mapToEntity(Order domainObj){
        return null;
    }
}
