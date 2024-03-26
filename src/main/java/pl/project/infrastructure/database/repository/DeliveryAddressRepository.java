package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.DeliveryAddressDAO;
import pl.project.domain.model.DeliveryAddress;
import pl.project.infrastructure.database.repository.jpa.DeliveryAddressJpaRepository;
import pl.project.infrastructure.database.repository.mapper.DeliveryAddressEntityMapper;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class DeliveryAddressRepository implements DeliveryAddressDAO {

    private final DeliveryAddressJpaRepository deliveryAddressJpaRepository;
    private final DeliveryAddressEntityMapper deliveryAddressEntityMapper;

    @Override
    public Optional<DeliveryAddress> createDeliveryAddress(DeliveryAddress deliveryAddress) {
        return Optional.ofNullable(
                deliveryAddressEntityMapper.mapFromEntity(
                        deliveryAddressJpaRepository.save(
                                deliveryAddressEntityMapper.mapToEntity(deliveryAddress)
                        )
                )
        );
    }

    @Override
    public Integer changeDeliveryAddressCity(String newCity, Integer deliveryAddressId) {
        return deliveryAddressJpaRepository.changeCity(newCity, deliveryAddressId);
    }

    @Override
    public Integer changeDeliveryAddressPostalCode(String newPostalCode, Integer deliveryAddressId) {
        return deliveryAddressJpaRepository.changeBuildingNumber(newPostalCode, deliveryAddressId);
    }

    @Override
    public Integer changeDeliveryAddressStreet(String newStreet, Integer deliveryAddressId) {
        return deliveryAddressJpaRepository.changeStreet(newStreet, deliveryAddressId);
    }
}
