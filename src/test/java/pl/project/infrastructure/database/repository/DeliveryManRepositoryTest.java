package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.infrastructure.database.entity.DeliveryManEntity;
import pl.project.infrastructure.database.repository.jpa.*;
import pl.project.infrastructure.security.db.UserRepository;
import pl.project.integration.configuration.Initializer;
import pl.project.integration.configuration.MyJpaConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.project.util.db.DeliveryManInstance.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class DeliveryManRepositoryTest extends MyJpaConfiguration {

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
    void initializeDbData() {
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
    void thatDeliveryManCanBeSavedAndModifiedCorrectly() {

        // given
        DeliveryManEntity deliveryMan1 = someDeliveryMan1();
        DeliveryManEntity deliveryMan2 = someDeliveryMan2();
        DeliveryManEntity deliveryMan3 = someDeliveryMan3();

        //when
        List<DeliveryManEntity> activeOwners = deliveryManJpaRepository.findActive();

        //then
        assertThat(activeOwners)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "user")
                .contains(deliveryMan1, deliveryMan2, deliveryMan3);
        assertTrue(activeOwners.size() >= 3);
    }

    @Test
    void thatPhoneNumberCanBeModifiedCorrectly() {

        //given
        String deliveryManPersonalCode = someDeliveryMan1().getPersonalCode();
        String newPhoneNumber = "0988765";

        //when
        deliveryManJpaRepository.changePhoneNumber(newPhoneNumber, deliveryManPersonalCode);
        DeliveryManEntity deliveryManFromDb1 = deliveryManJpaRepository.findByPersonalCode(deliveryManPersonalCode).orElseThrow();

        //then
        assertEquals(newPhoneNumber, deliveryManFromDb1.getPhoneNumber());

    }

    @Test
    void isDeliveryManDeactivatedCorrectly() {

        // given
        String deliveryManPersonalCode = someDeliveryMan1().getPersonalCode();

        // when
        deliveryManJpaRepository.deactivate(deliveryManPersonalCode);
        List<DeliveryManEntity> activeOwners = deliveryManJpaRepository.findActive();

        // then
        assertTrue(activeOwners.size() >= 2);
    }

    @Test
    void isDeliveryManAvailabilityStatusChangedCorrectly() {

        // given
        String deliveryManPersonalCode = someDeliveryMan1().getPersonalCode();

        // when
        deliveryManJpaRepository.changeAvailabilityStatus(false, deliveryManPersonalCode);
        List<DeliveryManEntity> availableOwners = deliveryManJpaRepository.findAvailable();

        // then
        assertTrue(availableOwners.size() >= 2);
    }

}