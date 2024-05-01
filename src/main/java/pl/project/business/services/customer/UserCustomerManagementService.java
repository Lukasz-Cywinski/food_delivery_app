package pl.project.business.services.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.project.business.dao.CustomerDAO;
import pl.project.domain.exception.customer.CustomerResourceReadException;
import pl.project.domain.exception.customer.CustomerResourceUpdateException;
import pl.project.domain.model.Customer;
import pl.project.infrastructure.security.ProjectUserDetailsService;

import java.util.Objects;

import static pl.project.domain.exception.ExceptionMessages.RESOURCE_MODIFICATION_EXCEPTION;
import static pl.project.domain.exception.ExceptionMessages.RESOURCE_READ_EXCEPTION;

@Service
@AllArgsConstructor
public class UserCustomerManagementService {

    private final CustomerDAO customerDAO;
    private final ProjectUserDetailsService projectUserDetailsService;

    @Transactional
    public void modifyCustomerPersonalData(Customer updatedCustomer, String customerEmail) {
        try {
            String newEmail = updatedCustomer.getEmail();
            String newPhoneNumber = updatedCustomer.getPhoneNumber();

            if (Objects.nonNull(newEmail) && !newEmail.isEmpty()) {
                customerDAO.changeCustomerEmail(newEmail, customerEmail);
                projectUserDetailsService.changeEmail(newEmail, customerEmail);

            }
            if (Objects.nonNull(newPhoneNumber) && !newPhoneNumber.isEmpty()) {
                customerDAO.changeCustomerPhoneNumber(newPhoneNumber, customerEmail);
            }
        }
        catch (Exception e){
            throw new CustomerResourceUpdateException(RESOURCE_MODIFICATION_EXCEPTION.formatted(Customer.class.getSimpleName(), customerEmail), e);
        }
    }

    @Transactional
    private Customer getCustomer(String customerEmail) {
        try {
            return customerDAO.findCustomerByEmail(customerEmail)
                    .orElseThrow(RuntimeException::new);
        }
        catch (Exception e){
            throw new CustomerResourceReadException(RESOURCE_READ_EXCEPTION.formatted(Customer.class.getSimpleName(), customerEmail), e);
        }
    }
}
