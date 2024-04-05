package pl.project.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageablePropertiesDTO {

    Integer pageNumber;
    Integer objectsPerPage;
    String sortedBy;
    String isAscending;
}
