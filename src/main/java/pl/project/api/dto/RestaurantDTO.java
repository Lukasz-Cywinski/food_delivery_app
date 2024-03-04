package pl.project.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.project.domain.model.RestaurantOwner;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {

    String restaurantCode;
    String name;
    String added;
    RestaurantOwner restaurantOwner;
    boolean isActive;
}
