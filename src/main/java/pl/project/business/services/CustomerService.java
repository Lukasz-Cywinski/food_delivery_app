package pl.project.business.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.project.business.dao.CustomerDAO;
import pl.project.domain.model.Customer;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;

    @Transactional
    public Customer addCustomer(Customer customer){
        return customerDAO.addCustomer(customer).get();
    }

}
