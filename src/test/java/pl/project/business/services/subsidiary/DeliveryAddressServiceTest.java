package pl.project.business.services.subsidiary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.project.business.dao.DeliveryAddressDAO;
import pl.project.domain.model.Customer;
import pl.project.domain.model.DeliveryAddress;
import pl.project.util.domain.InstanceMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pl.project.util.db.CustomerInstance.*;
import static pl.project.util.db.DeliveryAddressInstance.*;

@ExtendWith(MockitoExtension.class)
class DeliveryAddressServiceTest {

//    @Mock
//    private DeliveryAddressDAO deliveryAddressDAO;
//
//    @InjectMocks
//    private DeliveryAddressService deliveryAddressService;
//
//    InstanceMapper instanceMapper = new InstanceMapper();
//
//    @Test
//    void modifyCustomerDeliveryAddressTest() {
//        //given
//        DeliveryAddress deliveryAddress = instanceMapper.mapFromEntity(someDeliveryAddress1()).withId(1);
//        DeliveryAddress updatedDeliveryAddress = instanceMapper.mapFromEntity(someDeliveryAddress2()).withId(2);
//        Customer customer = instanceMapper.mapFromEntity(someCustomer1()).withDeliveryAddress(deliveryAddress);
//        Integer addressId = customer.getDeliveryAddress().getId();
//
//        when(deliveryAddressDAO.changeDeliveryAddressCity(updatedDeliveryAddress.getCity(), addressId)).thenReturn(1);
//        when(deliveryAddressDAO.changeDeliveryAddressStreet(updatedDeliveryAddress.getStreet(), addressId)).thenReturn(1);
//        when(deliveryAddressDAO.changeDeliveryAddressPostalCode(updatedDeliveryAddress.getPostalCode(), addressId)).thenReturn(1);
//
//        //when
//        boolean result = deliveryAddressService.modifyCustomerDeliveryAddress(updatedDeliveryAddress, customer);
//
//        //then
//        assertTrue(result);
//    }
}