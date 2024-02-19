package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.infrastructure.database.entity.RestaurantEntity;
import pl.project.infrastructure.database.entity.ServedAddressEntity;
import pl.project.infrastructure.database.repository.configuration.MyJpaConfiguration;
import pl.project.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import pl.project.infrastructure.database.repository.jpa.ServedAddressJpaRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.project.util.db.RestaurantInstance.someRestaurant1;
import static pl.project.util.db.ServedAddressInstance.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class ServedAddressRepositoryTest extends MyJpaConfiguration {

    ServedAddressJpaRepository servedAddressJpaRepository;
    RestaurantJpaRepository restaurantJpaRepository;

    @Test
    void thatServedAddressCanBeSavedCorrectly(){

        //given
        RestaurantEntity restaurant1 = someRestaurant1();

        ServedAddressEntity address1 = someServedAddress1();
        ServedAddressEntity address2 = someServedAddress2();
        ServedAddressEntity address3 = someServedAddress3();

        address1.setRestaurant(restaurant1);
        address2.setRestaurant(restaurant1);
        address3.setRestaurant(restaurant1);

        //when
        restaurantJpaRepository.save(restaurant1);

        servedAddressJpaRepository.save(address1);
        servedAddressJpaRepository.save(address2);
        servedAddressJpaRepository.save(address3);

        List<ServedAddressEntity> addresses = servedAddressJpaRepository.findAll();

        //then
        assertThat(addresses)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "restaurant")
                .contains(address1, address2, address3);

        assertEquals(3, addresses.size());
    }

    @Test
    void thatCityCanBeUpdatedCorrectly(){
        //given
        RestaurantEntity restaurant1 = someRestaurant1();
        ServedAddressEntity address1 = someServedAddress1();
        address1.setRestaurant(restaurant1);

        String newCityName = "new City";

        //when
        restaurantJpaRepository.save(restaurant1);
        servedAddressJpaRepository.save(address1);
        Integer addressId = servedAddressJpaRepository.findAll().getFirst().getId();
        servedAddressJpaRepository.changeCity(newCityName, addressId);

        ServedAddressEntity addressFromDb = servedAddressJpaRepository.findById(addressId).orElseThrow();

        //then
        assertEquals(newCityName, addressFromDb.getCity());
    }

    @Test
    void thatStreetCanBeUpdatedCorrectly(){
        //given
        RestaurantEntity restaurant1 = someRestaurant1();
        ServedAddressEntity address1 = someServedAddress1();
        address1.setRestaurant(restaurant1);

        String newStreetName = "new Street";

        //when
        restaurantJpaRepository.save(restaurant1);
        servedAddressJpaRepository.save(address1);
        Integer addressId = servedAddressJpaRepository.findAll().getFirst().getId();
        servedAddressJpaRepository.changeStreet(newStreetName, addressId);

        ServedAddressEntity addressFromDb = servedAddressJpaRepository.findById(addressId).orElseThrow();

        //then
        assertEquals(newStreetName, addressFromDb.getStreet());
    }

    @Test
    void thatServedAddressCanBeDeletedCorrectly(){
        //given
        RestaurantEntity restaurant1 = someRestaurant1();

        ServedAddressEntity address1 = someServedAddress1();
        ServedAddressEntity address2 = someServedAddress2();
        address1.setRestaurant(restaurant1);
        address2.setRestaurant(restaurant1);

        //when
        restaurantJpaRepository.save(restaurant1);
        servedAddressJpaRepository.save(address1);
        servedAddressJpaRepository.save(address2);

        servedAddressJpaRepository.delete(address1);

        List<ServedAddressEntity> addresses = servedAddressJpaRepository.findAll();

        //then
        assertEquals(1, addresses.size());
    }

}