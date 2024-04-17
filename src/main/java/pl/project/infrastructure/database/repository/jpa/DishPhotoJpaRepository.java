package pl.project.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.project.infrastructure.database.entity.DishPhotoEntity;

import java.util.Optional;

@Repository
public interface DishPhotoJpaRepository extends JpaRepository<DishPhotoEntity, Integer> {

    @Query("""
                SELECT d_ph FROM DishPhotoEntity d_ph
                WHERE d_ph.url = ?1
                """)
    Optional<DishPhotoEntity> findByOrderUrl(String url); //TODO JPA test

    @Query("""
                UPDATE DishPhotoEntity d_ph
                SET d_ph.name = ?1
                WHERE d_ph.id = ?2
                """)
    @Modifying(clearAutomatically = true)
    Integer changePhotoName(String newPhotoName, Integer dishPhotoId);

}
