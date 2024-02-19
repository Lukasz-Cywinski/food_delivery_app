package pl.project.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.project.infrastructure.database.entity.DishCategoryEntity;

@Repository
public interface DishCategoryJpaRepository extends JpaRepository<DishCategoryEntity, Integer> {

    @Query("""
                UPDATE DishCategoryEntity d_ca
                SET d_ca.description = ?1
                WHERE d_ca.id = ?2
                """)
    @Modifying(clearAutomatically = true)
    void changeDescription(String newDescription, Integer dishCategoryId);
}
