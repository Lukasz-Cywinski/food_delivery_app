package pl.project.infrastructure.database.repository.mapper;

import pl.project.domain.model.Dish;
import pl.project.infrastructure.database.entity.DishEntity;


public interface DishEntityMapper {

    Dish mapFromEntity(DishEntity entity);

    DishEntity mapToEntity(Dish domainObj);
}
