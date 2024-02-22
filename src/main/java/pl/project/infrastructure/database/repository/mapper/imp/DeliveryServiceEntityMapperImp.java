package pl.project.infrastructure.database.repository.mapper.imp;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.project.domain.model.DeliveryService;
import pl.project.infrastructure.database.entity.DeliveryServiceEntity;
import pl.project.infrastructure.database.repository.mapper.DeliveryManEntityMapper;
import pl.project.infrastructure.database.repository.mapper.DeliveryServiceEntityMapper;

@Component
@AllArgsConstructor
public class DeliveryServiceEntityMapperImp implements DeliveryServiceEntityMapper {

    private final DeliveryManEntityMapper deliveryManEntityMapper;

    public DeliveryService mapFromEntity(DeliveryServiceEntity entity){
        return DeliveryService.builder()
                .deliveryServiceCode(entity.getDeliveryServiceCode())
                .receivedDateTime(entity.getReceivedDateTime())
                .completedDateTime(entity.getCompletedDateTime())
                .deliveryMan(deliveryManEntityMapper.mapFromEntity(entity.getDeliveryMan()))
                .build();
    }

    public DeliveryServiceEntity mapToEntity(DeliveryService domainObj){
        return DeliveryServiceEntity.builder()
                .deliveryServiceCode(domainObj.getDeliveryServiceCode())
                .receivedDateTime(domainObj.getReceivedDateTime())
                .completedDateTime(domainObj.getCompletedDateTime())
                .deliveryMan(deliveryManEntityMapper.mapToEntity(domainObj.getDeliveryMan()))
                .build();
    }
}
