package pl.project.api.dto.mapper.imp;

import org.springframework.stereotype.Component;
import pl.project.api.dto.PageablePropertiesDTO;
import pl.project.api.dto.mapper.PageablePropertiesMapper;
import pl.project.domain.model.PageableProperties;

@Component
public class PageablePropertiesMapperImp implements PageablePropertiesMapper {
    @Override
    public PageableProperties mapFromDTO(PageablePropertiesDTO pageablePropertiesDTO) {
        return PageableProperties.builder()
                .pageNumber(pageablePropertiesDTO.getPageNumber())
                .objectsPerPage(pageablePropertiesDTO.getObjectsPerPage())
                .sortedBy(pageablePropertiesDTO.getSortedBy())
                .isAscending(Boolean.parseBoolean(pageablePropertiesDTO.getIsAscending()))
                .build();
    }
}
