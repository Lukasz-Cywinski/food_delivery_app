package pl.project.infrastructure.database.repository.mapper.imp;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.project.domain.model.Restaurant;
import pl.project.infrastructure.database.entity.RestaurantEntity;
import pl.project.infrastructure.database.repository.mapper.RestaurantEntityMapper;
import pl.project.infrastructure.database.repository.mapper.RestaurantOwnerEntityMapper;

@Component
@AllArgsConstructor
public class RestaurantEntityMapperImp implements RestaurantEntityMapper {

    private final RestaurantOwnerEntityMapper restaurantOwnerEntityMapper;

    public Restaurant mapFromEntity(RestaurantEntity entity){
        return Restaurant.builder()
                .restaurantCode(entity.getRestaurantCode())
                .name(entity.getName())
                .added(entity.getAdded())
                .restaurantOwner(restaurantOwnerEntityMapper.mapFromEntity(entity.getRestaurantOwner()))
                .isActive(entity.isActive())
                .build();
    }

    public RestaurantEntity mapToEntity(Restaurant domainObj){
        return RestaurantEntity.builder()
                .restaurantCode(domainObj.getRestaurantCode())
                .name(domainObj.getName())
                .added(domainObj.getAdded())
                .restaurantOwner(restaurantOwnerEntityMapper.mapToEntity(domainObj.getRestaurantOwner()))
                .isActive(domainObj.isActive())
                .build();
    }
}
