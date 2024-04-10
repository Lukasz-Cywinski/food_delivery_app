package pl.project.api.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"dishCode"})
public class DishDTO {

    @Pattern(regexp = "^[a-zA-Z0-9]{8}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{12}$")
    String dishCode;

    @Pattern(regexp = "^[A-Za-z0-9_]{1,32}$")
    String name;

    @Size(max = 64)
    String description;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 4, fraction = 2)
    BigDecimal price;

    @Range(min = 1, max = 300)
    Integer averagePreparationTimeMin;

    @Pattern(regexp = "^[a-zA-Z0-9]{8}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{12}$")
    String restaurantCode;

    @PositiveOrZero
    Integer dishCategoryId;
    String dishCategoryName;
    String dishPhotoURL;
    String dishPhotoName;
}
