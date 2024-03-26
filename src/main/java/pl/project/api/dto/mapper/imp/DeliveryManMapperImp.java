package pl.project.api.dto.mapper.imp;

import org.springframework.stereotype.Component;
import pl.project.api.dto.DeliveryManDTO;
import pl.project.api.dto.mapper.DeliveryManMapper;
import pl.project.domain.model.DeliveryMan;

@Component
public class DeliveryManMapperImp implements DeliveryManMapper {
    @Override
    public DeliveryMan mapFromDTO(DeliveryManDTO dto) {
        return DeliveryMan.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .phoneNumber(dto.getPhoneNumber())
                .build();
    }
}
