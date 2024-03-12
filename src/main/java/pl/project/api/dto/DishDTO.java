package pl.project.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import pl.project.domain.model.DishCategory;
import pl.project.domain.model.DishPhoto;
import pl.project.domain.model.Restaurant;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishDTO {

    String dishCode;
    String name;
    String description;
    BigDecimal price;
    Integer averagePreparationTimeMin;
    String restaurantCode;
    Integer dishCategoryId;
    String dishCategoryName;
    String dishPhotoURL;
    String dishPhotoName;
}
