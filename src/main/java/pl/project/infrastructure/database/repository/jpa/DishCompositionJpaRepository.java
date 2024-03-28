package pl.project.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.project.infrastructure.database.entity.DishCompositionEntity;
import pl.project.infrastructure.database.entity.OrderEntity;
import pl.project.infrastructure.database.entity.RestaurantEntity;

import java.util.List;
import java.util.Set;

@Repository
public interface DishCompositionJpaRepository extends JpaRepository<DishCompositionEntity, Integer> {

    @Query("""
                SELECT d_co FROM DishCompositionEntity d_co
                WHERE d_co.order = ?1
                """)
    List<DishCompositionEntity> findByOrder(OrderEntity order);

//@Query("""
//                SELECT o FROM DishCompositionEntity d_co
//                JOIN d_co.order o
//                JOIN d_co.dish d
//                WHERE d.restaurant = ?1
//                AND o.completedDateTime IS NULL
//                """)
//    Set<OrderEntity> findActiveOrdersForRestaurant(RestaurantEntity restaurant);

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
