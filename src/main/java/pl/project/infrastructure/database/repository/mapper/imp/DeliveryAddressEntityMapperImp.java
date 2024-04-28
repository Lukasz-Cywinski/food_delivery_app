package pl.project.infrastructure.database.repository.mapper.imp;


import org.springframework.stereotype.Component;
import pl.project.domain.model.DeliveryAddress;
import pl.project.infrastructure.database.entity.DeliveryAddressEntity;
import pl.project.infrastructure.database.repository.mapper.DeliveryAddressEntityMapper;

@Component
public class DeliveryAddressEntityMapperImp implements DeliveryAddressEntityMapper {

    public DeliveryAddress mapFromEntity(DeliveryAddressEntity entity){
        return DeliveryAddress.builder()
                .id(entity.getId())
                .city(entity.getCity())
                .postalCode(entity.getBuildingNumber())
                .street(entity.getStreet())
                .build();
    }

    public DeliveryAddressEntity mapToEntity(DeliveryAddress domainObj){
        return DeliveryAddressEntity.builder()
                .id(domainObj.getId())
                .city(domainObj.getCity())
                .buildingNumber(domainObj.getPostalCode())
                .street(domainObj.getStreet())
                .build();
    }
}
