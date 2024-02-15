package pl.project.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.project.infrastructure.database.entity.DeliveryManEntity;

@Repository
public interface DeliveryManJpaRepository extends JpaRepository<DeliveryManEntity, Integer> {
}
