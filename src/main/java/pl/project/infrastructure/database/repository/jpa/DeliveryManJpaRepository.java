package pl.project.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.project.infrastructure.database.entity.DeliveryManEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryManJpaRepository extends JpaRepository<DeliveryManEntity, Integer> {

    Optional<DeliveryManEntity> findByPersonalCode(String personalCode);

    @Query("""
                SELECT d_ma FROM DeliveryManEntity d_ma
                WHERE d_ma.isActive = true
                """)
    List<DeliveryManEntity> findActive();

    @Query("""
                SELECT d_ma FROM DeliveryManEntity d_ma
                WHERE d_ma.isActive = true
                AND d_ma.isAvailable = true
                """)
    List<DeliveryManEntity> findAvailable();

    @Query("""
                UPDATE DeliveryManEntity d_ma
                SET d_ma.phoneNumber = ?1
                WHERE d_ma.personalCode = ?2
                """)
    @Modifying(clearAutomatically = true)
    Integer changePhoneNumber(String newPhoneNumber, String personalCode);

    @Query("""
                UPDATE DeliveryManEntity d_ma
                SET d_ma.isActive = false
                WHERE d_ma.personalCode = ?1
                """)
    @Modifying(clearAutomatically = true)
    Integer deactivate(String personalCode);

    @Query("""
                UPDATE DeliveryManEntity d_ma
                SET d_ma.isAvailable = ?1
                WHERE d_ma.personalCode = ?2
                """)
    @Modifying(clearAutomatically = true)
    Integer changeAvailabilityStatus(boolean availabilityStatus, String personalCode);
}
