package pl.project.api.dto.mapper.imp;

import org.springframework.stereotype.Component;
import pl.project.api.dto.CustomerDTO;
import pl.project.api.dto.mapper.CustomerMapper;
import pl.project.domain.model.Customer;

@Component
public class CustomerMapperImp implements CustomerMapper {

    @Override
    public Customer mapFromDTO(CustomerDTO customerDTO) {
        return Customer.builder()
                .name(customerDTO.getName())
                .surname(customerDTO.getSurname())
                .phoneNumber(customerDTO.getPhoneNumber())
                .email(customerDTO.getEmail())
                .build();
    }
}
