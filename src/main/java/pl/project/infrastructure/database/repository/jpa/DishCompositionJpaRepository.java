package pl.project.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.project.infrastructure.database.entity.DishCompositionEntity;
import pl.project.infrastructure.database.entity.OrderEntity;

import java.util.List;
import java.util.Set;

@Repository
public interface DishCompositionJpaRepository extends JpaRepository<DishCompositionEntity, Integer> {

    List<DishCompositionEntity> findByOrder_OrderCode(String orderCode);

    @Query("""
                SELECT o FROM DishCompositionEntity d_co
                JOIN d_co.order o
                JOIN d_co.dish d
                JOIN d.restaurant r
                WHERE r.restaurantCode = ?1
                AND o.completedDateTime IS NULL
                """)
    Set<OrderEntity> findActiveOrdersForRestaurant(String restaurantCode);

    @Query("""
                SELECT o FROM DishCompositionEntity d_co
                JOIN d_co.order o
                JOIN d_co.dish d
                JOIN d.restaurant r
                WHERE r.restaurantCode = ?1
                AND o.completedDateTime IS NOT NULL
                """)
    Set<OrderEntity> findRealizedOrdersForRestaurant(String restaurantCode);
}
