package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.DeliveryAddressDAO;
import pl.project.infrastructure.database.repository.jpa.DeliveryAddressJpaRepository;
import pl.project.infrastructure.database.repository.mapper.DeliveryAddressMapper;

@Repository
@AllArgsConstructor
public class DeliveryAddressRepository implements DeliveryAddressDAO {

    private final DeliveryAddressJpaRepository deliveryAddressJpaRepository;
    private final DeliveryAddressMapper deliveryAddressMapper;
}