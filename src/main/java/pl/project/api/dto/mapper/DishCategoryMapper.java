package pl.project.api.dto.mapper;

import pl.project.api.dto.DishCategoryDTO;
import pl.project.domain.model.DishCategory;

public interface DishCategoryMapper {

    DishCategoryDTO mapToDTO(DishCategory domainObj);

    DishCategory mapFromDTO(DishCategoryDTO dto);
}
