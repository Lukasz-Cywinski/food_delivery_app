package pl.project.business.services.subsidiary;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.project.business.dao.DishCompositionDAO;
import pl.project.domain.model.Order;
import pl.project.domain.model.Restaurant;
import pl.project.util.domain.InstanceMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static pl.project.util.db.OrderInstance.someOrder1;
import static pl.project.util.db.OrderInstance.someOrder2;
import static pl.project.util.db.RestaurantInstance.someRestaurant1;

@ExtendWith(MockitoExtension.class)
class DishCompositionServiceTest {

    @Mock
    private DishCompositionDAO dishCompositionDAO;

    @InjectMocks
    private DishCompositionService dishCompositionService;

    private final InstanceMapper instanceMapper = new InstanceMapper();

    @Test
    void getActiveOrdersForRestaurant() {
        //given
        Restaurant restaurant = instanceMapper.mapFromEntity(someRestaurant1());
        Order order1 = instanceMapper.mapFromEntity(someOrder1());
        Order order2 = instanceMapper.mapFromEntity(someOrder2());
        List<Order> orders = List.of(order1, order2);
        when(dishCompositionDAO.getActiveOrdersForRestaurant(restaurant)).thenReturn(orders);

        //when
        List<Order> result = dishCompositionService.getActiveOrdersForRestaurant(restaurant);

        //then
        assertEquals(2, result.size());
        Assertions.assertThat(result).contains(order1, order2);
    }

    @Test
    void getRealizedOrdersForRestaurant() {
        //given
        Restaurant restaurant = instanceMapper.mapFromEntity(someRestaurant1());
        Order order1 = instanceMapper.mapFromEntity(someOrder1());
        Order order2 = instanceMapper.mapFromEntity(someOrder2());
        List<Order> orders = List.of(order1, order2);
        when(dishCompositionDAO.getRealizedOrdersForRestaurant(restaurant)).thenReturn(orders);

        //when
        List<Order> result = dishCompositionService.getRealizedOrdersForRestaurant(restaurant);

        //then
        assertEquals(2, result.size());
        Assertions.assertThat(result).contains(order1, order2);
    }
}