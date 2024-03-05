package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.infrastructure.database.entity.DeliveryAddressEntity;
import pl.project.infrastructure.database.repository.jpa.*;
import pl.project.infrastructure.security.db.UserRepository;
import pl.project.integration.configuration.Initializer;
import pl.project.integration.configuration.MyJpaConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.project.util.db.DeliveryAddressInstance.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class DeliveryAddressRepositoryTest extends MyJpaConfiguration {

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
    void thatDeliveryAddressCanBeSavedCorrectly(){
        //given
        DeliveryAddressEntity address1 = someDeliveryAddress1();
        DeliveryAddressEntity address2 = someDeliveryAddress2();
        DeliveryAddressEntity address3 = someDeliveryAddress3();

        //when
        List<DeliveryAddressEntity> addresses = deliveryAddressJpaRepository.findAll();

        //then
        assertThat(addresses)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "restaurant")
                .contains(address1, address2, address3);

        assertTrue(addresses.size() >= 3);
    }

    @Test
    void thatCityCanBeUpdatedCorrectly(){
        //given
        String newCityName = "new City";
        Integer addressId = initializer.SAVED_DELIVERY_ADDRESSES.getFirst().getId();

        //when
        deliveryAddressJpaRepository.changeCity(newCityName, addressId);
        DeliveryAddressEntity addressFromDb = deliveryAddressJpaRepository.findById(addressId).orElseThrow();

        //then
        assertEquals(newCityName, addressFromDb.getCity());
    }

    @Test
    void thatPostalCodeCanBeUpdatedCorrectly(){
        //given
        String newPostalCode = "new postal code";
        Integer addressId = initializer.SAVED_DELIVERY_ADDRESSES.getFirst().getId();

        //when
        deliveryAddressJpaRepository.changePostalCode(newPostalCode, addressId);
        DeliveryAddressEntity addressFromDb = deliveryAddressJpaRepository.findById(addressId).orElseThrow();

        //then
        assertEquals(newPostalCode, addressFromDb.getPostalCode());
    }

    @Test
    void thatStreetCanBeUpdatedCorrectly(){
        //given
        String newStreetName = "new Street";
        Integer addressId = initializer.SAVED_DELIVERY_ADDRESSES.getFirst().getId();

        //when
        deliveryAddressJpaRepository.changeStreet(newStreetName, addressId);
        DeliveryAddressEntity addressFromDb = deliveryAddressJpaRepository.findById(addressId).orElseThrow();

        //then
        assertEquals(newStreetName, addressFromDb.getStreet());
    }

}