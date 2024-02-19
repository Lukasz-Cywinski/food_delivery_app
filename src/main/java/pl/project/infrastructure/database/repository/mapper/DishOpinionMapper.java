package pl.project.infrastructure.database.repository.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.project.domain.model.DishOpinion;
import pl.project.infrastructure.database.entity.DishOpinionEntity;


public interface DishOpinionMapper {

    DishOpinion mapFromEntity(DishOpinionEntity entity);

    DishOpinionEntity mapToEntity(DishOpinion domainObj);
}
