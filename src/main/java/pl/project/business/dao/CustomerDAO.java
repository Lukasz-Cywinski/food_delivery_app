package pl.project.business.dao;

import pl.project.domain.model.Customer;
import pl.project.domain.model.DeliveryAddress;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {

    Optional<Customer> addCustomer(Customer customer);

    Optional<Customer> getOwnerByEmail(String email);

    List<Customer> getActiveCustomers();

    Integer changeCustomerName(String newName, String email);

    Integer changeCustomerSurname(String newSurname, String email);

    Integer changeCustomerPhoneNumber(String newPhoneNumber, String email);

    Integer changeCustomerEmail(String newEmail, String oldEmail);

    Integer changeCustomerDeliveryAddress(DeliveryAddress newDeliveryAddress, String oldEmail);

    Integer deactivateCustomer(String personalCode);

}
