package pl.project.infrastructure.database.repository.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.project.domain.model.Dish;
import pl.project.infrastructure.database.entity.DishEntity;


public interface DishMapper {

    Dish mapFromEntity(DishEntity entity);

    DishEntity mapToEntity(Dish domainObj);
}
