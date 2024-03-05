package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.infrastructure.database.entity.OrderEntity;
import pl.project.infrastructure.database.repository.jpa.*;
import pl.project.infrastructure.security.db.UserRepository;
import pl.project.integration.configuration.Initializer;
import pl.project.integration.configuration.MyJpaConfiguration;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.project.util.db.OrderInstance.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class OrderRepositoryTest extends MyJpaConfiguration {

    private ServedAddressJpaRepository servedAddressJpaRepository;
    private RestaurantOwnerJpaRepository restaurantOwnerJpaRepository;
    private RestaurantJpaRepository restaurantJpaRepository;
    private DishPhotoJpaRepository dishPhotoJpaRepository;
    private DishCategoryJpaRepository dishCategoryJpaRepository;
    private DishJpaRepository dishJpaRepository;
    private DishOpinionJpaRepository dishOpinionJpaRepository;
    private DishCompositionJpaRepository dishCompositionJpaRepository;
    private OrderJpaRepository orderJpaRepository;
    private CustomerJpaRepository customerJpaRepository;
    private DeliveryServiceJpaRepository deliveryServiceJpaRepository;
    private DeliveryAddressJpaRepository deliveryAddressJpaRepository;
    private DeliveryManJpaRepository deliveryManJpaRepository;
    private UserRepository userRepository;

    private final Initializer initializer = new Initializer();
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BeforeEach
    void initializeDbData(){
        initializer.setServedAddressJpaRepository(servedAddressJpaRepository);
        initializer.setRestaurantOwnerJpaRepository(restaurantOwnerJpaRepository);
        initializer.setRestaurantJpaRepository(restaurantJpaRepository);
        initializer.setDishPhotoJpaRepository(dishPhotoJpaRepository);
        initializer.setDishCategoryJpaRepository(dishCategoryJpaRepository);
        initializer.setDishJpaRepository(dishJpaRepository);
        initializer.setDishOpinionJpaRepository(dishOpinionJpaRepository);
        initializer.setDishCompositionJpaRepository(dishCompositionJpaRepository);
        initializer.setOrderJpaRepository(orderJpaRepository);
        initializer.setCustomerJpaRepository(customerJpaRepository);
        initializer.setDeliveryServiceJpaRepository(deliveryServiceJpaRepository);
        initializer.setDeliveryAddressJpaRepository(deliveryAddressJpaRepository);
        initializer.setDeliveryManJpaRepository(deliveryManJpaRepository);
        initializer.setUserRepository(userRepository);

        initializer.initializedBData();
    }

    @Test
    void thatOrderCanBeSavedCorrectly() {

        // given
        OrderEntity order1 = someOrder1();
        OrderEntity order2 = someOrder2();
        OrderEntity order3 = someOrder3();

        String orderCode1 = order1.getOrderCode();
        String orderCode2 = order1.getOrderCode();
        String orderCode3 = order1.getOrderCode();

        //when
        OrderEntity orderFromDb1 = orderJpaRepository.findByOrderCode(orderCode1).orElseThrow();
        OrderEntity orderFromDb2 = orderJpaRepository.findByOrderCode(orderCode2).orElseThrow();
        OrderEntity orderFromDb3 = orderJpaRepository.findByOrderCode(orderCode3).orElseThrow();

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
        String orderCode = someOrder1().getOrderCode();
        OffsetDateTime completedDateTime = OffsetDateTime.
                of(2024, 2, 15, 17, 30, 10, 10, ZoneOffset.ofHours(1));

        //when
        orderJpaRepository.reportCompletedDateTime(completedDateTime, orderCode);
        OrderEntity orderFromDb1 = orderJpaRepository.findByOrderCode(orderCode).orElseThrow();

        //then
        assertEquals(completedDateTime.format(FORMATTER), orderFromDb1.getCompletedDateTime().format(FORMATTER));
    }
}