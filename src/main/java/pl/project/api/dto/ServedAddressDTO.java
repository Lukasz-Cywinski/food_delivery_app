package pl.project.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.project.domain.model.Restaurant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServedAddressDTO {

    Integer id;
    String city;
    String street;
    RestaurantDTO restaurant;
}
