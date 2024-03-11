package pl.project.api.dto.mapper;

import pl.project.api.dto.DishDTO;
import pl.project.api.dto.RestaurantDTO;
import pl.project.domain.model.Dish;
import pl.project.domain.model.Restaurant;

public interface DishMapper {

    DishDTO mapToDTO(Dish domainObj);

    Dish mapFromDTO(DishDTO dto);
}
