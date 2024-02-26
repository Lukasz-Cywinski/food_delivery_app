package pl.project.business.services.subsidiary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.project.business.dao.DishPhotoDAO;
import pl.project.domain.exception.EntityCreationException;
import pl.project.domain.model.DishPhoto;
import pl.project.util.domain.InstanceMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static pl.project.util.db.DishPhotoInstance.someDishPhoto1;
import static pl.project.util.db.DishPhotoInstance.someDishPhoto2;

@ExtendWith(MockitoExtension.class)
class DishPhotoServiceTest {

    @Mock
    private DishPhotoDAO dishPhotoDAO;

    @InjectMocks
    private DishPhotoService dishPhotoService;

    private final InstanceMapper instanceMapper = new InstanceMapper();

    @Test
    void createDishPhoto() {
        //given
        DishPhoto dishPhoto = instanceMapper.mapFromEntity(someDishPhoto1());
        DishPhoto anotherPhoto = instanceMapper.mapFromEntity(someDishPhoto2());
        Mockito.when(dishPhotoDAO.createDishPhoto(dishPhoto)).thenReturn(Optional.of(dishPhoto));

        //when
        DishPhoto result = dishPhotoService.createDishPhoto(dishPhoto);
        Exception exception = assertThrows(EntityCreationException.class, () -> dishPhotoService.createDishPhoto(anotherPhoto));

        //then
        assertEquals(dishPhoto, result);
        assertInstanceOf(EntityCreationException.class, exception);
        assertEquals("Fail to crete dish photo: DishPhoto(name=name2, url=url/example2)", exception.getMessage());
    }
}