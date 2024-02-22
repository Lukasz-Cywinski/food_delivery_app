package pl.project.infrastructure.database.repository.mapper.imp;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.project.domain.model.DeliveryAddress;
import pl.project.infrastructure.database.entity.DeliveryAddressEntity;
import pl.project.infrastructure.database.repository.mapper.DeliveryAddressEntityMapper;

@Component
public class DeliveryAddressEntityMapperImp implements DeliveryAddressEntityMapper {

    public DeliveryAddress mapFromEntity(DeliveryAddressEntity entity){
        return DeliveryAddress.builder()
                .city(entity.getCity())
                .postalCode(entity.getPostalCode())
                .street(entity.getStreet())
                .build();
    }

    public DeliveryAddressEntity mapToEntity(DeliveryAddress domainObj){
        return DeliveryAddressEntity.builder()
                .city(domainObj.getCity())
                .postalCode(domainObj.getPostalCode())
                .street(domainObj.getStreet())
                .build();
    }
}
