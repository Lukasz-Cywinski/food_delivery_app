package pl.project.infrastructure.database.repository.mapper;

import pl.project.domain.model.Customer;
import pl.project.infrastructure.database.entity.CustomerEntity;


public interface CustomerEntityMapper {

    Customer mapFromEntity(CustomerEntity entity);
    CustomerEntity mapToEntity(Customer domainObj);
}
