package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.infrastructure.database.entity.CustomerEntity;
import pl.project.infrastructure.database.entity.DeliveryAddressEntity;
import pl.project.infrastructure.database.repository.jpa.*;
import pl.project.infrastructure.security.db.UserRepository;
import pl.project.integration.configuration.Initializer;
import pl.project.integration.configuration.MyJpaConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.project.util.db.CustomerInstance.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class CustomerRepositoryTest extends MyJpaConfiguration {

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
    void thatCustomerCanBeSavedCorrectly() {

        // given
        CustomerEntity customer1 = someCustomer1();
        CustomerEntity customer2 = someCustomer2();
        CustomerEntity customer3 = someCustomer3();

        //when
        List<CustomerEntity> activeCustomers = customerJpaRepository.findActive();

        //then
        assertThat(activeCustomers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "deliveryAddress", "user")
                .contains(customer1, customer2, customer3);
        assertTrue(activeCustomers.size() >= 3);
    }

    @Test
    void thatNameCanBeModifiedCorrectly() {

        //given
        String customerEmail = someCustomer1().getEmail();
        String newName = "new Name";

        //when
        customerJpaRepository.changeName(newName, customerEmail);
        CustomerEntity customerFromDb1 = customerJpaRepository.findByEmail(customerEmail).orElseThrow();

        //then
        assertEquals(newName, customerFromDb1.getName());
    }

    @Test
    void thatSurnameCanBeModifiedCorrectly() {

        //given
        String customerEmail = someCustomer1().getEmail();
        String newSurname = "new Surname";

        //when
        customerJpaRepository.changeSurname(newSurname, customerEmail);
        CustomerEntity customerFromDb1 = customerJpaRepository.findByEmail(customerEmail).orElseThrow();

        //then
        assertEquals(newSurname, customerFromDb1.getSurname());
    }

    @Test
    void thatPhoneNumberCanBeModifiedCorrectly() {

        //given
        String customerEmail = someCustomer1().getEmail();
        String newPhoneNumber = "0988765";

        //when
        customerJpaRepository.changePhoneNumber(newPhoneNumber, customerEmail);
        CustomerEntity customerFromDb1 = customerJpaRepository.findByEmail(customerEmail).orElseThrow();

        //then
        assertEquals(newPhoneNumber, customerFromDb1.getPhoneNumber());
    }

    @Test
    void thatEmailCanBeModifiedCorrectly() {

        //given
        String customerEmail = someCustomer1().getEmail();
        String newEmail = "newMail@mail.com";

        //when
        customerJpaRepository.changeEmail(newEmail, customerEmail);
        CustomerEntity customerFromDb1 = customerJpaRepository.findByEmail(newEmail).orElseThrow();

        //then
        assertEquals(newEmail, customerFromDb1.getEmail());
    }

    @Test
    void thatDeliveryAddressCanBeModifiedCorrectly() {

        //given
        String customerEmail = someCustomer1().getEmail();
        DeliveryAddressEntity newDeliveryAddress = initializer.SAVED_DELIVERY_ADDRESSES.get(1);

        //when
        customerJpaRepository.changeDeliveryAddress(newDeliveryAddress, customerEmail);
        CustomerEntity customerFromDb1 = customerJpaRepository.findByEmail(customerEmail).orElseThrow();

        //then
        Assertions.assertThat(newDeliveryAddress)
                .usingRecursiveComparison()
                .ignoringFields("id", "customers")
                .isEqualTo(customerFromDb1.getDeliveryAddress());
    }

    @Test
    void isCustomerDeactivatedCorrectly() {

        // given
        String customerEmail = someCustomer1().getEmail();

        // when
        customerJpaRepository.deactivate(customerEmail);
        List<CustomerEntity> activeCustomers = customerJpaRepository.findActive();

        // then
        assertTrue(activeCustomers.size() >= 2);
    }

}