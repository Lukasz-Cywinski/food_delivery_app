package pl.project.infrastructure.database.repository.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.project.domain.model.Customer;
import pl.project.infrastructure.database.entity.CustomerEntity;


public interface CustomerMapper {

    Customer mapFromEntity(CustomerEntity entity);
    CustomerEntity mapToEntity(Customer domainObj);
}
