package pl.project.infrastructure.database.repository.mapper.imp;


import org.springframework.stereotype.Component;
import pl.project.domain.model.RestaurantOwner;
import pl.project.infrastructure.database.entity.RestaurantOwnerEntity;
import pl.project.infrastructure.database.repository.mapper.RestaurantOwnerEntityMapper;

@Component
public class RestaurantOwnerEntityMapperImp implements RestaurantOwnerEntityMapper {

    public RestaurantOwner mapFromEntity(RestaurantOwnerEntity entity){
        return RestaurantOwner.builder()
                .name(entity.getName())
                .surname(entity.getSurname())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .isActive(entity.isActive())
                .build();
    }

    public RestaurantOwnerEntity mapToEntity(RestaurantOwner domainObj){
        return RestaurantOwnerEntity.builder()
                .name(domainObj.getName())
                .surname(domainObj.getSurname())
                .phoneNumber(domainObj.getPhoneNumber())
                .email(domainObj.getEmail())
                .isActive(domainObj.isActive())
                .build();
    }
}
