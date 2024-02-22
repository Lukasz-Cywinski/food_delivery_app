package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.infrastructure.database.entity.RestaurantOwnerEntity;
import pl.project.integration.configuration.MyJpaConfiguration;
import pl.project.infrastructure.database.repository.jpa.RestaurantOwnerJpaRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.project.util.db.RestaurantOwnerInstance.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class RestaurantOwnerRepositoryTest extends MyJpaConfiguration
{

    private RestaurantOwnerJpaRepository restaurantOwnerJpaRepository;

    @Test
    void thatRestaurantOwnerCanBeSavedAndModifiedCorrectly(){

        // given
        RestaurantOwnerEntity owner1 = someRestaurantOwner1();
        RestaurantOwnerEntity owner2 = someRestaurantOwner2();
        RestaurantOwnerEntity owner3 = someRestaurantOwner3();

        //when
        restaurantOwnerJpaRepository.save(owner1);
        restaurantOwnerJpaRepository.save(owner2);
        restaurantOwnerJpaRepository.save(owner3);

        RestaurantOwnerEntity ownerFromDb1 = restaurantOwnerJpaRepository.findByEmail(owner1.getEmail()).orElseThrow();
        RestaurantOwnerEntity ownerFromDb2 = restaurantOwnerJpaRepository.findByEmail(owner2.getEmail()).orElseThrow();
        RestaurantOwnerEntity ownerFromDb3 = restaurantOwnerJpaRepository.findByEmail(owner3.getEmail()).orElseThrow();

        List<RestaurantOwnerEntity> activeOwners = restaurantOwnerJpaRepository.findActive();

        //then
        assertThatObject(owner1)
                .usingRecursiveComparison()
                .ignoringFields("id", "restaurants")
                .isEqualTo(ownerFromDb1);

        assertThatObject(owner2)
                .usingRecursiveComparison()
                .ignoringFields("id", "restaurants")
                .isEqualTo(ownerFromDb2);

        assertThatObject(owner3)
                .usingRecursiveComparison()
                .ignoringFields("id", "restaurants")
                .isEqualTo(ownerFromDb3);

        assertEquals(3, activeOwners.size());
    }

    @Test
    void thatEmailCanBeModifiedCorrectly(){

        //given
        RestaurantOwnerEntity owner1 = someRestaurantOwner1();
        String newEmail = "newEmail@mail.com";

        //when
        restaurantOwnerJpaRepository.save(owner1);
        restaurantOwnerJpaRepository.changeEmail(newEmail, owner1.getEmail());
        RestaurantOwnerEntity ownerFromDb1 = restaurantOwnerJpaRepository.findByEmail(newEmail).orElseThrow();

        //then
        assertEquals(newEmail, ownerFromDb1.getEmail());

    }

    @Test
    void thatPhoneNumberCanBeModifiedCorrectly(){

        //given
        RestaurantOwnerEntity owner1 = someRestaurantOwner1();
        String newPhoneNumber = "0988765";

        //when
        restaurantOwnerJpaRepository.save(owner1);
        restaurantOwnerJpaRepository.changePhoneNumber(newPhoneNumber, owner1.getEmail());
        RestaurantOwnerEntity ownerFromDb1 = restaurantOwnerJpaRepository.findByEmail(owner1.getEmail()).orElseThrow();

        //then
        assertEquals(newPhoneNumber, ownerFromDb1.getPhoneNumber());

    }

    @Test
    void isRestaurantOwnerDeactivatedCorrectly(){

        // given
        RestaurantOwnerEntity owner1 = someRestaurantOwner1();
        RestaurantOwnerEntity owner2 = someRestaurantOwner2();
        RestaurantOwnerEntity owner3 = someRestaurantOwner3();

        // when
        restaurantOwnerJpaRepository.save(owner1);
        restaurantOwnerJpaRepository.save(owner2);
        restaurantOwnerJpaRepository.save(owner3);

        restaurantOwnerJpaRepository.deactivate(someRestaurantOwner3().getEmail());
        List<RestaurantOwnerEntity> activeOwners = restaurantOwnerJpaRepository.findActive();

        // then
        assertEquals(2, activeOwners.size());
    }
}