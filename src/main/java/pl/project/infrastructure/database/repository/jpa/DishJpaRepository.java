package pl.project.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.project.infrastructure.database.entity.DishCategoryEntity;
import pl.project.infrastructure.database.entity.DishEntity;
import pl.project.infrastructure.database.entity.DishPhotoEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface DishJpaRepository extends JpaRepository<DishEntity, Integer> {

    Optional<DishEntity> findByDishCode(String dishCode);

    @Query("""
                SELECT d FROM DishEntity d
                WHERE d.isActive = true
                """)
    List<DishEntity> findActive();

    @Query("""
                SELECT d FROM DishEntity d
                JOIN d.restaurant r
                WHERE d.isActive = true
                AND r.restaurantCode = ?1
                """)
    List<DishEntity> findActiveByRestaurant(String restaurantCode);

    @Query("""
                UPDATE DishEntity d
                SET d.name = ?1
                WHERE d.dishCode = ?2
                """)
    @Modifying(clearAutomatically = true)
    Integer changeDishName(String newDishName, String dishCode);

    @Query("""
                UPDATE DishEntity d
                SET d.description = ?1
                WHERE d.dishCode = ?2
                """)
    @Modifying(clearAutomatically = true)
    Integer changeDishDescription(String newDishDescription, String dishCode);

    @Query("""
                UPDATE DishEntity d
                SET d.price = ?1
                WHERE d.dishCode = ?2
                """)
    @Modifying(clearAutomatically = true)
    Integer changeDishPrice(BigDecimal newDishPrice, String dishCode);

    @Query("""
                UPDATE DishEntity d
                SET d.averagePreparationTimeMin = ?1
                WHERE d.dishCode = ?2
                """)
    @Modifying(clearAutomatically = true)
    Integer changeDishPreparationTime(Integer minutes, String dishCode);

    @Query("""
                UPDATE DishEntity d
                SET d.dishPhoto = ?1
                WHERE d.dishCode = ?2
                """)
    @Modifying(clearAutomatically = true)
    Integer changeDishPhoto(DishPhotoEntity newDishPhoto, String dishCode);

    @Query(value = """
                UPDATE dish d
                SET dish_category_id = ?1
                WHERE dish_code = ?2
                """, nativeQuery = true)
    @Modifying(clearAutomatically = true)
    Integer changeDishCategory(Integer newDishCategoryId, String dishCode);

    @Query("""
                UPDATE DishEntity d
                SET d.isActive = false
                WHERE d.dishCode = ?1
                """)
    @Modifying(clearAutomatically = true)
    Integer deactivate(String dishCode);
}
