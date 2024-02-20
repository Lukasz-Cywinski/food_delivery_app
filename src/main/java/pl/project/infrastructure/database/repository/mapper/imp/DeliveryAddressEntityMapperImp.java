package pl.project.infrastructure.database.repository.mapper.imp;


import org.springframework.stereotype.Component;
import pl.project.domain.model.DeliveryAddress;
import pl.project.infrastructure.database.entity.DeliveryAddressEntity;
import pl.project.infrastructure.database.repository.mapper.DeliveryAddressEntityMapper;

@Component
public class DeliveryAddressEntityMapperImp implements DeliveryAddressEntityMapper {

    public DeliveryAddress mapFromEntity(DeliveryAddressEntity entity){
        return null;
    }

    public DeliveryAddressEntity mapToEntity(DeliveryAddress domainObj){
        return null;
    }
}
