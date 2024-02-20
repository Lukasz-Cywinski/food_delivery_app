package pl.project.infrastructure.database.repository.mapper.imp;


import org.springframework.stereotype.Component;
import pl.project.domain.model.Customer;
import pl.project.infrastructure.database.entity.CustomerEntity;
import pl.project.infrastructure.database.repository.mapper.CustomerEntityMapper;

@Component
public class CustomerEntityMapperImp implements CustomerEntityMapper {

    public Customer mapFromEntity(CustomerEntity entity){
        return null;
    }

    public CustomerEntity mapToEntity(Customer domainObj){
        return null;
    }

}
