package pl.project.api.dto.mapper.imp;

import org.springframework.stereotype.Component;
import pl.project.api.dto.OrderDTO;
import pl.project.api.dto.mapper.OrderMapper;
import pl.project.domain.formatter.Formatters;
import pl.project.domain.model.Order;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static pl.project.domain.formatter.Formatters.*;

@Component
public class OrderMapperImp implements OrderMapper {
    @Override
    public OrderDTO mapToDTO(Order order) {
        return OrderDTO.builder()
                .orderCode(order.getOrderCode())
                .receivedDateTime(Objects.nonNull(order.getReceivedDateTime()) ?
                        order.getReceivedDateTime().format(DATE_TIME_FORMATTER) : null)
                .completedDateTime(Objects.nonNull(order.getCompletedDateTime()) ?
                        order.getCompletedDateTime().format(DATE_TIME_FORMATTER) : null)
                .build();
    }
}
