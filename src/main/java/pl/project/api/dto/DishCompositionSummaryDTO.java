package pl.project.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishCompositionSummaryDTO {
    Map<DishDTO, String> compositions;
    BigDecimal totalPrice;
    String orderCode;
    Integer estimatedPreparationTime;

}
