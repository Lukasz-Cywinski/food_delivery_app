package pl.project.infrastructure.database.repository.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.project.domain.model.DishComposition;
import pl.project.infrastructure.database.entity.DishCompositionEntity;


public interface DishCompositionMapper {

    DishComposition mapFromEntity(DishCompositionEntity entity);

    DishCompositionEntity mapToEntity(DishComposition domainObj);
}
