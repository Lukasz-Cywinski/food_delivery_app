package pl.project.api.dto.mapper.imp;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.project.api.dto.RestaurantDTO;
import pl.project.api.dto.mapper.RestaurantMapper;
import pl.project.api.dto.mapper.RestaurantOwnerMapper;
import pl.project.domain.formatter.Formatters;
import pl.project.domain.model.Restaurant;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static pl.project.domain.formatter.Formatters.*;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RestaurantMapperImp implements RestaurantMapper {

    private final RestaurantOwnerMapper restaurantOwnerMapper;

    @Override
    public RestaurantDTO mapToDTO(Restaurant domainObj) {
        return RestaurantDTO.builder()
                .restaurantCode(domainObj.getRestaurantCode())
                .name(domainObj.getName())
                .added(domainObj.getAdded().format(DATE_TIME_FORMATTER))
                .build();
    }

    @Override
    public Restaurant mapFromDTO(RestaurantDTO dto) {
        return Restaurant.builder()
                .restaurantCode(dto.getRestaurantCode())
                .name(dto.getName())
                .build();
    }
}
