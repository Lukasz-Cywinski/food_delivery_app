package pl.project.api.dto.mapper;

import pl.project.api.dto.DishPhotoDTO;
import pl.project.domain.model.DishPhoto;

public interface DishPhotoMapper {

    DishPhotoDTO mapToDTO(DishPhoto domainObj);

    DishPhoto mapFromDTO(DishPhotoDTO dto);
}
