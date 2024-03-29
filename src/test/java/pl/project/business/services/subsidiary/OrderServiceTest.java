package pl.project.business.services.subsidiary;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

//    @Mock
//    private OrderDAO orderDAO;
//
//    @Mock
//    private DishCompositionService dishCompositionService;
//
//    @InjectMocks
//    private OrderService orderService;
//
//    private final InstanceMapper instanceMapper = new InstanceMapper();
//
//    @Test
//    void createOrderTest() {
//        //given
//        Customer customer = instanceMapper.mapFromEntity(someCustomer1());
//        DeliveryService deliveryService = instanceMapper.mapFromEntity(someDeliveryService1());
//        Order order = instanceMapper.mapFromEntity(someOrder1());
//        when(orderDAO.createOrder(any())).thenReturn(Optional.of(order));
//
//        //when
//        Order result = orderService.createOrder(customer, deliveryService);
//
//        //then
//        assertEquals(order, result);
//    }
//
//    @Test
//    void createOrderEntityCreationException() {
//        //given
//        Customer customer = instanceMapper.mapFromEntity(someCustomer1());
//        DeliveryService deliveryService = instanceMapper.mapFromEntity(someDeliveryService1());
//        Order order = instanceMapper.mapFromEntity(someOrder1());
//        lenient().when(orderDAO.createOrder(order)).thenReturn(Optional.of(order));
//
//        //when
//        Exception exception = assertThrows(OwnerResourceCreationException.class, () -> orderService.createOrder(customer, deliveryService));
//
//        //then
//        assertInstanceOf(OwnerResourceCreationException.class, exception);
//        assertThat(exception.getMessage()).contains("Fail to crete order with order code:");
//    }
//
//    @Test
//    void cancelOrder() {
//        //given
//        Order orderPossibleToCancel = instanceMapper.mapFromEntity(someOrder1()).withReceivedDateTime(OffsetDateTime.now());
//        Order orderImpossibleToCancel = instanceMapper.mapFromEntity(someOrder2()).withReceivedDateTime(OffsetDateTime.now().minusHours(1));
//        Order otherOrder = instanceMapper.mapFromEntity(someOrder3());
//
//        when(orderDAO.findOrderByOrderCode(orderPossibleToCancel.getOrderCode()))
//                .thenReturn(Optional.of(orderPossibleToCancel));
//        when(orderDAO.findOrderByOrderCode(orderImpossibleToCancel.getOrderCode()))
//                .thenReturn(Optional.of(orderImpossibleToCancel));
//
//        //when
//        boolean result1 = orderService.cancelOrder(orderPossibleToCancel.getOrderCode());
//        boolean result2 = orderService.cancelOrder(orderImpossibleToCancel.getOrderCode());
//        Exception exception = assertThrows(OwnerResourceReadException.class, () -> orderService.cancelOrder(otherOrder.getOrderCode()));
//
//        //then
//        assertTrue(result1);
//        assertFalse(result2);
//        assertInstanceOf(OwnerResourceReadException.class, exception);
//        assertThat(exception.getMessage()).contains("Fail to found order by order code:");
//    }
//
//    @Test
//    void finishOrder() {
//        //given
//        Order order = instanceMapper.mapFromEntity(someOrder2());
//
//        when(orderDAO.reportCompletedDateTime(any(), eq(order.getOrderCode()))).thenReturn(1);
//
//        //when
//        boolean result = orderService.finishOrder(order);
//
//        //then
//        assertTrue(result);
//    }
}