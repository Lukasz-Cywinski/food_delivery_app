package pl.project.api.dto.mapper.imp;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.project.api.dto.DishDTO;
import pl.project.api.dto.mapper.DishMapper;
import pl.project.domain.model.Dish;
import pl.project.domain.model.DishCategory;
import pl.project.domain.model.Restaurant;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DishMapperImp implements DishMapper {

    private final String MAPPER_ENTITY_READ_EXCEPTION = "Failed to found resource %s by code: %s in dish mapper";

//    RestaurantDAO restaurantDAO;
//    DishCategoryDAO dishCategoryDAO;

    @Override
    public DishDTO mapToDTO(Dish domainObj) {
        return DishDTO.builder()
                .name(domainObj.getName())
                .dishCode(domainObj.getDishCode())
                .description(domainObj.getDescription())
                .price(domainObj.getPrice())
                .averagePreparationTimeMin(domainObj.getAveragePreparationTimeMin())
                .restaurantCode(domainObj.getRestaurant().getRestaurantCode())
                .dishCategoryId(domainObj.getDishCategory().getId())
                .dishPhotoURL(domainObj.getDishPhoto().getUrl())
                .dishPhotoName(domainObj.getDishPhoto().getName()
                        .substring(domainObj.getDishPhoto().getName().indexOf("_") + 1))
                .dishCategoryName(domainObj.getDishCategory().getName())
                .build();
    }

    @Override
    public Dish mapFromDTO(DishDTO dto) {
        return Dish.builder()
                .name(dto.getName())
                .dishCode(dto.getDishCode())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .averagePreparationTimeMin(dto.getAveragePreparationTimeMin())
                .restaurant(Restaurant.builder()
                        .restaurantCode(dto.getRestaurantCode())
                        .build())
                .dishCategory(DishCategory.builder()
                        .id(dto.getDishCategoryId())
                        .build())
//                .restaurant(restaurantDAO.findRestaurantByRestaurantCode(dto.getRestaurantCode())
//                        .orElseThrow(()-> new EntityReadException(
//                                MAPPER_ENTITY_READ_EXCEPTION.formatted(RestaurantEntity.class, dto.getRestaurantCode())
//                        )))
//                .dishCategory(dishCategoryDAO.getDishCategoryByDishCategoryId(dto.getDishCategoryId())
//                        .orElseThrow(() -> new EntityReadException(
//                                MAPPER_ENTITY_READ_EXCEPTION.formatted(DishCategoryEntity.class, dto.getDishCategoryId())
//                        )))
                .build();
    }
}
