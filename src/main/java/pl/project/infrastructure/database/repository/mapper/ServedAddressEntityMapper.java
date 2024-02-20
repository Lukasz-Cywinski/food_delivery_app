package pl.project.infrastructure.database.repository.mapper;

import pl.project.domain.model.ServedAddress;
import pl.project.infrastructure.database.entity.ServedAddressEntity;


public interface ServedAddressEntityMapper {

    ServedAddress mapFromEntity(ServedAddressEntity entity);

    ServedAddressEntity mapToEntity(ServedAddress domainObj);
}
