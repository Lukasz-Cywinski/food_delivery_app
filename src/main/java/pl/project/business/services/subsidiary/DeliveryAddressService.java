package pl.project.business.services.subsidiary;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.project.business.dao.DeliveryAddressDAO;
import pl.project.domain.model.Customer;
import pl.project.domain.model.DeliveryAddress;

@Service
@AllArgsConstructor
public class DeliveryAddressService {

    private final DeliveryAddressDAO deliveryAddressDAO;

    // without transactional -> methods all called from another Transactional methods
//    public boolean modifyCustomerDeliveryAddress(DeliveryAddress deliveryAddress, Customer customer){
//        Integer addressId = customer.getDeliveryAddress().getId();
//        return (deliveryAddressDAO.changeDeliveryAddressCity(deliveryAddress.getCity(), addressId)
//                + deliveryAddressDAO.changeDeliveryAddressStreet(deliveryAddress.getStreet(), addressId)
//                + deliveryAddressDAO.changeDeliveryAddressPostalCode(deliveryAddress.getPostalCode(), addressId))
//                > 0;
//    }

}
