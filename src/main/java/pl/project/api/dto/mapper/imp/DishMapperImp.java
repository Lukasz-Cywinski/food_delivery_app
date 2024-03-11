package pl.project.api.dto.mapper.imp;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.project.api.controller.addresses.HomeAddresses;
import pl.project.api.controller.addresses.ResourcePaths;
import pl.project.api.dto.DishCategoryDTO;
import pl.project.api.dto.DishDTO;
import pl.project.api.dto.RestaurantDTO;
import pl.project.api.dto.mapper.DishCategoryMapper;
import pl.project.api.dto.mapper.DishMapper;
import pl.project.api.dto.mapper.DishPhotoMapper;
import pl.project.api.dto.mapper.RestaurantMapper;
import pl.project.business.dao.DishCategoryDAO;
import pl.project.business.dao.RestaurantDAO;
import pl.project.domain.exception.EntityReadException;
import pl.project.domain.model.Dish;
import pl.project.domain.model.DishPhoto;
import pl.project.infrastructure.database.entity.DishCategoryEntity;
import pl.project.infrastructure.database.entity.RestaurantEntity;

import java.math.BigDecimal;
import java.util.Objects;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DishMapperImp implements DishMapper {

    private final String MAPPER_ENTITY_READ_EXCEPTION = "Failed to found resource %s by code: %s in dish mapper";

    RestaurantDAO restaurantDAO;
    DishCategoryDAO dishCategoryDAO;

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
                .restaurant(restaurantDAO.findRestaurantByRestaurantCode(dto.getRestaurantCode())
                        .orElseThrow(()-> new EntityReadException(
                                MAPPER_ENTITY_READ_EXCEPTION.formatted(RestaurantEntity.class, dto.getRestaurantCode())
                        )))
                .dishCategory(dishCategoryDAO.getDishCategoryByDishCategoryId(dto.getDishCategoryId())
                        .orElseThrow(() -> new EntityReadException(
                                MAPPER_ENTITY_READ_EXCEPTION.formatted(DishCategoryEntity.class, dto.getDishCategoryId())
                        )))
                .build();
    }
}
