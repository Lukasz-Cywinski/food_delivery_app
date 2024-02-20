package pl.project.infrastructure.database.repository.mapper;

import pl.project.domain.model.DishComposition;
import pl.project.infrastructure.database.entity.DishCompositionEntity;


public interface DishCompositionEntityMapper {

    DishComposition mapFromEntity(DishCompositionEntity entity);

    DishCompositionEntity mapToEntity(DishComposition domainObj);
}
