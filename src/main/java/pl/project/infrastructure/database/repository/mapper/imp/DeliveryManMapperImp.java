package pl.project.infrastructure.database.repository.mapper.imp;


import org.springframework.stereotype.Component;
import pl.project.domain.model.DeliveryMan;
import pl.project.infrastructure.database.entity.DeliveryManEntity;
import pl.project.infrastructure.database.repository.mapper.DeliveryManMapper;

@Component
public class DeliveryManMapperImp implements DeliveryManMapper {

    public DeliveryMan mapFromEntity(DeliveryManEntity entity){
        return null;
    }

    public DeliveryManEntity mapToEntity(DeliveryMan domainObj){
        return null;
    }
}
