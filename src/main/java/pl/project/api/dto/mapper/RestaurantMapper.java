package pl.project.api.dto.mapper;

import pl.project.api.dto.RestaurantDTO;
import pl.project.domain.model.Restaurant;

public interface RestaurantMapper {

//    Restaurant mapFromDTO(RestaurantDTO dto);
    RestaurantDTO mapToDTO(Restaurant domainObj);

    Restaurant mapFromDTO(RestaurantDTO dto);
}
