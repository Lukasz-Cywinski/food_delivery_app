package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.infrastructure.database.entity.DeliveryServiceEntity;
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
import static pl.project.util.db.DeliveryServiceInstance.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class DeliveryServiceRepositoryTest extends MyJpaConfiguration {

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
    void thatDeliveryServiceCanBeSavedCorrectly() {

        // given
        DeliveryServiceEntity deliveryService1 = someDeliveryService1();
        DeliveryServiceEntity deliveryService2 = someDeliveryService2();
        DeliveryServiceEntity deliveryService3 = someDeliveryService3();

        String deliveryServiceCode1 = deliveryService1.getDeliveryServiceCode();
        String deliveryServiceCode2 = deliveryService2.getDeliveryServiceCode();
        String deliveryServiceCode3 = deliveryService3.getDeliveryServiceCode();

        //when
        DeliveryServiceEntity deliveryServiceFromDb1 = deliveryServiceJpaRepository.findByDeliveryServiceCode(deliveryServiceCode1).orElseThrow();
        DeliveryServiceEntity deliveryServiceFromDb2 = deliveryServiceJpaRepository.findByDeliveryServiceCode(deliveryServiceCode2).orElseThrow();
        DeliveryServiceEntity deliveryServiceFromDb3 = deliveryServiceJpaRepository.findByDeliveryServiceCode(deliveryServiceCode3).orElseThrow();

        List<DeliveryServiceEntity> deliveryServices = deliveryServiceJpaRepository.findAll();

        //then
        assertThat(deliveryServices)
                .usingRecursiveFieldByFieldElementComparatorOnFields("orderCode")
                .contains(deliveryService1, deliveryService2, deliveryService3);

        assertEquals(deliveryService1.getReceivedDateTime().format(FORMATTER), deliveryServiceFromDb1.getReceivedDateTime().format(FORMATTER));
        assertEquals(deliveryService2.getReceivedDateTime().format(FORMATTER), deliveryServiceFromDb2.getReceivedDateTime().format(FORMATTER));
        assertEquals(deliveryService3.getReceivedDateTime().format(FORMATTER), deliveryServiceFromDb3.getReceivedDateTime().format(FORMATTER));

        if (Objects.nonNull(deliveryServiceFromDb1.getCompletedDateTime())
                && Objects.nonNull(deliveryServiceFromDb2.getCompletedDateTime())
                && Objects.nonNull(deliveryServiceFromDb3.getCompletedDateTime())) {
            assertEquals(deliveryService1.getCompletedDateTime().format(FORMATTER), deliveryServiceFromDb1.getCompletedDateTime().format(FORMATTER));
            assertEquals(deliveryService2.getCompletedDateTime().format(FORMATTER), deliveryServiceFromDb2.getCompletedDateTime().format(FORMATTER));
            assertEquals(deliveryService3.getCompletedDateTime().format(FORMATTER), deliveryServiceFromDb3.getCompletedDateTime().format(FORMATTER));
        }
        assertEquals(3, deliveryServices.size());
    }

    @Test
    void thatDeliveryServiceCompletedTimeIsSavedCorrectly() {
        //given
        DeliveryServiceEntity deliveryService1 = someDeliveryService1();
        String deliveryServiceCode1 = deliveryService1.getDeliveryServiceCode();
        OffsetDateTime completedDateTime = OffsetDateTime.of(2024, 2, 15, 17, 30, 10, 10, ZoneOffset.of("+1"));

        //when
        deliveryServiceJpaRepository.reportCompletedDateTime(completedDateTime, deliveryServiceCode1);
        DeliveryServiceEntity deliveryServiceFromDb1 = deliveryServiceJpaRepository.findByDeliveryServiceCode(deliveryServiceCode1).orElseThrow();

        //then
        assertEquals(completedDateTime.format(FORMATTER), deliveryServiceFromDb1.getCompletedDateTime().format(FORMATTER));
    }
}