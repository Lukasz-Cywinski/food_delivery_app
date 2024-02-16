package pl.project.infrastructure.database.repository.mapper.imp;


import org.springframework.stereotype.Component;
import pl.project.domain.model.Customer;
import pl.project.domain.model.DeliveryAddress;
import pl.project.infrastructure.database.entity.CustomerEntity;
import pl.project.infrastructure.database.entity.DeliveryAddressEntity;
import pl.project.infrastructure.database.repository.mapper.DeliveryAddressMapper;

@Component
public class DeliveryAddressMapperImp implements DeliveryAddressMapper {

    public DeliveryAddress mapFromEntity(DeliveryAddressEntity entity){
        return null;
    }

    public DeliveryAddressEntity mapToEntity(DeliveryAddress domainObj){
        return null;
    }
}
