package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.infrastructure.database.entity.CustomerEntity;
import pl.project.infrastructure.database.entity.DeliveryServiceEntity;
import pl.project.infrastructure.database.entity.OrderEntity;
import pl.project.integration.configuration.MyJpaConfiguration;
import pl.project.infrastructure.database.repository.jpa.CustomerJpaRepository;
import pl.project.infrastructure.database.repository.jpa.DeliveryServiceJpaRepository;
import pl.project.infrastructure.database.repository.jpa.OrderJpaRepository;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.project.util.db.CustomerInstance.*;
import static pl.project.util.db.DeliveryServiceInstance.*;
import static pl.project.util.db.OrderInstance.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class OrderRepositoryTest extends MyJpaConfiguration {

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private OrderJpaRepository orderJpaRepository;
    private DeliveryServiceJpaRepository deliveryServiceJpaRepository;
    private CustomerJpaRepository customerJpaRepository;

    @Test
    void thatOrderCanBeSavedCorrectly() {

        // given
        OrderEntity order1 = someOrder1();
        OrderEntity order2 = someOrder2();
        OrderEntity order3 = someOrder3();

        DeliveryServiceEntity deliveryService1 = someDeliveryService1();
        DeliveryServiceEntity deliveryService2 = someDeliveryService2();
        DeliveryServiceEntity deliveryService3 = someDeliveryService3();

        CustomerEntity customer1 = someCustomer1();
        CustomerEntity customer2 = someCustomer2();
        CustomerEntity customer3 = someCustomer3();

        order1.setDeliveryService(deliveryService1);
        order2.setDeliveryService(deliveryService2);
        order3.setDeliveryService(deliveryService3);

        order1.setCustomer(customer1);
        order2.setCustomer(customer2);
        order3.setCustomer(customer3);

        //when
        deliveryServiceJpaRepository.save(deliveryService1);
        deliveryServiceJpaRepository.save(deliveryService2);
        deliveryServiceJpaRepository.save(deliveryService3);

        customerJpaRepository.save(customer1);
        customerJpaRepository.save(customer2);
        customerJpaRepository.save(customer3);

        orderJpaRepository.save(order1);
        orderJpaRepository.save(order2);
        orderJpaRepository.save(order3);

        OrderEntity orderFromDb1 = orderJpaRepository.findByOrderCode(order1.getOrderCode()).orElseThrow();
        OrderEntity orderFromDb2 = orderJpaRepository.findByOrderCode(order2.getOrderCode()).orElseThrow();
        OrderEntity orderFromDb3 = orderJpaRepository.findByOrderCode(order3.getOrderCode()).orElseThrow();

        List<OrderEntity> orders = orderJpaRepository.findAll();

        //then
        assertThat(orders)
                .usingRecursiveFieldByFieldElementComparatorOnFields("orderCode")
                .contains(order1, order2, order3);

        assertEquals(order1.getReceivedDateTime().format(FORMATTER), orderFromDb1.getReceivedDateTime().format(FORMATTER));
        assertEquals(order2.getReceivedDateTime().format(FORMATTER), orderFromDb2.getReceivedDateTime().format(FORMATTER));
        assertEquals(order3.getReceivedDateTime().format(FORMATTER), orderFromDb3.getReceivedDateTime().format(FORMATTER));

        if (Objects.nonNull(orderFromDb1.getCompletedDateTime())
                && Objects.nonNull(orderFromDb2.getCompletedDateTime())
                && Objects.nonNull(orderFromDb3.getCompletedDateTime())) {
            assertEquals(order1.getCompletedDateTime().format(FORMATTER), orderFromDb1.getCompletedDateTime().format(FORMATTER));
            assertEquals(order2.getCompletedDateTime().format(FORMATTER), orderFromDb2.getCompletedDateTime().format(FORMATTER));
            assertEquals(order3.getCompletedDateTime().format(FORMATTER), orderFromDb3.getCompletedDateTime().format(FORMATTER));
        }
        assertEquals(3, orders.size());
    }

    @Test
    void thatOrderCompletedTimeIsSavedCorrectly() {
        // given
        OrderEntity order1 = someOrder1();
        DeliveryServiceEntity deliveryService1 = someDeliveryService1();
        CustomerEntity customer1 = someCustomer1();
        order1.setDeliveryService(deliveryService1);

        order1.setCustomer(customer1);
        OffsetDateTime completedDateTime = OffsetDateTime.of(2024, 2, 15, 17, 30, 10, 10, ZoneOffset.of("Z"));

        //when
        deliveryServiceJpaRepository.save(deliveryService1);
        customerJpaRepository.save(customer1);

        orderJpaRepository.save(order1);
        orderJpaRepository.reportCompletedDateTime(completedDateTime, order1.getOrderCode());
        OrderEntity orderFromDb1 = orderJpaRepository.findByOrderCode(order1.getOrderCode()).orElseThrow();

        //then
        assertEquals(completedDateTime.format(FORMATTER), orderFromDb1.getCompletedDateTime().format(FORMATTER));
    }

}