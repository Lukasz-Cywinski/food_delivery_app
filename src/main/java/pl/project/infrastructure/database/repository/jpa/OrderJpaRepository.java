package pl.project.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.project.infrastructure.database.entity.OrderEntity;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, Integer> {

    Optional<OrderEntity> findByOrderCode(String deliveryServiceCode);

    @Query("""
                UPDATE OrderEntity o
                SET o.completedDateTime = ?1
                WHERE o.orderCode = ?2
                """)
    @Modifying(clearAutomatically = true)
    void reportCompletedDateTime(OffsetDateTime deliveryDateTime, String orderCode);
}
