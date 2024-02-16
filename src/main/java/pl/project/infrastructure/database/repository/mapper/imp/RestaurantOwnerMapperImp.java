package pl.project.infrastructure.database.repository.mapper.imp;


import org.springframework.stereotype.Component;
import pl.project.domain.model.RestaurantOwner;
import pl.project.infrastructure.database.entity.RestaurantOwnerEntity;
import pl.project.infrastructure.database.repository.mapper.RestaurantOwnerMapper;

@Component
public class RestaurantOwnerMapperImp implements RestaurantOwnerMapper {

    public RestaurantOwner mapFromEntity(RestaurantOwnerEntity entity){
        return null;
    }

    public RestaurantOwnerEntity mapToEntity(RestaurantOwner domainObj){
        return null;
    }
}
