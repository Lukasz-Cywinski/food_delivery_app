package pl.project.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.project.infrastructure.database.entity.DeliveryAddressEntity;

@Repository
public interface DeliveryAddressJpaRepository extends JpaRepository<DeliveryAddressEntity, Integer> {

    @Query("""
                UPDATE DeliveryAddressEntity d_ad
                SET d_ad.city = ?1
                WHERE d_ad.id = ?2
                """)
    @Modifying(clearAutomatically = true)
    void changeCity(String newCity, Integer deliveryAddressId);

    @Query("""
                UPDATE DeliveryAddressEntity d_ad
                SET d_ad.postalCode = ?1
                WHERE d_ad.id = ?2
                """)
    @Modifying(clearAutomatically = true)
    void changePostalCode(String newPostalCode, Integer deliveryAddressId);

    @Query("""
                UPDATE DeliveryAddressEntity d_ad
                SET d_ad.street = ?1
                WHERE d_ad.id = ?2
                """)
    @Modifying(clearAutomatically = true)
    void changeStreet(String newStreet, Integer deliveryAddressId);
}
