package pl.project.domain.model;

import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"pageNumber", "objectsPerPage", "sortedBy", "isAscending"})
@ToString(of = {"pageNumber", "objectsPerPage", "sortedBy", "isAscending"})
public class PageableProperties {
    Integer pageNumber;
    Integer objectsPerPage;
    String sortedBy;
    boolean isAscending;
}
