package pl.project.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.project.infrastructure.database.entity.RestaurantOwnerEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantOwnerJpaRepository extends JpaRepository<RestaurantOwnerEntity, Integer> {

    Optional<RestaurantOwnerEntity> findByEmail(String email);

    @Query("""
                SELECT r_ow FROM RestaurantOwnerEntity r_ow
                WHERE r_ow.isActive = true
                """)
    List<RestaurantOwnerEntity> findActive();

    @Query("""
                UPDATE RestaurantOwnerEntity r_ow
                SET r_ow.email = ?1
                WHERE r_ow.email = ?2
                """)
    @Modifying(clearAutomatically = true)
    Integer changeEmail(String newEmail, String oldEmail);

    @Query("""
                UPDATE RestaurantOwnerEntity r_ow
                SET r_ow.phoneNumber = ?1
                WHERE r_ow.email = ?2
                """)
    @Modifying(clearAutomatically = true)
    Integer changePhoneNumber(String newPhoneNumber, String email);

    @Query("""
                UPDATE RestaurantOwnerEntity r_ow
                SET r_ow.isActive = false
                WHERE r_ow.email = ?1
                """)
    @Modifying(clearAutomatically = true)
    Integer deactivate(String email);
}
