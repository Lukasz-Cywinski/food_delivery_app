package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.infrastructure.database.entity.DeliveryManEntity;
import pl.project.infrastructure.database.repository.configuration.MyJpaConfiguration;
import pl.project.infrastructure.database.repository.jpa.DeliveryManJpaRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.project.util.db.DeliveryManInstance.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class DeliveryManRepositoryTest extends MyJpaConfiguration {

    private DeliveryManJpaRepository deliveryManJpaRepository;

    @Test
    void thatDeliveryManCanBeSavedAndModifiedCorrectly(){

        // given
        DeliveryManEntity deliveryMan1 = someDeliveryMan1();
        DeliveryManEntity deliveryMan2 = someDeliveryMan2();
        DeliveryManEntity deliveryMan3 = someDeliveryMan3();

        //when
        deliveryManJpaRepository.save(deliveryMan1);
        deliveryManJpaRepository.save(deliveryMan2);
        deliveryManJpaRepository.save(deliveryMan3);

        List<DeliveryManEntity> activeOwners = deliveryManJpaRepository.findActive();

        //then
        assertThat(activeOwners)
                        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                                .contains(deliveryMan1, deliveryMan2, deliveryMan3);

        assertEquals(3, activeOwners.size());
    }

    @Test
    void thatPhoneNumberCanBeModifiedCorrectly(){

        //given
        DeliveryManEntity deliveryMan1 = someDeliveryMan1();
        String newPhoneNumber = "0988765";

        //when
        deliveryManJpaRepository.save(deliveryMan1);
        deliveryManJpaRepository.changePhoneNumber(newPhoneNumber, deliveryMan1.getPersonalCode());
        DeliveryManEntity deliveryManFromDb1 = deliveryManJpaRepository.findByPersonalCode(deliveryMan1.getPersonalCode()).orElseThrow();

        //then
        assertEquals(newPhoneNumber, deliveryManFromDb1.getPhoneNumber());

    }

    @Test
    void isDeliveryManDeactivatedCorrectly(){

        // given
        DeliveryManEntity deliveryMan1 = someDeliveryMan1();
        DeliveryManEntity deliveryMan2 = someDeliveryMan2();
        DeliveryManEntity deliveryMan3 = someDeliveryMan3();

        // when
        deliveryManJpaRepository.save(deliveryMan1);
        deliveryManJpaRepository.save(deliveryMan2);
        deliveryManJpaRepository.save(deliveryMan3);

        deliveryManJpaRepository.deactivate(someDeliveryMan3().getPersonalCode());
        List<DeliveryManEntity> activeOwners = deliveryManJpaRepository.findActive();

        // then
        assertEquals(2, activeOwners.size());
    }

    @Test
    void isDeliveryManAvailabilityStatusChangedCorrectly(){

        // given
        DeliveryManEntity deliveryMan1 = someDeliveryMan1();
        DeliveryManEntity deliveryMan2 = someDeliveryMan2();
        DeliveryManEntity deliveryMan3 = someDeliveryMan3();

        // when
        deliveryManJpaRepository.save(deliveryMan1);
        deliveryManJpaRepository.save(deliveryMan2);
        deliveryManJpaRepository.save(deliveryMan3);

        deliveryManJpaRepository.changeAvailabilityStatus(false, someDeliveryMan3().getPersonalCode());
        List<DeliveryManEntity> availableOwners = deliveryManJpaRepository.findAvailable();

        // then
        assertEquals(2, availableOwners.size());
    }

}