package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.DeliveryServiceDAO;
import pl.project.domain.model.DeliveryService;
import pl.project.infrastructure.database.repository.jpa.DeliveryServiceJpaRepository;
import pl.project.infrastructure.database.repository.mapper.DeliveryServiceEntityMapper;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class DeliveryServiceRepository implements DeliveryServiceDAO {

    private final DeliveryServiceJpaRepository deliveryServiceJpaRepository;
    private final DeliveryServiceEntityMapper deliveryServiceEntityMapper;

    @Override
    public Optional<DeliveryService> createDeliveryService(DeliveryService deliveryService) {
        return Optional.ofNullable(
                deliveryServiceEntityMapper.mapFromEntity(
                        deliveryServiceJpaRepository.save(
                                deliveryServiceEntityMapper.mapToEntity(deliveryService)
                        )
                )
        );
    }

    @Override
    public Optional<DeliveryService> getDeliveryServiceByDeliveryServiceCode(String deliveryServiceCode) {
        return deliveryServiceJpaRepository.findByDeliveryServiceCode(deliveryServiceCode)
                .map(deliveryServiceEntityMapper::mapFromEntity);
    }

    @Override
    public Integer reportCompletedDateTime(OffsetDateTime deliveryDateTime, String deliveryServiceCode) {
        return deliveryServiceJpaRepository.reportCompletedDateTime(deliveryDateTime, deliveryServiceCode);
    }
}
