package pl.project.infrastructure.database.repository.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.project.domain.model.RestaurantOwner;
import pl.project.infrastructure.database.entity.RestaurantOwnerEntity;


public interface RestaurantOwnerMapper {

    RestaurantOwner mapFromEntity(RestaurantOwnerEntity entity);

    RestaurantOwnerEntity mapToEntity(RestaurantOwner domainObj);
}
