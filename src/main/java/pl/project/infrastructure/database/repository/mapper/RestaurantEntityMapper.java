package pl.project.infrastructure.database.repository.mapper;

import pl.project.domain.model.Restaurant;
import pl.project.infrastructure.database.entity.RestaurantEntity;


public interface RestaurantEntityMapper {

    Restaurant mapFromEntity(RestaurantEntity entity);

    RestaurantEntity mapToEntity(Restaurant domainObj);
}
