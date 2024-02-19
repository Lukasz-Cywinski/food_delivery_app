package pl.project.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.project.infrastructure.database.entity.DishCompositionEntity;
import pl.project.infrastructure.database.entity.OrderEntity;

import java.util.List;

@Repository
public interface DishCompositionJpaRepository extends JpaRepository<DishCompositionEntity, Integer> {

    @Query("""
                SELECT d_co FROM DishCompositionEntity d_co
                WHERE d_co.order = ?1
                """)
    List<DishCompositionEntity> findByOrder(OrderEntity order);
}
