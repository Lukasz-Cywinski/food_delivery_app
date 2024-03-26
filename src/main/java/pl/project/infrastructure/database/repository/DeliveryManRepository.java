package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.DeliveryManDAO;
import pl.project.domain.model.DeliveryMan;
import pl.project.infrastructure.database.repository.jpa.DeliveryManJpaRepository;
import pl.project.infrastructure.database.repository.mapper.DeliveryManEntityMapper;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class DeliveryManRepository implements DeliveryManDAO {

    private final DeliveryManJpaRepository deliveryManJpaRepository;
    private final DeliveryManEntityMapper deliveryManEntityMapper;

    @Override
    public Optional<DeliveryMan> createDeliveryMan(DeliveryMan deliveryMan) {
        return Optional.ofNullable(
                deliveryManEntityMapper.mapFromEntity(
                        deliveryManJpaRepository.save(
                                deliveryManEntityMapper.mapToEntity(deliveryMan)
                        )
                )
        );
    }

    @Override
    public Optional<DeliveryMan> getDeliveryManByPersonalCode(String personalCode) {
        return deliveryManJpaRepository.findByPersonalCode(personalCode)
                .map(deliveryManEntityMapper::mapFromEntity);
    }

    @Override
    public List<DeliveryMan> getActiveDeliveryMen() {
        return deliveryManJpaRepository.findActive().stream()
                .map(deliveryManEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public List<DeliveryMan> getAvailableDeliveryMen() {
        return deliveryManJpaRepository.findAvailable().stream()
                .map(deliveryManEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Integer changeDeliveryManPhoneNumber(String newPhoneNumber, String personalCode) {
        return deliveryManJpaRepository.changePhoneNumber(newPhoneNumber, personalCode);
    }

    @Override
    public Integer deactivateDeliveryMan(String personalCode) {
        return deliveryManJpaRepository.deactivate(personalCode);
    }

    @Override
    public Integer changeDeliveryManAvailabilityStatus(boolean availabilityStatus, String personalCode) {
        return deliveryManJpaRepository.changeAvailabilityStatus(availabilityStatus, personalCode);
    }
}
