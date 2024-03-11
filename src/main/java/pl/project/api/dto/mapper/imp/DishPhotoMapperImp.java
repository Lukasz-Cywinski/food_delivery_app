package pl.project.api.dto.mapper.imp;

import org.springframework.stereotype.Component;
import pl.project.api.dto.DishPhotoDTO;
import pl.project.api.dto.mapper.DishPhotoMapper;
import pl.project.domain.model.DishPhoto;

@Component
public class DishPhotoMapperImp implements DishPhotoMapper {
    @Override
    public DishPhotoDTO mapToDTO(DishPhoto domainObj) {
        return DishPhotoDTO.builder()
                .name(domainObj.getName())
                .url(domainObj.getUrl())
                .build();
    }

    @Override
    public DishPhoto mapFromDTO(DishPhotoDTO dto) {
        return DishPhoto.builder()
                .name(dto.getName())
                .url(dto.getUrl())
                .build();
    }
}
