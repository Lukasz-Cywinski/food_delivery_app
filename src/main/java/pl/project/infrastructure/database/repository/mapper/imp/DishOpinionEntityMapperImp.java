package pl.project.infrastructure.database.repository.mapper.imp;


import org.springframework.stereotype.Component;
import pl.project.domain.model.DishOpinion;
import pl.project.infrastructure.database.entity.DishOpinionEntity;
import pl.project.infrastructure.database.repository.mapper.DishOpinionEntityMapper;

@Component
public class DishOpinionEntityMapperImp implements DishOpinionEntityMapper {

    public DishOpinion mapFromEntity(DishOpinionEntity entity){
        return null;
    }

    public DishOpinionEntity mapToEntity(DishOpinion domainObj){
        return null;
    }
}
