package pl.project.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.project.infrastructure.database.entity.ServedAddressEntity;

@Repository
public interface ServedAddressJpaRepository extends JpaRepository<ServedAddressEntity, Integer> {

    @Query("""
                UPDATE ServedAddressEntity s_ad
                SET s_ad.city = ?1
                WHERE s_ad.id = ?2
                """)
    @Modifying(clearAutomatically = true)
    void changeCity(String newCity, Integer servedAddressId);

    @Query("""
                UPDATE ServedAddressEntity s_ad
                SET s_ad.street = ?1
                WHERE s_ad.id = ?2
                """)
    @Modifying(clearAutomatically = true)
    void changeStreet(String newStreet, Integer servedAddressId);
}
