package pl.project.infrastructure.database.repository.mapper.imp;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.project.domain.model.Dish;
import pl.project.infrastructure.database.entity.DishEntity;
import pl.project.infrastructure.database.repository.mapper.DishCategoryEntityMapper;
import pl.project.infrastructure.database.repository.mapper.DishEntityMapper;
import pl.project.infrastructure.database.repository.mapper.DishPhotoEntityMapper;
import pl.project.infrastructure.database.repository.mapper.RestaurantEntityMapper;

@Component
@AllArgsConstructor
public class DishEntityMapperImp implements DishEntityMapper {

    private final RestaurantEntityMapper restaurantEntityMapper;
    private final DishPhotoEntityMapper dishPhotoEntityMapper;
    private final DishCategoryEntityMapper dishCategoryEntityMapper;

    public Dish mapFromEntity(DishEntity entity){
        return Dish.builder()
                .id(entity.getId())
                .dishCode(entity.getDishCode())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .averagePreparationTimeMin(entity.getAveragePreparationTimeMin())
                .restaurant(restaurantEntityMapper.mapFromEntity(entity.getRestaurant()))
                .dishPhoto(dishPhotoEntityMapper.mapFromEntity(entity.getDishPhoto()))
                .dishCategory(dishCategoryEntityMapper.mapFromEntity(entity.getDishCategory()))
                .isActive(entity.isActive())
                .build();
    }

    public DishEntity mapToEntity(Dish domainObj){
        return DishEntity.builder()
                .id(domainObj.getId())
                .dishCode(domainObj.getDishCode())
                .name(domainObj.getName())
                .description(domainObj.getDescription())
                .price(domainObj.getPrice())
                .averagePreparationTimeMin(domainObj.getAveragePreparationTimeMin())
                .restaurant(restaurantEntityMapper.mapToEntity(domainObj.getRestaurant()))
                .dishPhoto(dishPhotoEntityMapper.mapToEntity(domainObj.getDishPhoto()))
                .dishCategory(dishCategoryEntityMapper.mapToEntity(domainObj.getDishCategory()))
                .isActive(domainObj.isActive())
                .build();
    }
}
