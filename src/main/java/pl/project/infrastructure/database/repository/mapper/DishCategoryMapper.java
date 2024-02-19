package pl.project.infrastructure.database.repository.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.project.domain.model.DishCategory;
import pl.project.infrastructure.database.entity.DishCategoryEntity;


public interface DishCategoryMapper {

    DishCategory mapFromEntity(DishCategoryEntity entity);

    DishCategoryEntity mapToEntity(DishCategory domainObj);
}
