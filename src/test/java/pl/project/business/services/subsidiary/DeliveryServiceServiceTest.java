package pl.project.business.services.subsidiary;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.project.business.dao.DeliveryManDAO;
import pl.project.business.dao.DeliveryServiceDAO;
import pl.project.domain.exception.EntityCreationException;
import pl.project.domain.exception.NoAvailableDeliveryMan;
import pl.project.domain.model.DeliveryMan;
import pl.project.domain.model.DeliveryService;
import pl.project.domain.model.Order;
import pl.project.util.domain.InstanceMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static pl.project.util.db.DeliveryManInstance.*;
import static pl.project.util.db.DeliveryServiceInstance.*;
import static pl.project.util.db.OrderInstance.*;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceServiceTest {

    @Mock
    private DeliveryServiceDAO deliveryServiceDAO;
    @Mock
    private DeliveryManDAO deliveryManDAO;
    @Mock
    private OrderService orderService;

    @InjectMocks
    private DeliveryServiceService deliveryServiceService;

    private final InstanceMapper instanceMapper = new InstanceMapper();

    @Test
    void createDeliveryServiceTest() {
        //given
        DeliveryMan deliveryMan1 = instanceMapper.mapFromEntity(someDeliveryMan1());
        DeliveryMan deliveryMan2 = instanceMapper.mapFromEntity(someDeliveryMan2());
        DeliveryMan deliveryMan3 = instanceMapper.mapFromEntity(someDeliveryMan3());
        List<DeliveryMan> deliveryMen = List.of(deliveryMan1, deliveryMan2, deliveryMan3);
        DeliveryService deliveryService = instanceMapper.mapFromEntity(someDeliveryService1()).withDeliveryMan(deliveryMan1);

        when(deliveryManDAO.getAvailableDeliveryMen()).thenReturn(deliveryMen);
        when(deliveryServiceDAO.createDeliveryService(any())).thenReturn(Optional.of(deliveryService));

        //when
        DeliveryService result = deliveryServiceService.createDeliveryService();

        //then
        assertEquals(deliveryService, result);
    }

    @Test
    void createDeliveryServiceNoAvailableDeliveryManExceptionsTest() {
        //given
        when(deliveryManDAO.getAvailableDeliveryMen()).thenReturn(List.of());

        //when
        Exception exception = assertThrows(NoAvailableDeliveryMan.class, () -> deliveryServiceService.createDeliveryService());

        //then
        assertInstanceOf(NoAvailableDeliveryMan.class, exception);
        assertEquals("No available delivery man please try later", exception.getMessage());
    }

    @Test
    void createDeliveryServiceEntityCreationExceptionTest() {
        //given
        DeliveryMan deliveryMan1 = instanceMapper.mapFromEntity(someDeliveryMan1());
        DeliveryMan deliveryMan2 = instanceMapper.mapFromEntity(someDeliveryMan2());
        DeliveryMan deliveryMan3 = instanceMapper.mapFromEntity(someDeliveryMan3());
        List<DeliveryMan> deliveryMen = List.of(deliveryMan1, deliveryMan2, deliveryMan3);
        DeliveryService deliveryService = instanceMapper.mapFromEntity(someDeliveryService1()).withDeliveryMan(deliveryMan1);

        when(deliveryManDAO.getAvailableDeliveryMen()).thenReturn(deliveryMen);
        lenient().when(deliveryServiceDAO.createDeliveryService(deliveryService)).thenReturn(Optional.of(deliveryService));

        //when
        Exception exception = assertThrows(EntityCreationException.class, () -> deliveryServiceService.createDeliveryService());

        //then
        assertInstanceOf(EntityCreationException.class, exception);
        Assertions.assertThat(exception.getMessage()).contains("Fail to crete delivery service with code:");
    }

    @Test
    void deliverOrderTest() {
        //given
        Order order = instanceMapper.mapFromEntity(someOrder1());
        when(orderService.finishOrder(order)).thenReturn(true);
        when(deliveryServiceDAO.reportCompletedDateTime(any(), eq(order.getOrderCode()))).thenReturn(1);

        //when
        boolean result = deliveryServiceService.deliverOrder(order);

        //then
        assertTrue(result);
    }
}