package pl.project.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageNavigationDTO {

    Integer firstPage;
    Integer lastPage;
    Integer currentPage;
    Integer previousPage;
    Integer nextPage;
}
