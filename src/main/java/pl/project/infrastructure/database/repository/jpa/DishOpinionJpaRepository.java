package pl.project.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.project.infrastructure.database.entity.CustomerEntity;
import pl.project.infrastructure.database.entity.DishOpinionEntity;

@Repository
public interface DishOpinionJpaRepository extends JpaRepository<DishOpinionEntity, Integer> {
}
