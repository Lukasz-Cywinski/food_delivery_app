package pl.project.infrastructure.database.repository.mapper.imp;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.project.domain.model.DeliveryService;
import pl.project.infrastructure.database.entity.DeliveryServiceEntity;
import pl.project.infrastructure.database.repository.mapper.DeliveryManEntityMapper;
import pl.project.infrastructure.database.repository.mapper.DeliveryServiceEntityMapper;

import java.util.Objects;

@Component
@AllArgsConstructor
public class DeliveryServiceEntityMapperImp implements DeliveryServiceEntityMapper {

    private final DeliveryManEntityMapper deliveryManEntityMapper;

    public DeliveryService mapFromEntity(DeliveryServiceEntity entity){
        return DeliveryService.builder()
                .id(entity.getId())
                .deliveryServiceCode(entity.getDeliveryServiceCode())
                .receivedDateTime(entity.getReceivedDateTime())
                .completedDateTime(entity.getCompletedDateTime())
                .deliveryMan(Objects.isNull(entity.getDeliveryMan()) ?
                        null : deliveryManEntityMapper.mapFromEntity(entity.getDeliveryMan()))
                .build();
    }

    public DeliveryServiceEntity mapToEntity(DeliveryService domainObj){
        return DeliveryServiceEntity.builder()
                .id(domainObj.getId())
                .deliveryServiceCode(domainObj.getDeliveryServiceCode())
                .receivedDateTime(domainObj.getReceivedDateTime())
                .completedDateTime(domainObj.getCompletedDateTime())
                .deliveryMan(Objects.isNull(domainObj.getDeliveryMan()) ?
                        null : deliveryManEntityMapper.mapToEntity(domainObj.getDeliveryMan()))
                .build();
    }
}
