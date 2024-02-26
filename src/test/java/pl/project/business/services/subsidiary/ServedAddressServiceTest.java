package pl.project.business.services.subsidiary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.project.business.dao.ServedAddressDAO;
import pl.project.domain.exception.EntityCreationException;
import pl.project.domain.model.Restaurant;
import pl.project.domain.model.ServedAddress;
import pl.project.util.domain.InstanceMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static pl.project.util.db.RestaurantInstance.someRestaurant1;
import static pl.project.util.db.ServedAddressInstance.someServedAddress1;
import static pl.project.util.db.ServedAddressInstance.someServedAddress2;

@ExtendWith(MockitoExtension.class)
class ServedAddressServiceTest {

    @Mock
    private ServedAddressDAO servedAddressDAO;

    @InjectMocks
    private ServedAddressService servedAddressService;

    private final InstanceMapper instanceMapper = new InstanceMapper();

    @Test
    void createServedAddress() {
        //given
        ServedAddress servedAddress = instanceMapper.mapFromEntity(someServedAddress1());
        ServedAddress anotherServedAddress = instanceMapper.mapFromEntity(someServedAddress2());
        when(servedAddressDAO.createServedAddress(servedAddress)).thenReturn(Optional.of(servedAddress));

        //when
        ServedAddress result = servedAddressService.createServedAddress(servedAddress);
        Exception exception = assertThrows(EntityCreationException.class, () -> servedAddressService.createServedAddress(anotherServedAddress));

        //then
        assertEquals(servedAddress, result);
        assertInstanceOf(EntityCreationException.class, exception);
        assertEquals("Fail to crete served address: ServedAddress(city=city2, street=street2)", exception.getMessage());
    }

    @Test
    void deleteServedAddress() {
        //given
        ServedAddress servedAddress = instanceMapper.mapFromEntity(someServedAddress1());
        when(servedAddressDAO.deleteServedAddress(servedAddress)).thenReturn(1);

        //when
        boolean result = servedAddressService.deleteServedAddress(servedAddress);

        //then
        assertTrue(result);
    }

    @Test
    void getServedAddresses() {
        //given
        Restaurant restaurant = instanceMapper.mapFromEntity(someRestaurant1());
        ServedAddress servedAddress1 = instanceMapper.mapFromEntity(someServedAddress1()).withRestaurant(restaurant);
        ServedAddress servedAddress2 = instanceMapper.mapFromEntity(someServedAddress2()).withRestaurant(restaurant);
        List<ServedAddress> servedAddresses = List.of(servedAddress1, servedAddress2);
        when(servedAddressDAO.getServedAddresses(restaurant)).thenReturn(servedAddresses);

        //when
        List<ServedAddress> result = servedAddressService.getServedAddresses(restaurant);

        //then
        assertEquals(2, result.size());
        assertThat(result).contains(servedAddress1, servedAddress2);
    }
}