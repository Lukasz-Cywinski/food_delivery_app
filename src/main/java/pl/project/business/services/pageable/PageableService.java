package pl.project.business.services.pageable;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.project.api.dto.PageNavigationDTO;
import pl.project.domain.exception.ExceptionMessages;
import pl.project.domain.exception.customer.CustomerSearchFiltersException;
import pl.project.domain.model.PageableProperties;

@Service
@AllArgsConstructor
public class PageableService {

    public Pageable buildPageable(PageableProperties pageableProperties){
        Sort sort = pageableProperties.isAscending()
                ? Sort.by(pageableProperties.getSortedBy()).ascending() : Sort.by(pageableProperties.getSortedBy()).descending();
        return PageRequest.of(pageableProperties.getPageNumber(), pageableProperties.getObjectsPerPage(), sort);
    }

    public PageNavigationDTO buildPageNavigation(Integer allMatchedObjects, PageableProperties pageableProperties) {
        try {
            Integer objectsPerPage = pageableProperties.getObjectsPerPage();

            Integer lastPage = 0;
            if(allMatchedObjects > objectsPerPage) {
                lastPage = (allMatchedObjects % objectsPerPage == 0) ?
                        ((allMatchedObjects / objectsPerPage) - 1) : (allMatchedObjects / objectsPerPage);
            }
            Integer firstPage = 0;
            Integer currentPage = pageableProperties.getPageNumber();
            Integer previousPage = (currentPage.equals(firstPage)) ? firstPage : currentPage - 1;
            Integer nextPage = (currentPage.equals(lastPage)) ? lastPage : currentPage + 1;

            return PageNavigationDTO.builder()
                    .firstPage(firstPage)
                    .lastPage(lastPage)
                    .currentPage(currentPage)
                    .previousPage(previousPage)
                    .nextPage(nextPage)
                    .build();
        } catch (Exception e) {
            throw new CustomerSearchFiltersException(ExceptionMessages.SEARCH_FILTER_EXCEPTION, e);
        }
    }
}
