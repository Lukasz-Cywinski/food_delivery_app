package pl.project.infrastructure.database.repository.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.project.domain.model.DishPhoto;
import pl.project.infrastructure.database.entity.DishPhotoEntity;


public interface DishPhotoMapper {
    DishPhoto mapFromEntity(DishPhotoEntity entity);

    DishPhotoEntity mapToEntity(DishPhoto domainObj);
}
