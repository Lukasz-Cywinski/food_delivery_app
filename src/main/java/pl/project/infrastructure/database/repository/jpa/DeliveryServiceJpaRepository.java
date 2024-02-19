package pl.project.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.project.infrastructure.database.entity.DeliveryServiceEntity;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public interface DeliveryServiceJpaRepository extends JpaRepository<DeliveryServiceEntity, Integer> {

    Optional<DeliveryServiceEntity> findByDeliveryServiceCode(String deliveryServiceCode);

    @Query("""
                UPDATE DeliveryServiceEntity d_se
                SET d_se.completedDateTime = ?1
                WHERE d_se.deliveryServiceCode = ?2
                """)
    @Modifying(clearAutomatically = true)
    void reportCompletedDateTime(OffsetDateTime deliveryDateTime, String deliveryServiceCode);
}
