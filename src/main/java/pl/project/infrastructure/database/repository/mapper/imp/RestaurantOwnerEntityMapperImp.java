package pl.project.infrastructure.database.repository.mapper.imp;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.project.domain.model.RestaurantOwner;
import pl.project.infrastructure.database.entity.RestaurantOwnerEntity;
import pl.project.infrastructure.database.repository.mapper.RestaurantOwnerEntityMapper;
import pl.project.infrastructure.security.db.mapper.UserEntityMapper;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RestaurantOwnerEntityMapperImp implements RestaurantOwnerEntityMapper {

    private final UserEntityMapper userEntityMapper;

    public RestaurantOwner mapFromEntity(RestaurantOwnerEntity entity){
        return RestaurantOwner.builder()
                .name(entity.getName())
                .surname(entity.getSurname())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .isActive(entity.isActive())
                .user(userEntityMapper.mapFromEntity(entity.getUser()))
                .build();
    }

    public RestaurantOwnerEntity mapToEntity(RestaurantOwner domainObj){
        return RestaurantOwnerEntity.builder()
                .name(domainObj.getName())
                .surname(domainObj.getSurname())
                .phoneNumber(domainObj.getPhoneNumber())
                .email(domainObj.getEmail())
                .isActive(domainObj.isActive())
                .user(userEntityMapper.mapToEntity(domainObj.getUser()))
                .build();
    }
}