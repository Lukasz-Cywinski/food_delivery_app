package pl.project.business.services.subsidiary;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.project.business.dao.ServedAddressDAO;
import pl.project.domain.exception.EntityCreationException;
import pl.project.domain.model.Restaurant;
import pl.project.domain.model.ServedAddress;

import java.util.List;

@Service
@AllArgsConstructor
public class ServedAddressService {

    private final String SERVED_ADDRESS_CREATION_EXCEPTION = "Fail to crete served address: %s";

    private ServedAddressDAO servedAddressDAO;

    // without transactional -> methods all called from another Transactional methods
    public ServedAddress createServedAddress(ServedAddress servedAddress){
        return servedAddressDAO.createServedAddress(servedAddress)
                .orElseThrow(() -> new EntityCreationException(SERVED_ADDRESS_CREATION_EXCEPTION.formatted(servedAddress)));
    }

    public boolean deleteServedAddress(ServedAddress servedAddress) {
        return servedAddressDAO.deleteServedAddress(servedAddress) == 1;
    }

    public List<ServedAddress> getServedAddresses(Restaurant restaurant) {
        return servedAddressDAO.getServedAddresses(restaurant);
    }
}
