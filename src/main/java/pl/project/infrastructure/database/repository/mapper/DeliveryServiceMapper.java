package pl.project.infrastructure.database.repository.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.project.domain.model.DeliveryService;
import pl.project.infrastructure.database.entity.DeliveryServiceEntity;


public interface DeliveryServiceMapper {
    DeliveryService mapFromEntity(DeliveryServiceEntity entity);

    DeliveryServiceEntity mapToEntity(DeliveryService domainObj);
}
