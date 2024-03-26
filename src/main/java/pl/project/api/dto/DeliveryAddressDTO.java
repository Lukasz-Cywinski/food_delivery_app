package pl.project.api.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddressDTO {

    Integer id;

    @Pattern(regexp = "^[A-Za-z0-9_]{1,32}$")
    String city;

    @Pattern(regexp = "\\d+/\\d*")
    String buildingNumber;

    @Pattern(regexp = "^[A-Za-z0-9_]{1,32}$")
    String street;
}
