package pl.project.infrastructure.database.repository.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.project.domain.model.ServedAddress;
import pl.project.infrastructure.database.entity.ServedAddressEntity;


public interface ServedAddressMapper {

    ServedAddress mapFromEntity(ServedAddressEntity entity);

    ServedAddressEntity mapToEntity(ServedAddress domainObj);
}
