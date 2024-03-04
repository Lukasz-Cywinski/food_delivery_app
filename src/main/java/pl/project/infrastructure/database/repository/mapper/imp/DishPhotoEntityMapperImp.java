package pl.project.infrastructure.database.repository.mapper.imp;


import org.springframework.stereotype.Component;
import pl.project.domain.model.DishPhoto;
import pl.project.infrastructure.database.entity.DishPhotoEntity;
import pl.project.infrastructure.database.repository.mapper.DishPhotoEntityMapper;

@Component
public class DishPhotoEntityMapperImp implements DishPhotoEntityMapper {

    public DishPhoto mapFromEntity(DishPhotoEntity entity){
        return DishPhoto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .url(entity.getUrl())
                .build();
    }

    public DishPhotoEntity mapToEntity(DishPhoto domainObj){
        return DishPhotoEntity.builder()
                .id(domainObj.getId())
                .name(domainObj.getName())
                .url(domainObj.getUrl())
                .build();
    }
}
