package pl.project.infrastructure.database.repository.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.project.domain.model.Restaurant;
import pl.project.infrastructure.database.entity.RestaurantEntity;


public interface RestaurantMapper {

    Restaurant mapFromEntity(RestaurantEntity entity);

    RestaurantEntity mapToEntity(Restaurant domainObj);
}
