package pl.project.business.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.project.business.dao.DeliveryAddressDAO;

import java.util.Objects;

@Service
@AllArgsConstructor
public class DeliveryAddressService {

    private final DeliveryAddressDAO deliveryAddressDAO;

    public void method(){
        System.out.println(">>>>>>>>>>>>>>>>>>>>");
        System.out.println(">>>>>>>>>>>>>>>>>>>>");
        System.out.println(Objects.nonNull(deliveryAddressDAO));
        System.out.println(">>>>>>>>>>>>>>>>>>>>");
        System.out.println(">>>>>>>>>>>>>>>>>>>>");
    }

}
