package pl.project.api.dto.mapper.imp;

import org.springframework.stereotype.Component;
import pl.project.api.dto.OrderDTO;
import pl.project.api.dto.mapper.OrderMapper;
import pl.project.domain.model.Order;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public class OrderMapperImp implements OrderMapper {
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Override
    public OrderDTO mapToDTO(Order order) {
        return OrderDTO.builder()
                .orderCode(order.getOrderCode())
                .receivedDateTime(Objects.nonNull(order.getReceivedDateTime()) ?
                        order.getReceivedDateTime().format(FORMATTER) : null)
                .completedDateTime(Objects.nonNull(order.getCompletedDateTime()) ?
                        order.getCompletedDateTime().format(FORMATTER) : null)
                .build();
    }
}
