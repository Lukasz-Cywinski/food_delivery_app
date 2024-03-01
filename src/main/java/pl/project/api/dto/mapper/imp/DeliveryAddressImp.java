package pl.project.api.dto.mapper.imp;

import org.springframework.stereotype.Component;
import pl.project.api.dto.DeliveryAddressDTO;
import pl.project.api.dto.mapper.DeliveryAddressMapper;
import pl.project.domain.model.DeliveryAddress;

@Component
public class DeliveryAddressImp implements DeliveryAddressMapper {
    @Override
    public DeliveryAddress mapFromDTO(DeliveryAddressDTO dto) {
        return DeliveryAddress.builder()
                .city(dto.getCity())
                .street(dto.getStreet())
                .postalCode(dto.getPostalCode())
                .build();
    }
}
