package pl.project.business.services.subsidiary.pageable;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pl.project.business.services.pageable.PageableService;
import pl.project.domain.model.PageableProperties;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PageableServiceTest {

    @InjectMocks
    PageableService pageableService;

    @Test
    void buildPageableTest() {
        //given
        PageableProperties pageableProperties1 = PageableProperties.builder()
                .pageNumber(0)
                .objectsPerPage(5)
                .sortedBy("name")
                .isAscending(true)
                .build();
        Pageable pageable1 = PageRequest.of(0, 5, Sort.by("name").ascending());
        PageableProperties pageableProperties2 = PageableProperties.builder()
                .pageNumber(0)
                .objectsPerPage(5)
                .sortedBy("name")
                .isAscending(false)
                .build();
        Pageable pageable2 = PageRequest.of(0, 5, Sort.by("name").descending());
        //when
        Pageable result1 = pageableService.buildPageable(pageableProperties1);
        Pageable result2 = pageableService.buildPageable(pageableProperties2);

        //then
        assertEquals(pageable1, result1);
        assertEquals(pageable2, result2);
    }
}