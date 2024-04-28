package pl.project.api.dto.mapper;

import pl.project.api.dto.OrderDetailsDTO;
import pl.project.domain.model.Order;

public interface OrderDetailsMapper {

    OrderDetailsDTO mapToDTO(Order domainObj);
}
