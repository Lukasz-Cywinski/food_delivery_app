package pl.project.infrastructure.database.repository.mapper.imp;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.project.domain.model.Restaurant;
import pl.project.infrastructure.database.entity.RestaurantEntity;
import pl.project.infrastructure.database.repository.mapper.RestaurantEntityMapper;
import pl.project.infrastructure.database.repository.mapper.RestaurantOwnerEntityMapper;

import java.util.Objects;

@Component
@AllArgsConstructor
public class RestaurantEntityMapperImp implements RestaurantEntityMapper {

    private final RestaurantOwnerEntityMapper restaurantOwnerEntityMapper;

    public Restaurant mapFromEntity(RestaurantEntity entity){
        return Restaurant.builder()
                .id(entity.getId())
                .restaurantCode(entity.getRestaurantCode())
                .name(entity.getName())
                .added(entity.getAdded())
                .restaurantOwner(Objects.isNull(entity.getRestaurantOwner()) ?
                        null : restaurantOwnerEntityMapper.mapFromEntity(entity.getRestaurantOwner()))
                .isActive(entity.isActive())
                .build();
    }

    public RestaurantEntity mapToEntity(Restaurant domainObj){
        return RestaurantEntity.builder()
                .id(domainObj.getId())
                .restaurantCode(domainObj.getRestaurantCode())
                .name(domainObj.getName())
                .added(domainObj.getAdded())
                .restaurantOwner(Objects.isNull(domainObj.getRestaurantOwner()) ?
                        null : restaurantOwnerEntityMapper.mapToEntity(domainObj.getRestaurantOwner()))
                .isActive(domainObj.isActive())
                .build();
    }
}
