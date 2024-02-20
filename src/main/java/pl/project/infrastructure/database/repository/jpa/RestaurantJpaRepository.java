package pl.project.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.project.infrastructure.database.entity.DishEntity;
import pl.project.infrastructure.database.entity.RestaurantEntity;
import pl.project.infrastructure.database.entity.RestaurantOwnerEntity;
import pl.project.infrastructure.database.entity.ServedAddressEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, Integer> {

    Optional<RestaurantEntity> findByRestaurantCode(String restaurantCode);

    @Query("""
                SELECT r FROM RestaurantEntity r
                WHERE r.isActive = true
                """)
    List<RestaurantEntity> findActive();

    @Query("""
                SELECT d FROM DishEntity d
                WHERE d.isActive = true
                AND d.restaurant = ?1
                """)
    List<DishEntity> findActiveDishes(RestaurantEntity restaurant);

    @Query("""
                SELECT s_add FROM ServedAddressEntity s_add
                WHERE s_add.restaurant = ?1
                """)
    List<ServedAddressEntity> findServedAddresses(RestaurantEntity restaurant);

    @Query("""
                UPDATE RestaurantEntity r
                SET r.name = ?1
                WHERE r.restaurantCode = ?2
                """)
    @Modifying(clearAutomatically = true)
    Integer changeName(String newRestaurantName, String restaurantCode);

    @Query("""
                UPDATE RestaurantEntity r
                SET r.restaurantOwner = ?1
                WHERE r.restaurantCode = ?2
                """)
    @Modifying(clearAutomatically = true)
    Integer changeOwner(RestaurantOwnerEntity newRestaurantOwner, String restaurantCode);

    @Query("""
                UPDATE RestaurantEntity r
                SET r.isActive = false
                WHERE r.restaurantCode = ?1
                """)
    @Modifying(clearAutomatically = true)
    Integer deactivate(String restaurantCode);

}
