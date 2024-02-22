package pl.project.infrastructure.database.repository.mapper.imp;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.project.domain.model.DeliveryMan;
import pl.project.infrastructure.database.entity.DeliveryManEntity;
import pl.project.infrastructure.database.repository.mapper.DeliveryManEntityMapper;

@Component
public class DeliveryManEntityMapperImp implements DeliveryManEntityMapper {

    public DeliveryMan mapFromEntity(DeliveryManEntity entity){
        return DeliveryMan.builder()
                .personalCode(entity.getPersonalCode())
                .name(entity.getName())
                .surname(entity.getSurname())
                .phoneNumber(entity.getPhoneNumber())
                .isAvailable(entity.isAvailable())
                .isActive(entity.isActive())
                .build();
    }

    public DeliveryManEntity mapToEntity(DeliveryMan domainObj){
        return DeliveryManEntity.builder()
                .personalCode(domainObj.getPersonalCode())
                .name(domainObj.getName())
                .surname(domainObj.getSurname())
                .phoneNumber(domainObj.getPhoneNumber())
                .isAvailable(domainObj.isAvailable())
                .isActive(domainObj.isActive())
                .build();
    }
}
