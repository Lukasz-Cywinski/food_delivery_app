package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.infrastructure.database.entity.RestaurantEntity;
import pl.project.infrastructure.database.entity.ServedAddressEntity;
import pl.project.infrastructure.database.repository.jpa.*;
import pl.project.infrastructure.security.db.UserRepository;
import pl.project.integration.configuration.Initializer;
import pl.project.integration.configuration.MyJpaConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.project.util.db.ServedAddressInstance.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class ServedAddressRepositoryTest extends MyJpaConfiguration {

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
    void thatServedAddressCanBeSavedCorrectly(){

        //given
        ServedAddressEntity address1 = someServedAddress1();
        ServedAddressEntity address2 = someServedAddress2();
        ServedAddressEntity address3 = someServedAddress3();

        //when
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
        String newCityName = "new City";
        Integer addressId = initializer.SAVED_SERVED_ADDRESSES.getFirst().getId();

        //when
        servedAddressJpaRepository.changeCity(newCityName, addressId);
        ServedAddressEntity addressFromDb = servedAddressJpaRepository.findById(addressId).orElseThrow();

        //then
        assertEquals(newCityName, addressFromDb.getCity());
    }

    @Test
    void thatStreetCanBeUpdatedCorrectly(){
        //given
        String newStreetName = "new Street";
        Integer addressId = initializer.SAVED_SERVED_ADDRESSES.getFirst().getId();

        //when
        servedAddressJpaRepository.changeStreet(newStreetName, addressId);
        ServedAddressEntity addressFromDb = servedAddressJpaRepository.findById(addressId).orElseThrow();

        //then
        assertEquals(newStreetName, addressFromDb.getStreet());
    }

    @Test
    void thatServedAddressCanBeDeletedCorrectly(){
        //given
        ServedAddressEntity address1 = initializer.SAVED_SERVED_ADDRESSES.getFirst();

        //when
        servedAddressJpaRepository.deleteServedAddress(address1);
        List<ServedAddressEntity> addresses = servedAddressJpaRepository.findAll();

        //then
        assertEquals(2, addresses.size());
    }

    @Test
    void thatServedAddressCanBeFindByRestaurantCorrectly(){
        //given
        RestaurantEntity restaurant = initializer.SAVED_RESTAURANTS.getFirst();

        ServedAddressEntity address1 = someServedAddress1();
        ServedAddressEntity address2 = someServedAddress2();
        ServedAddressEntity address3 = someServedAddress3();

        address1.setRestaurant(restaurant);
        address2.setRestaurant(restaurant);
        address3.setRestaurant(restaurant);

        //when
        servedAddressJpaRepository.save(address1);
        servedAddressJpaRepository.save(address2);
        servedAddressJpaRepository.save(address3);

        List<ServedAddressEntity> addresses = servedAddressJpaRepository.findServedAddressesForRestaurant(restaurant);

        //then
        assertTrue(addresses.size() >= 3);
    }

    @Test
    void thatRestaurantCanBeFindByServedAddressCorrectly(){
        //given
        ServedAddressEntity address1 = initializer.SAVED_SERVED_ADDRESSES.getFirst();

        //when
        List<RestaurantEntity> restaurants = servedAddressJpaRepository.findRestaurantsForServedAddress(address1);

        //then
        assertEquals(1, restaurants.size());
    }

}