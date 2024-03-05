package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.infrastructure.database.entity.RestaurantOwnerEntity;
import pl.project.infrastructure.database.repository.jpa.*;
import pl.project.infrastructure.security.db.UserRepository;
import pl.project.integration.configuration.Initializer;
import pl.project.integration.configuration.MyJpaConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.project.util.db.RestaurantOwnerInstance.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class RestaurantOwnerRepositoryTest extends MyJpaConfiguration
{
    private ServedAddressJpaRepository servedAddressJpaRepository;
    private RestaurantOwnerJpaRepository restaurantOwnerJpaRepository;
    private RestaurantJpaRepository restaurantJpaRepository;
    private DishPhotoJpaRepository dishPhotoJpaRepository;
    private DishCategoryJpaRepository dishCategoryJpaRepository;
    private DishJpaRepository dishJpaRepository;
    private DishOpinionJpaRepository dishOpinionJpaRepository;
    private DishCompositionJpaRepository dishCompositionJpaRepository;
    private OrderJpaRepository orderJpaRepository;
    private CustomerJpaRepository customerJpaRepository;
    private DeliveryServiceJpaRepository deliveryServiceJpaRepository;
    private DeliveryAddressJpaRepository deliveryAddressJpaRepository;
    private DeliveryManJpaRepository deliveryManJpaRepository;
    private UserRepository userRepository;

    private final Initializer initializer = new Initializer();

    @BeforeEach
    void initializeDbData(){
        initializer.setServedAddressJpaRepository(servedAddressJpaRepository);
        initializer.setRestaurantOwnerJpaRepository(restaurantOwnerJpaRepository);
        initializer.setRestaurantJpaRepository(restaurantJpaRepository);
        initializer.setDishPhotoJpaRepository(dishPhotoJpaRepository);
        initializer.setDishCategoryJpaRepository(dishCategoryJpaRepository);
        initializer.setDishJpaRepository(dishJpaRepository);
        initializer.setDishOpinionJpaRepository(dishOpinionJpaRepository);
        initializer.setDishCompositionJpaRepository(dishCompositionJpaRepository);
        initializer.setOrderJpaRepository(orderJpaRepository);
        initializer.setCustomerJpaRepository(customerJpaRepository);
        initializer.setDeliveryServiceJpaRepository(deliveryServiceJpaRepository);
        initializer.setDeliveryAddressJpaRepository(deliveryAddressJpaRepository);
        initializer.setDeliveryManJpaRepository(deliveryManJpaRepository);
        initializer.setUserRepository(userRepository);

        initializer.initializedBData();
    }

    @Test
    void thatRestaurantOwnerCanBeSavedAndModifiedCorrectly(){
        // given
        RestaurantOwnerEntity owner1 = someRestaurantOwner1();
        RestaurantOwnerEntity owner2 = someRestaurantOwner2();
        RestaurantOwnerEntity owner3 = someRestaurantOwner3();
        String ownerEmail1 = owner1.getEmail();
        String ownerEmail2 = owner2.getEmail();
        String ownerEmail3 = owner3.getEmail();

        //when
        RestaurantOwnerEntity ownerFromDb1 = restaurantOwnerJpaRepository.findByEmail(ownerEmail1).orElseThrow();
        RestaurantOwnerEntity ownerFromDb2 = restaurantOwnerJpaRepository.findByEmail(ownerEmail2).orElseThrow();
        RestaurantOwnerEntity ownerFromDb3 = restaurantOwnerJpaRepository.findByEmail(ownerEmail3).orElseThrow();

        List<RestaurantOwnerEntity> activeOwners = restaurantOwnerJpaRepository.findActive();
        List<RestaurantOwnerEntity> ownersByEmail = List.of(ownerFromDb1, ownerFromDb2, ownerFromDb3);

        //then
        assertThat(activeOwners).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "restaurants", "user")
                        .contains(owner1, owner2, owner3);
        assertThat(ownersByEmail).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "restaurants", "user")
                .contains(owner1, owner2, owner3);
        assertTrue(activeOwners.size() >= 3);
    }

    @Test
    void thatEmailCanBeModifiedCorrectly(){
        //given
        String ownerEmail = someRestaurantOwner1().getEmail();
        String newEmail = "newEmail@mail.com";

        //when
        restaurantOwnerJpaRepository.changeEmail(newEmail, ownerEmail);
        RestaurantOwnerEntity ownerFromDb1 = restaurantOwnerJpaRepository.findByEmail(newEmail).orElseThrow();

        //then
        assertEquals(newEmail, ownerFromDb1.getEmail());
    }

    @Test
    void thatPhoneNumberCanBeModifiedCorrectly(){
        //given
        String ownerEmail = someRestaurantOwner1().getEmail();
        String newPhoneNumber = "0988765";

        //when
        restaurantOwnerJpaRepository.changePhoneNumber(newPhoneNumber, ownerEmail);
        RestaurantOwnerEntity ownerFromDb1 = restaurantOwnerJpaRepository.findByEmail(ownerEmail).orElseThrow();

        //then
        assertEquals(newPhoneNumber, ownerFromDb1.getPhoneNumber());
    }

    @Test
    void isRestaurantOwnerDeactivatedCorrectly(){

        // given
        String ownerEmail = someRestaurantOwner1().getEmail();

        // when
        restaurantOwnerJpaRepository.deactivate(ownerEmail);
        List<RestaurantOwnerEntity> activeOwners = restaurantOwnerJpaRepository.findActive();

        // then
        assertTrue(activeOwners.size() >= 2);
    }
}