package pl.project.api.dto.mapper.imp;

import org.springframework.stereotype.Component;
import pl.project.api.dto.OrderDetailsDTO;
import pl.project.api.dto.mapper.OrderDetailsMapper;
import pl.project.domain.model.Order;

import java.util.Objects;

import static pl.project.domain.formatter.Formatters.DATE_TIME_FORMATTER;

@Component
public class OrderDetailsMapperImp implements OrderDetailsMapper {
    @Override
    public OrderDetailsDTO mapToDTO(Order domainObj) {
        return OrderDetailsDTO.builder()
                .orderCode(domainObj.getOrderCode())
                .receivedDateTime(Objects.isNull(domainObj.getReceivedDateTime()) ?
                        "" : domainObj.getReceivedDateTime().format(DATE_TIME_FORMATTER))
                .completedDateTime(Objects.isNull(domainObj.getCompletedDateTime()) ?
                        "" : domainObj.getCompletedDateTime().format(DATE_TIME_FORMATTER))
                .customerEmail(domainObj.getCustomer().getEmail())
                .customerPhoneNumber(domainObj.getCustomer().getPhoneNumber())
                .customerCity(domainObj.getCustomer().getDeliveryAddress().getCity())
                .customerStreet(domainObj.getCustomer().getDeliveryAddress().getStreet())
                .customerPostalCode(domainObj.getCustomer().getDeliveryAddress().getPostalCode())
                .deliveryServiceCode(domainObj.getDeliveryService().getDeliveryServiceCode())
                .build();
    }
}
