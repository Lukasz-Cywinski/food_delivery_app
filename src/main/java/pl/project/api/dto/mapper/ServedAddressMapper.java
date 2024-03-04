package pl.project.api.dto.mapper;

import pl.project.api.dto.ServedAddressDTO;
import pl.project.domain.model.ServedAddress;

public interface ServedAddressMapper {
    ServedAddressDTO mapToDTO(ServedAddress domainObj);
    ServedAddress mapFromDTO(ServedAddressDTO dto);
}
