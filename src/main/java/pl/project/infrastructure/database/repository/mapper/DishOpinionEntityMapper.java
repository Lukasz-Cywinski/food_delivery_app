package pl.project.infrastructure.database.repository.mapper;

import pl.project.domain.model.DishOpinion;
import pl.project.infrastructure.database.entity.DishOpinionEntity;


public interface DishOpinionEntityMapper {

    DishOpinion mapFromEntity(DishOpinionEntity entity);

    DishOpinionEntity mapToEntity(DishOpinion domainObj);
}
