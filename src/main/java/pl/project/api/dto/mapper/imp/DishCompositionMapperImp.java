package pl.project.api.dto.mapper.imp;

import org.springframework.stereotype.Component;
import pl.project.api.dto.DishCompositionDTO;
import pl.project.api.dto.mapper.DishCompositionMapper;
import pl.project.domain.model.DishComposition;

@Component
public class DishCompositionMapperImp implements DishCompositionMapper {
    @Override
    public DishCompositionDTO mapToDTO(DishComposition domainObj) {
        return DishCompositionDTO.builder()
                .quantity(domainObj.getQuantity())
                .dishName(domainObj.getDish().getName())
                .dishCode(domainObj.getDish().getDishCode())
                .build();
    }
}
