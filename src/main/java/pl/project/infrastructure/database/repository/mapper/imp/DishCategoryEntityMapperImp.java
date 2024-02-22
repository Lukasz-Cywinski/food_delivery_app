package pl.project.infrastructure.database.repository.mapper.imp;


import org.springframework.stereotype.Component;
import pl.project.domain.model.DishCategory;
import pl.project.infrastructure.database.entity.DishCategoryEntity;
import pl.project.infrastructure.database.repository.mapper.DishCategoryEntityMapper;

@Component
public class DishCategoryEntityMapperImp implements DishCategoryEntityMapper {

    public DishCategory mapFromEntity(DishCategoryEntity entity){
        return DishCategory.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public DishCategoryEntity mapToEntity(DishCategory domainObj){
        return DishCategoryEntity.builder()
                .name(domainObj.getName())
                .description(domainObj.getDescription())
                .build();
    }
}
