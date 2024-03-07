package pl.project.infrastructure.database.repository.mapper.imp;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.project.domain.model.RestaurantOwner;
import pl.project.infrastructure.database.entity.RestaurantOwnerEntity;
import pl.project.infrastructure.database.repository.mapper.RestaurantOwnerEntityMapper;
import pl.project.infrastructure.security.db.mapper.UserEntityMapper;

import java.util.Objects;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RestaurantOwnerEntityMapperImp implements RestaurantOwnerEntityMapper {

    private final UserEntityMapper userEntityMapper;

    public RestaurantOwner mapFromEntity(RestaurantOwnerEntity entity){
        return RestaurantOwner.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .isActive(entity.isActive())
                .user(Objects.isNull(entity.getUser()) ?
                        null : userEntityMapper.mapFromEntity(entity.getUser()))
                .build();
    }

    public RestaurantOwnerEntity mapToEntity(RestaurantOwner domainObj){
        return RestaurantOwnerEntity.builder()
                .id(domainObj.getId())
                .name(domainObj.getName())
                .surname(domainObj.getSurname())
                .phoneNumber(domainObj.getPhoneNumber())
                .email(domainObj.getEmail())
                .isActive(domainObj.isActive())
                .user(Objects.isNull(domainObj.getUser()) ?
                        null : userEntityMapper.mapToEntity(domainObj.getUser()))
                .build();
    }
}
