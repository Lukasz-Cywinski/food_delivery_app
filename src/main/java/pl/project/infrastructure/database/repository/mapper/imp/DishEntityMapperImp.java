package pl.project.infrastructure.database.repository.mapper.imp;


import org.springframework.stereotype.Component;
import pl.project.domain.model.Dish;
import pl.project.infrastructure.database.entity.DishEntity;
import pl.project.infrastructure.database.repository.mapper.DishEntityMapper;

@Component
public class DishEntityMapperImp implements DishEntityMapper {

    public Dish mapFromEntity(DishEntity entity){
        return null;
    }

    public DishEntity mapToEntity(Dish domainObj){
        return null;
    }
}
