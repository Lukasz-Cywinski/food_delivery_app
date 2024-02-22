package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.infrastructure.database.entity.CustomerEntity;
import pl.project.infrastructure.database.entity.DeliveryAddressEntity;
import pl.project.integration.configuration.MyJpaConfiguration;
import pl.project.infrastructure.database.repository.jpa.CustomerJpaRepository;
import pl.project.infrastructure.database.repository.jpa.DeliveryAddressJpaRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.project.util.db.CustomerInstance.*;
import static pl.project.util.db.DeliveryAddressInstance.someDeliveryAddress2;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class CustomerRepositoryTest extends MyJpaConfiguration {

    private CustomerJpaRepository customerJpaRepository;
    private DeliveryAddressJpaRepository deliveryAddressJpaRepository;

    @Test
    void thatCustomerCanBeSavedCorrectly() {

        // given
        CustomerEntity customer1 = someCustomer1();
        CustomerEntity customer2 = someCustomer2();
        CustomerEntity customer3 = someCustomer3();

        //when

        customerJpaRepository.save(customer1);
        customerJpaRepository.save(customer2);
        customerJpaRepository.save(customer3);

        List<CustomerEntity> activeCustomers = customerJpaRepository.findActive();

        //then
        assertThat(activeCustomers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "deliveryAddress")
                .contains(customer1, customer2, customer3);

        assertEquals(3, activeCustomers.size());
    }

    @Test
    void thatNameCanBeModifiedCorrectly() {

        //given
        CustomerEntity customer1 = someCustomer1();
        String newName = "new Name";

        //when
        customerJpaRepository.save(customer1);
        customerJpaRepository.changeName(newName, customer1.getEmail());
        CustomerEntity customerFromDb1 = customerJpaRepository.findByEmail(customer1.getEmail()).orElseThrow();

        //then
        assertEquals(newName, customerFromDb1.getName());
    }

    @Test
    void thatSurnameCanBeModifiedCorrectly() {

        //given
        CustomerEntity customer1 = someCustomer1();
        String newSurname = "new Surname";

        //when
        customerJpaRepository.save(customer1);
        customerJpaRepository.changeSurname(newSurname, customer1.getEmail());
        CustomerEntity customerFromDb1 = customerJpaRepository.findByEmail(customer1.getEmail()).orElseThrow();

        //then
        assertEquals(newSurname, customerFromDb1.getSurname());
    }

    @Test
    void thatPhoneNumberCanBeModifiedCorrectly() {

        //given
        CustomerEntity customer1 = someCustomer1();
        String newPhoneNumber = "0988765";

        //when
        customerJpaRepository.save(customer1);
        customerJpaRepository.changePhoneNumber(newPhoneNumber, customer1.getEmail());
        CustomerEntity customerFromDb1 = customerJpaRepository.findByEmail(customer1.getEmail()).orElseThrow();

        //then
        assertEquals(newPhoneNumber, customerFromDb1.getPhoneNumber());
    }

    @Test
    void thatEmailCanBeModifiedCorrectly() {

        //given
        CustomerEntity customer1 = someCustomer1();
        String newEmail = "newMail@mail.com";

        //when
        customerJpaRepository.save(customer1);
        customerJpaRepository.changeEmail(newEmail, customer1.getEmail());
        CustomerEntity customerFromDb1 = customerJpaRepository.findByEmail(newEmail).orElseThrow();

        //then
        assertEquals(newEmail, customerFromDb1.getEmail());
    }

    @Test
    void thatDeliveryAddressCanBeModifiedCorrectly() {

        //given
        CustomerEntity customer1 = someCustomer1();
        DeliveryAddressEntity newDeliveryAddress = someDeliveryAddress2();

        //when
        customerJpaRepository.save(customer1);
        deliveryAddressJpaRepository.save(newDeliveryAddress);
        customerJpaRepository.changeDeliveryAddress(newDeliveryAddress, customer1.getEmail());
        CustomerEntity customerFromDb1 = customerJpaRepository.findByEmail(customer1.getEmail()).orElseThrow();

        //then
        Assertions.assertThat(newDeliveryAddress)
                .usingRecursiveComparison()
                .ignoringFields("id", "customers")
                .isEqualTo(customerFromDb1.getDeliveryAddress());
    }

    @Test
    void isCustomerDeactivatedCorrectly() {

        // given
        CustomerEntity customer1 = someCustomer1();
        CustomerEntity customer2 = someCustomer2();
        CustomerEntity customer3 = someCustomer3();

        // when
        customerJpaRepository.save(customer1);
        customerJpaRepository.save(customer2);
        customerJpaRepository.save(customer3);

        customerJpaRepository.deactivate(someCustomer3().getEmail());
        List<CustomerEntity> activeCustomers = customerJpaRepository.findActive();

        // then
        assertEquals(2, activeCustomers.size());
    }

}