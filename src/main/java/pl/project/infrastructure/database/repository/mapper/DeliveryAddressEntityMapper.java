package pl.project.infrastructure.database.repository.mapper;

import pl.project.domain.model.DeliveryAddress;
import pl.project.infrastructure.database.entity.DeliveryAddressEntity;


public interface DeliveryAddressEntityMapper {

    DeliveryAddress mapFromEntity(DeliveryAddressEntity entity);

    DeliveryAddressEntity mapToEntity(DeliveryAddress domainObj);
}
