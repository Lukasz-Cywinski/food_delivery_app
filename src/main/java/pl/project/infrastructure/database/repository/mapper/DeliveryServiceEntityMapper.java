package pl.project.infrastructure.database.repository.mapper;

import pl.project.domain.model.DeliveryService;
import pl.project.infrastructure.database.entity.DeliveryServiceEntity;


public interface DeliveryServiceEntityMapper {
    DeliveryService mapFromEntity(DeliveryServiceEntity entity);

    DeliveryServiceEntity mapToEntity(DeliveryService domainObj);
}
