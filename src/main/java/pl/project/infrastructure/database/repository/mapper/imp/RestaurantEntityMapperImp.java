package pl.project.infrastructure.database.repository.mapper.imp;


import org.springframework.stereotype.Component;
import pl.project.domain.model.Restaurant;
import pl.project.infrastructure.database.entity.RestaurantEntity;
import pl.project.infrastructure.database.repository.mapper.RestaurantEntityMapper;

@Component
public class RestaurantEntityMapperImp implements RestaurantEntityMapper {

    public Restaurant mapFromEntity(RestaurantEntity entity){
        return null;
    }

    public RestaurantEntity mapToEntity(Restaurant domainObj){
        return null;
    }
}
