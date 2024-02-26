package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.CustomerDAO;
import pl.project.domain.model.Customer;
import pl.project.domain.model.DeliveryAddress;
import pl.project.infrastructure.database.repository.jpa.CustomerJpaRepository;
import pl.project.infrastructure.database.repository.mapper.CustomerEntityMapper;
import pl.project.infrastructure.database.repository.mapper.DeliveryAddressEntityMapper;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CustomerRepository implements CustomerDAO {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerEntityMapper customerEntityMapper;
    private final DeliveryAddressEntityMapper deliveryAddressEntityMapper;

    @Override
    public Optional<Customer> addCustomer(Customer customer) {
        return Optional.ofNullable(
                customerEntityMapper.mapFromEntity(
                        customerJpaRepository.save(
                                customerEntityMapper.mapToEntity(customer)
                        )
                )
        );
    }

    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerJpaRepository.findByEmail(email)
                .map(customerEntityMapper::mapFromEntity);
    }

    @Override
    public List<Customer> getActiveCustomers() {
        return customerJpaRepository.findActive().stream()
                .map(customerEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Integer changeCustomerName(String newName, String email) {
        return customerJpaRepository.changeName(newName, email);
    }

    @Override
    public Integer changeCustomerSurname(String newSurname, String email) {
        return customerJpaRepository.changeSurname(newSurname, email);
    }

    @Override
    public Integer changeCustomerPhoneNumber(String newPhoneNumber, String email) {
        return customerJpaRepository.changePhoneNumber(newPhoneNumber, email);
    }

    @Override
    public Integer changeCustomerEmail(String newEmail, String oldEmail) {
        return customerJpaRepository.changeEmail(newEmail, oldEmail);
    }

    @Override
    public Integer changeCustomerDeliveryAddress(DeliveryAddress newDeliveryAddress, String oldEmail) {
        return customerJpaRepository.changeDeliveryAddress(
                deliveryAddressEntityMapper.mapToEntity(newDeliveryAddress),
                oldEmail
        );
    }

    @Override
    public Integer deactivateCustomer(String personalCode) {
        return customerJpaRepository.deactivate(personalCode);
    }
}
