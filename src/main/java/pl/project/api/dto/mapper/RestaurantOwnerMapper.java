package pl.project.api.dto.mapper;

import pl.project.api.dto.RestaurantOwnerDTO;
import pl.project.domain.model.RestaurantOwner;

public interface RestaurantOwnerMapper {
    RestaurantOwner mapFromDTO(RestaurantOwnerDTO dto);
}
