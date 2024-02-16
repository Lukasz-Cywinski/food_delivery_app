package pl.project.infrastructure.database.repository.mapper.imp;


import org.springframework.stereotype.Component;
import pl.project.domain.model.DeliveryService;
import pl.project.infrastructure.database.entity.DeliveryServiceEntity;
import pl.project.infrastructure.database.repository.mapper.DeliveryServiceMapper;

@Component
public class DeliveryServiceMapperImp implements DeliveryServiceMapper {

    public DeliveryService mapFromEntity(DeliveryServiceEntity entity){
        return null;
    }

    public DeliveryServiceEntity mapToEntity(DeliveryService domainObj){
        return null;
    }
}
