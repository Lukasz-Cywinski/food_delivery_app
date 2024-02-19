package pl.project.infrastructure.database.repository.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.project.domain.model.DeliveryAddress;
import pl.project.infrastructure.database.entity.DeliveryAddressEntity;


public interface DeliveryAddressMapper {

    DeliveryAddress mapFromEntity(DeliveryAddressEntity entity);

    DeliveryAddressEntity mapToEntity(DeliveryAddress domainObj);
}
