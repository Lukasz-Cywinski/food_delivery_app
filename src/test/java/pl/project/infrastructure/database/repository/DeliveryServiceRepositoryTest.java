package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.infrastructure.database.entity.DeliveryServiceEntity;
import pl.project.integration.configuration.MyJpaConfiguration;
import pl.project.infrastructure.database.repository.jpa.DeliveryServiceJpaRepository;

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

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private DeliveryServiceJpaRepository deliveryServiceJpaRepository;

    @Test
    void thatDeliveryServiceCanBeSavedCorrectly() {

        // given
        DeliveryServiceEntity deliveryService1 = someDeliveryService1();
        DeliveryServiceEntity deliveryService2 = someDeliveryService2();
        DeliveryServiceEntity deliveryService3 = someDeliveryService3();

        //when
        deliveryServiceJpaRepository.save(deliveryService1);
        deliveryServiceJpaRepository.save(deliveryService2);
        deliveryServiceJpaRepository.save(deliveryService3);

        DeliveryServiceEntity deliveryServiceFromDb1 = deliveryServiceJpaRepository.findByDeliveryServiceCode(deliveryService1.getDeliveryServiceCode()).orElseThrow();
        DeliveryServiceEntity deliveryServiceFromDb2 = deliveryServiceJpaRepository.findByDeliveryServiceCode(deliveryService2.getDeliveryServiceCode()).orElseThrow();
        DeliveryServiceEntity deliveryServiceFromDb3 = deliveryServiceJpaRepository.findByDeliveryServiceCode(deliveryService3.getDeliveryServiceCode()).orElseThrow();

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
        OffsetDateTime completedDateTime = OffsetDateTime.of(2024, 2, 15, 17, 30, 10, 10, ZoneOffset.of("Z"));

        //when
        deliveryServiceJpaRepository.save(deliveryService1);
        deliveryServiceJpaRepository.reportCompletedDateTime(completedDateTime, deliveryService1.getDeliveryServiceCode());
        DeliveryServiceEntity deliveryServiceFromDb1 = deliveryServiceJpaRepository.findByDeliveryServiceCode(deliveryService1.getDeliveryServiceCode()).orElseThrow();

        //then
        assertEquals(completedDateTime.format(FORMATTER), deliveryServiceFromDb1.getCompletedDateTime().format(FORMATTER));
    }
}