package pl.project.api.dto.mapper;

import pl.project.api.dto.OrderDTO;
import pl.project.domain.model.Order;

public interface OrderMapper {

    OrderDTO mapToDTO(Order order);
}
