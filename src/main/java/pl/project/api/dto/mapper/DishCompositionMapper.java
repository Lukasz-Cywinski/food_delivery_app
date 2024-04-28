package pl.project.api.dto.mapper;

import pl.project.api.dto.DishCompositionDTO;
import pl.project.domain.model.DishComposition;

public interface DishCompositionMapper {

    DishCompositionDTO mapToDTO(DishComposition domainObj);

}
