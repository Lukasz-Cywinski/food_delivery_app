package pl.project.infrastructure.database.repository.mapper.imp;


import org.springframework.stereotype.Component;
import pl.project.domain.model.DishCategory;
import pl.project.infrastructure.database.entity.DishCategoryEntity;
import pl.project.infrastructure.database.repository.mapper.DishCategoryEntityMapper;

@Component
public class DishCategoryEntityMapperImp implements DishCategoryEntityMapper {

    public DishCategory mapFromEntity(DishCategoryEntity entity){
        return null;
    }

    public DishCategoryEntity mapToEntity(DishCategory domainObj){
        return null;
    }
}
