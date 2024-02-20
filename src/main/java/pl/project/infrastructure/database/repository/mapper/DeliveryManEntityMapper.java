package pl.project.infrastructure.database.repository.mapper;

import pl.project.domain.model.DeliveryMan;
import pl.project.infrastructure.database.entity.DeliveryManEntity;


public interface DeliveryManEntityMapper {

    DeliveryMan mapFromEntity(DeliveryManEntity entity);

    DeliveryManEntity mapToEntity(DeliveryMan domainObj);
}
