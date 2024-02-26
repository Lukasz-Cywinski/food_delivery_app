package pl.project.business.services.subsidiary.pageable;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.project.domain.model.PageableProperties;

@Service
@AllArgsConstructor
public class PageableService {

    public Pageable buildPageable(PageableProperties pageableProperties){
        Sort sort = pageableProperties.isAscending()
                ? Sort.by(pageableProperties.getSortedBy()).ascending() : Sort.by(pageableProperties.getSortedBy()).descending();
        return PageRequest.of(pageableProperties.getPageNumber(), pageableProperties.getObjectsPerPage(), sort);
    }
}
