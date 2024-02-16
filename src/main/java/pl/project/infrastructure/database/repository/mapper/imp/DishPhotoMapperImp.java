package pl.project.infrastructure.database.repository.mapper.imp;


import org.springframework.stereotype.Component;
import pl.project.domain.model.DishPhoto;
import pl.project.infrastructure.database.entity.DishPhotoEntity;
import pl.project.infrastructure.database.repository.mapper.DishPhotoMapper;

@Component
public class DishPhotoMapperImp implements DishPhotoMapper {

    public DishPhoto mapFromEntity(DishPhotoEntity entity){
        return null;
    }

    public DishPhotoEntity mapToEntity(DishPhoto domainObj){
        return null;
    }
}
