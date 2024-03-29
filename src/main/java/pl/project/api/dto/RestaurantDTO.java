package pl.project.api.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.project.domain.model.RestaurantOwner;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {

    @Pattern(regexp = "^[a-zA-Z0-9]{8}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{12}$")
    String restaurantCode;

    @Pattern(regexp = "^[A-Za-z0-9_]{1,32}$")
    String name;
    String added;
}
