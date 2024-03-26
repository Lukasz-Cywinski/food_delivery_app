package pl.project.api.dto.mapper;

import pl.project.api.dto.DeliveryManDTO;
import pl.project.domain.model.DeliveryMan;

public interface DeliveryManMapper {

    DeliveryMan mapFromDTO (DeliveryManDTO dto);
}
