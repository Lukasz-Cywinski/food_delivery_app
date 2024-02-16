package pl.project.infrastructure.database.repository.mapper.imp;


import org.springframework.stereotype.Component;
import pl.project.domain.model.DishComposition;
import pl.project.infrastructure.database.entity.DishCompositionEntity;
import pl.project.infrastructure.database.repository.mapper.DishCompositionMapper;

@Component
public class DishCompositionMapperImp implements DishCompositionMapper {

    public DishComposition mapFromEntity(DishCompositionEntity entity){
        return null;
    }

    public DishCompositionEntity mapToEntity(DishComposition domainObj){
        return null;
    }
}
