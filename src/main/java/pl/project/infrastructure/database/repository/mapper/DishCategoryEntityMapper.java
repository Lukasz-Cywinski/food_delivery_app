package pl.project.infrastructure.database.repository.mapper;

import pl.project.domain.model.DishCategory;
import pl.project.infrastructure.database.entity.DishCategoryEntity;


public interface DishCategoryEntityMapper {

    DishCategory mapFromEntity(DishCategoryEntity entity);

    DishCategoryEntity mapToEntity(DishCategory domainObj);
}
