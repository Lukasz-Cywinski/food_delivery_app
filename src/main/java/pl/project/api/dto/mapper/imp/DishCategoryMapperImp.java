package pl.project.api.dto.mapper.imp;

import org.springframework.stereotype.Component;
import pl.project.api.dto.DishCategoryDTO;
import pl.project.api.dto.mapper.DishCategoryMapper;
import pl.project.domain.model.DishCategory;

@Component
public class DishCategoryMapperImp implements DishCategoryMapper {
    @Override
    public DishCategoryDTO mapToDTO(DishCategory domainObj) {
        return DishCategoryDTO.builder()
                .id(domainObj.getId())
                .name(domainObj.getName())
                .description(domainObj.getDescription())
                .build();
    }

    @Override
    public DishCategory mapFromDTO(DishCategoryDTO dto) {
        return DishCategory.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }
}
