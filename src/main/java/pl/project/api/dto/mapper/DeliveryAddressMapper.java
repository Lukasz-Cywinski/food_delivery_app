package pl.project.api.dto.mapper;

import pl.project.api.dto.DeliveryAddressDTO;
import pl.project.domain.model.DeliveryAddress;

public interface DeliveryAddressMapper {

    DeliveryAddress mapFromDTO(DeliveryAddressDTO dto);
}
