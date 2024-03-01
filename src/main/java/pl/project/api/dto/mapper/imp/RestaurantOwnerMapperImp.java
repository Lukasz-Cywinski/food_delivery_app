package pl.project.api.dto.mapper.imp;

import org.springframework.stereotype.Component;
import pl.project.api.dto.RestaurantOwnerDTO;
import pl.project.api.dto.mapper.RestaurantOwnerMapper;
import pl.project.domain.model.RestaurantOwner;

@Component
public class RestaurantOwnerMapperImp implements RestaurantOwnerMapper {

    @Override
    public RestaurantOwner mapFromDTO(RestaurantOwnerDTO restaurantOwnerDTO) {
        return RestaurantOwner.builder()
                .name(restaurantOwnerDTO.getName())
                .surname(restaurantOwnerDTO.getSurname())
                .phoneNumber(restaurantOwnerDTO.getPhoneNumber())
                .email(restaurantOwnerDTO.getEmail())
                .build();
    }
}
