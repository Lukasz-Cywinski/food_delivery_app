package pl.project.infrastructure.database.repository.mapper;

import pl.project.domain.model.RestaurantOwner;
import pl.project.infrastructure.database.entity.RestaurantOwnerEntity;


public interface RestaurantOwnerEntityMapper {

    RestaurantOwner mapFromEntity(RestaurantOwnerEntity entity);

    RestaurantOwnerEntity mapToEntity(RestaurantOwner domainObj);
}
