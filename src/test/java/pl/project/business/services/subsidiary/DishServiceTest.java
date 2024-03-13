package pl.project.business.services.subsidiary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.project.business.dao.DishDAO;
import pl.project.domain.exception.restaurant_owner.OwnerResourceCreationException;
import pl.project.domain.exception.restaurant_owner.OwnerResourceReadException;
import pl.project.domain.model.Dish;
import pl.project.util.domain.InstanceMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pl.project.util.db.DishInstance.*;

@ExtendWith(MockitoExtension.class)
class DishServiceTest {

//    @Mock
//    private DishDAO dishDAO;
//    @Mock
//    private DishPhotoService dishPhotoService;
//
//    @InjectMocks
//    private DishService dishService;
//
//    InstanceMapper instanceMapper = new InstanceMapper();
//
//    @Test
//    void getDish() {
//        //given
//        Dish dish = instanceMapper.mapFromEntity(someDish1());
//        when(dishDAO.findDishByDishCode(dish.getDishCode())).thenReturn(Optional.of(dish));
//
//        //when
//        Dish result = dishService.getDish(dish.getDishCode());
//        Exception exception = assertThrows(OwnerResourceReadException.class, () -> dishService.getDish("fake code"));
//
//        //then
//        assertEquals(dish, result);
//        assertInstanceOf(OwnerResourceReadException.class, exception);
//        assertEquals("Fail to found dish by dish code: fake code", exception.getMessage());
//    }
//
//    @Test
//    void createDish() {
//        //given
//        Dish dish = instanceMapper.mapFromEntity(someDish1());
//        Dish anotherDish = instanceMapper.mapFromEntity(someDish2());
//        when(dishDAO.createDish(dish)).thenReturn(Optional.of(dish));
//
//        //when
//        Dish result = dishService.createDish(dish);
//        Exception exception = assertThrows(OwnerResourceCreationException.class, () -> dishService.createDish(anotherDish));
//
//        //then
//        assertEquals(dish, result);
//        assertInstanceOf(OwnerResourceCreationException.class, exception);
//        assertEquals("Fail to crete dish: Dish(name=name2, description=description2, price=2.0, averagePreparationTimeMin=32)",
//                exception.getMessage());
//    }
//
//    @Test
//    void modifyDishData() {
//        //given
//        String dishCode = instanceMapper.mapFromEntity(someDish1()).getDishCode();
//        Dish updatedDish = instanceMapper.mapFromEntity(someDish2());
//
//        when(dishDAO.changeDishName(updatedDish.getName(),dishCode)).thenReturn(1);
//        when(dishDAO.changeDishDescription(updatedDish.getDescription(),dishCode)).thenReturn(1);
//        when(dishDAO.changeDishPrice(updatedDish.getPrice(),dishCode)).thenReturn(1);
//        when(dishDAO.changeDishPreparationTime(updatedDish.getAveragePreparationTimeMin(),dishCode)).thenReturn(1);
//        when(dishDAO.changeDishCategory(updatedDish.getDishCategory(),dishCode)).thenReturn(1);
//
//        when(dishPhotoService.createDishPhoto(updatedDish.getDishPhoto())).thenReturn(updatedDish.getDishPhoto());
//        when(dishDAO.changeDishPhoto(updatedDish.getDishPhoto(), dishCode)).thenReturn(1);
//
//        //when
//        boolean result = dishService.modifyDishData(updatedDish, dishCode);
//
//        //then
//        assertTrue(result);
//    }
//
//    @Test
//    void deactivateDish() {
//        //given
//        Dish dish = instanceMapper.mapFromEntity(someDish1());
//        when(dishDAO.deactivateDish(dish.getDishCode())).thenReturn(1);
//
//        //when
//        boolean result = dishService.deactivateDish(dish.getDishCode());
//
//        //then
//        assertTrue(result);
//    }
}