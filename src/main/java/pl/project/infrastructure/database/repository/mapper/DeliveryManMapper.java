package pl.project.infrastructure.database.repository.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.project.domain.model.DeliveryMan;
import pl.project.infrastructure.database.entity.DeliveryManEntity;


public interface DeliveryManMapper {

    DeliveryMan mapFromEntity(DeliveryManEntity entity);

    DeliveryManEntity mapToEntity(DeliveryMan domainObj);
}
