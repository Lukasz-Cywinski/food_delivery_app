package pl.project.api.dto.mapper;

import pl.project.api.dto.PageablePropertiesDTO;
import pl.project.domain.model.PageableProperties;

public interface PageablePropertiesMapper {

    PageableProperties mapFromDTO(PageablePropertiesDTO pageablePropertiesDTO);

}
