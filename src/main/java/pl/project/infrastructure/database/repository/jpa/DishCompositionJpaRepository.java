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
    Integer deleteByOrder_OrderCode(String orderCode);

    @Query("""
                SELECT d_co FROM DishCompositionEntity d_co
                JOIN d_co.order o
                JOIN d_co.dish d
                JOIN o.customer c
                WHERE c.email = ?1
                AND o.completedDateTime IS NULL
                """)
    List<DishCompositionEntity> findActiveForCustomer(String customerEmail); //TODO JPA test

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

//    @Query("""
//                DELETE d_co FROM DishCompositionEntity d_co
//                JOIN d_co.order o
//                WHERE o.orderCode = ?1
//                """)
//    @Modifying(clearAutomatically = true)
//    Integer deleteDishCompositionsForOrder(String orderCode); //TODO JPA TEST
}
