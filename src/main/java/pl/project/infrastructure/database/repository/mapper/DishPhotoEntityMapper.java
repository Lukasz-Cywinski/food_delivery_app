package pl.project.infrastructure.database.repository.mapper;

import pl.project.domain.model.DishPhoto;
import pl.project.infrastructure.database.entity.DishPhotoEntity;


public interface DishPhotoEntityMapper {
    DishPhoto mapFromEntity(DishPhotoEntity entity);

    DishPhotoEntity mapToEntity(DishPhoto domainObj);
}
