package pl.project.infrastructure.database.repository.mapper.imp;


import jakarta.persistence.*;
import org.springframework.stereotype.Component;
import pl.project.domain.model.Customer;
import pl.project.infrastructure.database.entity.CustomerEntity;
import pl.project.infrastructure.database.entity.DeliveryAddressEntity;
import pl.project.infrastructure.database.entity.DishOpinionEntity;
import pl.project.infrastructure.database.entity.OrderEntity;
import pl.project.infrastructure.database.repository.mapper.CustomerMapper;

import java.util.Set;

@Component
public class CustomerMapperImp implements CustomerMapper {

    public Customer mapFromEntity(CustomerEntity entity){
        return null;
    }

    public CustomerEntity mapToEntity(Customer domainObj){
        return null;
    }

}
