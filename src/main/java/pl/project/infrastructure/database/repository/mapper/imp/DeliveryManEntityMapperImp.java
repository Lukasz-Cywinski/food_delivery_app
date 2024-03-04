package pl.project.infrastructure.database.repository.mapper.imp;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.project.domain.model.DeliveryMan;
import pl.project.infrastructure.database.entity.DeliveryManEntity;
import pl.project.infrastructure.database.repository.mapper.DeliveryManEntityMapper;
import pl.project.infrastructure.security.db.mapper.UserEntityMapper;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryManEntityMapperImp implements DeliveryManEntityMapper {

    private final UserEntityMapper userEntityMapper;

    public DeliveryMan mapFromEntity(DeliveryManEntity entity){
        return DeliveryMan.builder()
                .id(entity.getId())
                .personalCode(entity.getPersonalCode())
                .name(entity.getName())
                .surname(entity.getSurname())
                .phoneNumber(entity.getPhoneNumber())
                .isAvailable(entity.isAvailable())
                .isActive(entity.isActive())
                .user(userEntityMapper.mapFromEntity(entity.getUser()))
                .build();
    }

    public DeliveryManEntity mapToEntity(DeliveryMan domainObj){
        return DeliveryManEntity.builder()
                .id(domainObj.getId())
                .personalCode(domainObj.getPersonalCode())
                .name(domainObj.getName())
                .surname(domainObj.getSurname())
                .phoneNumber(domainObj.getPhoneNumber())
                .isAvailable(domainObj.isAvailable())
                .isActive(domainObj.isActive())
                .user(userEntityMapper.mapToEntity(domainObj.getUser()))
                .build();
    }
}
