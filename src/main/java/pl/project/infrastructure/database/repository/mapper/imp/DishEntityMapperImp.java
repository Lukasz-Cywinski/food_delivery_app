package pl.project.infrastructure.database.repository.mapper.imp;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.project.domain.model.Dish;
import pl.project.infrastructure.database.entity.DishEntity;
import pl.project.infrastructure.database.repository.mapper.DishCategoryEntityMapper;
import pl.project.infrastructure.database.repository.mapper.DishEntityMapper;
import pl.project.infrastructure.database.repository.mapper.DishPhotoEntityMapper;
import pl.project.infrastructure.database.repository.mapper.RestaurantEntityMapper;

import java.util.Objects;

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
                .restaurant(Objects.isNull(entity.getRestaurant()) ?
                        null : restaurantEntityMapper.mapFromEntity(entity.getRestaurant()))
                .dishPhoto(Objects.isNull(entity.getDishPhoto()) ?
                        null : dishPhotoEntityMapper.mapFromEntity(entity.getDishPhoto()))
                .dishCategory(Objects.isNull(entity.getDishCategory()) ?
                        null : dishCategoryEntityMapper.mapFromEntity(entity.getDishCategory()))
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
                .restaurant(Objects.isNull(domainObj.getRestaurant()) ?
                        null : restaurantEntityMapper.mapToEntity(domainObj.getRestaurant()))
                .dishPhoto(Objects.isNull(domainObj.getDishPhoto()) ?
                        null : dishPhotoEntityMapper.mapToEntity(domainObj.getDishPhoto()))
                .dishCategory(Objects.isNull(domainObj.getDishCategory()) ?
                        null : dishCategoryEntityMapper.mapToEntity(domainObj.getDishCategory()))
                .isActive(domainObj.isActive())
                .build();
    }
}
