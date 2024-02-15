package pl.project.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.project.infrastructure.database.entity.CustomerEntity;
import pl.project.infrastructure.database.entity.DishCategoryEntity;

@Repository
public interface DishCategoryJpaRepository extends JpaRepository<DishCategoryEntity, Integer> {
}
