package pl.project.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.project.infrastructure.database.entity.CustomerEntity;
import pl.project.infrastructure.database.entity.DeliveryAddressEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Integer> {

    Optional<CustomerEntity> findByEmail(String email);

    @Query("""
                SELECT c FROM CustomerEntity c
                WHERE c.isActive = true
                """)
    List<CustomerEntity> findActive();

    @Query("""
                UPDATE CustomerEntity c
                SET c.name = ?1
                WHERE c.email = ?2
                """)
    @Modifying(clearAutomatically = true)
    void changeName(String newName, String email);

    @Query("""
                UPDATE CustomerEntity c
                SET c.surname = ?1
                WHERE c.email = ?2
                """)
    @Modifying(clearAutomatically = true)
    void changeSurname(String newSurname, String email);

    @Query("""
                UPDATE CustomerEntity c
                SET c.phoneNumber = ?1
                WHERE c.email = ?2
                """)
    @Modifying(clearAutomatically = true)
    void changePhoneNumber(String newPhoneNumber, String email);

    @Query("""
                UPDATE CustomerEntity c
                SET c.email = ?1
                WHERE c.email = ?2
                """)
    @Modifying(clearAutomatically = true)
    void changeEmail(String newEmail, String oldEmail);

    @Query("""
                UPDATE CustomerEntity c
                SET c.deliveryAddress = ?1
                WHERE c.email = ?2
                """)
    @Modifying(clearAutomatically = true)
    void changeDeliveryAddress(DeliveryAddressEntity newDeliveryAddress, String oldEmail);

    @Query("""
                UPDATE CustomerEntity c
                SET c.isActive = false
                WHERE c.email = ?1
                """)
    @Modifying(clearAutomatically = true)
    void deactivate(String personalCode);

}
