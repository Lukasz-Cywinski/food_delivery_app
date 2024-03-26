package pl.project.business.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.project.business.dao.CustomerDAO;
import pl.project.domain.exception.restaurant_owner.OwnerResourceCreateException;
import pl.project.domain.exception.restaurant_owner.OwnerResourceReadException;
import pl.project.domain.model.*;
import pl.project.infrastructure.security.ProjectUserDetailsService;
import pl.project.infrastructure.security.User;

@Service
@AllArgsConstructor
public class CustomerService {

    private final String CUSTOMER_CREATION_EXCEPTION = "Fail to crete customer: %s";
    private final String CUSTOMER_READ_EXCEPTION = "Fail to found customer by email: %s";

    private final CustomerDAO customerDAO;

    private final ProjectUserDetailsService projectUserDetailsService;

    // Customer Services
    @Transactional
    public Customer createCustomer(Customer customer) {
        User user = projectUserDetailsService.saveUserAndAssignRoles(customer.getUser());
        return customerDAO.createCustomer(customer.withUser(user))
                .orElseThrow(() -> new OwnerResourceCreateException(CUSTOMER_CREATION_EXCEPTION.formatted(customer)));
    }

    @Transactional
    public boolean modifyCustomerPersonalData(Customer updatedCustomerData, String customerEmail) {
        getCustomer(customerEmail);
        return (customerDAO.changeCustomerName(updatedCustomerData.getName(), customerEmail)
                + customerDAO.changeCustomerSurname(updatedCustomerData.getSurname(), customerEmail)
                + customerDAO.changeCustomerPhoneNumber(updatedCustomerData.getPhoneNumber(), customerEmail)
                + customerDAO.changeCustomerEmail(updatedCustomerData.getEmail(), customerEmail))
                > 0;
    }

    @Transactional
    public boolean deactivateCustomer(String customerEmail){
        return customerDAO.deactivateCustomer(customerEmail) == 1;
    }

    private Customer getCustomer(String customerEmail) {
        User user = projectUserDetailsService.getUserAndRoleByEmail(customerEmail);
        return customerDAO.getCustomerByEmail(customerEmail)
                .orElseThrow(() -> new OwnerResourceReadException(CUSTOMER_READ_EXCEPTION.formatted(customerEmail)))
                .withUser(user);
    }
}
