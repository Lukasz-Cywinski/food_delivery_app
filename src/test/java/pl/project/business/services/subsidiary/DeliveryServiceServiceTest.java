package pl.project.business.services.subsidiary;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceServiceTest {

//    @Mock
//    private DeliveryServiceDAO deliveryServiceDAO;
//    @Mock
//    private DeliveryManDAO deliveryManDAO;
//    @Mock
//    private OrderService orderService;
//
//    @InjectMocks
//    private DeliveryServiceService deliveryServiceService;
//
//    private final InstanceMapper instanceMapper = new InstanceMapper();
//
//    @Test
//    void createDeliveryServiceTest() {
//        //given
//        DeliveryMan deliveryMan1 = instanceMapper.mapFromEntity(someDeliveryMan1());
//        DeliveryMan deliveryMan2 = instanceMapper.mapFromEntity(someDeliveryMan2());
//        DeliveryMan deliveryMan3 = instanceMapper.mapFromEntity(someDeliveryMan3());
//        List<DeliveryMan> deliveryMen = List.of(deliveryMan1, deliveryMan2, deliveryMan3);
//        DeliveryService deliveryService = instanceMapper.mapFromEntity(someDeliveryService1()).withDeliveryMan(deliveryMan1);
//
//        when(deliveryManDAO.getAvailableDeliveryMen()).thenReturn(deliveryMen);
//        when(deliveryServiceDAO.createDeliveryService(any())).thenReturn(Optional.of(deliveryService));
//
//        //when
//        DeliveryService result = deliveryServiceService.createDeliveryService();
//
//        //then
//        assertEquals(deliveryService, result);
//    }
//
//    @Test
//    void createDeliveryServiceNoAvailableDeliveryManExceptionsTest() {
//        //given
//        when(deliveryManDAO.getAvailableDeliveryMen()).thenReturn(List.of());
//
//        //when
//        Exception exception = assertThrows(NoAvailableDeliveryMan.class, () -> deliveryServiceService.createDeliveryService());
//
//        //then
//        assertInstanceOf(NoAvailableDeliveryMan.class, exception);
//        assertEquals("No available delivery man please try later", exception.getMessage());
//    }
//
//    @Test
//    void createDeliveryServiceEntityCreationExceptionTest() {
//        //given
//        DeliveryMan deliveryMan1 = instanceMapper.mapFromEntity(someDeliveryMan1());
//        DeliveryMan deliveryMan2 = instanceMapper.mapFromEntity(someDeliveryMan2());
//        DeliveryMan deliveryMan3 = instanceMapper.mapFromEntity(someDeliveryMan3());
//        List<DeliveryMan> deliveryMen = List.of(deliveryMan1, deliveryMan2, deliveryMan3);
//        DeliveryService deliveryService = instanceMapper.mapFromEntity(someDeliveryService1()).withDeliveryMan(deliveryMan1);
//
//        when(deliveryManDAO.getAvailableDeliveryMen()).thenReturn(deliveryMen);
//        lenient().when(deliveryServiceDAO.createDeliveryService(deliveryService)).thenReturn(Optional.of(deliveryService));
//
//        //when
//        Exception exception = assertThrows(OwnerResourceCreationException.class, () -> deliveryServiceService.createDeliveryService());
//
//        //then
//        assertInstanceOf(OwnerResourceCreationException.class, exception);
//        Assertions.assertThat(exception.getMessage()).contains("Fail to crete delivery service with code:");
//    }
//
//    @Test
//    void deliverOrderTest() {
//        //given
//        Order order = instanceMapper.mapFromEntity(someOrder1());
//        when(orderService.finishOrder(order)).thenReturn(true);
//        when(deliveryServiceDAO.reportCompletedDateTime(any(), eq(order.getOrderCode()))).thenReturn(1);
//
//        //when
//        boolean result = deliveryServiceService.deliverOrder(order);
//
//        //then
//        assertTrue(result);
//    }
}