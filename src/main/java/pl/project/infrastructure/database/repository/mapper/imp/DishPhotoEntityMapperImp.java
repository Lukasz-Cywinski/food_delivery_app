package pl.project.infrastructure.database.repository.mapper.imp;


import org.springframework.stereotype.Component;
import pl.project.domain.model.DishPhoto;
import pl.project.infrastructure.database.entity.DishPhotoEntity;
import pl.project.infrastructure.database.repository.mapper.DishPhotoEntityMapper;

@Component
public class DishPhotoEntityMapperImp implements DishPhotoEntityMapper {

    public DishPhoto mapFromEntity(DishPhotoEntity entity){
        return null;
    }

    public DishPhotoEntity mapToEntity(DishPhoto domainObj){
        return null;
    }
}
