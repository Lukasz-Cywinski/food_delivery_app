package pl.project.business.services.subsidiary;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DishPhotoServiceTest {

//    @Mock
//    private DishPhotoDAO dishPhotoDAO;
//
//    @InjectMocks
//    private DishPhotoService dishPhotoService;
//
//    private final InstanceMapper instanceMapper = new InstanceMapper();
//
//    @Test
//    void createDishPhoto() {
//        //given
//        DishPhoto dishPhoto = instanceMapper.mapFromEntity(someDishPhoto1());
//        DishPhoto anotherPhoto = instanceMapper.mapFromEntity(someDishPhoto2());
//        Mockito.when(dishPhotoDAO.createDishPhoto(dishPhoto)).thenReturn(Optional.of(dishPhoto));
//
//        //when
//        DishPhoto result = dishPhotoService.createDishPhoto(dishPhoto);
//        Exception exception = assertThrows(OwnerResourceCreationException.class, () -> dishPhotoService.createDishPhoto(anotherPhoto));
//
//        //then
//        assertEquals(dishPhoto, result);
//        assertInstanceOf(OwnerResourceCreationException.class, exception);
//        assertEquals("Fail to crete dish photo: DishPhoto(name=name2, url=url/example2)", exception.getMessage());
//    }
}