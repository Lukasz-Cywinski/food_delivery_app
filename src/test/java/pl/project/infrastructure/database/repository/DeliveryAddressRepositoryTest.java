package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.infrastructure.database.entity.DeliveryAddressEntity;
import pl.project.integration.configuration.MyJpaConfiguration;
import pl.project.infrastructure.database.repository.jpa.DeliveryAddressJpaRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.project.util.db.DeliveryAddressInstance.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class DeliveryAddressRepositoryTest extends MyJpaConfiguration {

    private DeliveryAddressJpaRepository deliveryAddressJpaRepository;

    @Test
    void thatDeliveryAddressCanBeSavedCorrectly(){
        //given
        DeliveryAddressEntity address1 = someDeliveryAddress1();
        DeliveryAddressEntity address2 = someDeliveryAddress2();
        DeliveryAddressEntity address3 = someDeliveryAddress3();

        //when
        deliveryAddressJpaRepository.save(address1);
        deliveryAddressJpaRepository.save(address2);
        deliveryAddressJpaRepository.save(address3);

        List<DeliveryAddressEntity> addresses = deliveryAddressJpaRepository.findAll();

        //then
        assertThat(addresses)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "restaurant")
                .contains(address1, address2, address3);

        assertEquals(3, addresses.size());
    }

    @Test
    void thatCityCanBeUpdatedCorrectly(){
        //given
        DeliveryAddressEntity address1 = someDeliveryAddress1();
        String newCityName = "new City";

        //when
        deliveryAddressJpaRepository.save(address1);
        Integer addressId = deliveryAddressJpaRepository.findAll().getFirst().getId();
        deliveryAddressJpaRepository.changeCity(newCityName, addressId);

        DeliveryAddressEntity addressFromDb = deliveryAddressJpaRepository.findById(addressId).orElseThrow();

        //then
        assertEquals(newCityName, addressFromDb.getCity());
    }

    @Test
    void thatPostalCOdeCanBeUpdatedCorrectly(){
        //given
        DeliveryAddressEntity address1 = someDeliveryAddress1();
        String newPostalCode = "new postal code";

        //when
        deliveryAddressJpaRepository.save(address1);
        Integer addressId = deliveryAddressJpaRepository.findAll().getFirst().getId();
        deliveryAddressJpaRepository.changePostalCode(newPostalCode, addressId);

        DeliveryAddressEntity addressFromDb = deliveryAddressJpaRepository.findById(addressId).orElseThrow();

        //then
        assertEquals(newPostalCode, addressFromDb.getPostalCode());
    }

    @Test
    void thatStreetCanBeUpdatedCorrectly(){
        //given
        DeliveryAddressEntity address1 = someDeliveryAddress1();
        String newStreetName = "new Street";

        //when
        deliveryAddressJpaRepository.save(address1);
        Integer addressId = deliveryAddressJpaRepository.findAll().getFirst().getId();
        deliveryAddressJpaRepository.changeStreet(newStreetName, addressId);

        DeliveryAddressEntity addressFromDb = deliveryAddressJpaRepository.findById(addressId).orElseThrow();

        //then
        assertEquals(newStreetName, addressFromDb.getStreet());
    }

}