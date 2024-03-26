package pl.project.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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

    @Pattern(regexp = "^[A-Za-z0-9_]{1,32}$")
    String city;

    @Pattern(regexp = "^[A-Za-z0-9_]{1,32}$")
    String street;
}
