package pl.project.api.dto.mapper;

import pl.project.api.dto.CustomerDTO;
import pl.project.domain.model.Customer;

public interface CustomerMapper {
    Customer mapFromDTO(CustomerDTO dto);
}
