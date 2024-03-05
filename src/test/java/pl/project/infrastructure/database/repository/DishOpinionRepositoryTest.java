package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pl.project.infrastructure.database.entity.CustomerEntity;
import pl.project.infrastructure.database.entity.DishEntity;
import pl.project.infrastructure.database.entity.DishOpinionEntity;
import pl.project.infrastructure.database.repository.jpa.*;
import pl.project.infrastructure.security.db.UserRepository;
import pl.project.integration.configuration.Initializer;
import pl.project.integration.configuration.MyJpaConfiguration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.project.util.db.DishOpinionInstance.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class DishOpinionRepositoryTest extends MyJpaConfiguration {

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
    void thatDishOpinionCanBeSavedCorrectly(){
        //given
        DishOpinionEntity dishOpinion1 = someDishOpinion1();
        DishOpinionEntity dishOpinion2 = someDishOpinion2();
        DishOpinionEntity dishOpinion3 = someDishOpinion3();

        // when
        List<DishOpinionEntity> opinions = dishOpinionJpaRepository.findAll();

        //then
        assertThat(opinions)
                .usingRecursiveFieldByFieldElementComparatorOnFields("opinion", "productEvaluation")
                .contains(dishOpinion1, dishOpinion2, dishOpinion3);

        assertEquals(opinions.getFirst().getDish().getDishCode(), dishOpinion1.getDish().getDishCode());
        assertEquals(opinions.getFirst().getCustomer().getEmail(), dishOpinion1.getCustomer().getEmail());

        assertEquals(3, opinions.size());
    }

    @Test
    void thatOpinionsByDishSelectedCorrectly(){
        //given
        DishOpinionEntity dishOpinion1 = someDishOpinion1();

        //when
        List<DishOpinionEntity> allOpinions = dishOpinionJpaRepository.findAll();
        DishEntity dish1 = allOpinions.getFirst().getDish();
        Sort sort = Sort.by("productEvaluation");
        Pageable pageable = PageRequest.of(0, 3, sort);
        Page<DishOpinionEntity> page = dishOpinionJpaRepository.findByDish(dish1, pageable);
        List<DishOpinionEntity> opinionsByDish = page.getContent();

        //then
        assertThat(opinionsByDish)
                .usingRecursiveFieldByFieldElementComparatorOnFields("opinion", "productEvaluation")
                .contains(dishOpinion1);

        assertEquals(opinionsByDish.getFirst().getDish().getDishCode(), dishOpinion1.getDish().getDishCode());

        assertEquals(1, opinionsByDish.size());
    }

    @Test
    void thatOpinionsByCustomersSelectedCorrectly(){
        //given
        DishOpinionEntity dishOpinion1 = someDishOpinion1();

        //when
        List<DishOpinionEntity> allOpinions = dishOpinionJpaRepository.findAll();
        CustomerEntity customer1 = allOpinions.getFirst().getCustomer();
        List<DishOpinionEntity> opinionsByCustomer = dishOpinionJpaRepository.findByCustomer(customer1);

        //then
        assertThat(opinionsByCustomer)
                .usingRecursiveFieldByFieldElementComparatorOnFields("opinion", "productEvaluation")
                .contains(dishOpinion1);

        assertEquals(opinionsByCustomer.getFirst().getCustomer().getEmail(), dishOpinion1.getCustomer().getEmail());

        assertEquals(1, opinionsByCustomer.size());
    }

    @Test
    void thatOpinionsByEvaluationSelectedCorrectly(){
        //given

        //when
        BigDecimal from = BigDecimal.ZERO;
        BigDecimal to = BigDecimal.TWO;
        Sort sort = Sort.by("productEvaluation");
        Pageable pageable = PageRequest.of(0, 3, sort);
        Page<DishOpinionEntity> page = dishOpinionJpaRepository.findByEvaluationRange(from, to, pageable);
        List<DishOpinionEntity> opinionsByEvaluationRange = page.getContent();

        //then
        assertThat(opinionsByEvaluationRange)
                .satisfiesExactly(opinion -> assertThat(opinion.getProductEvaluation()).isBetween(from, to));
    }

    @Test
    void thatAverageEvaluationCalculatedCorrectly(){
        //given

        //when
        List<DishOpinionEntity> allOpinions = dishOpinionJpaRepository.findAll();
        DishEntity dish1 = allOpinions.getFirst().getDish();

        BigDecimal averageEvaluationFromDb = dishOpinionJpaRepository.getAverageDishEvaluation(dish1)
                .orElseThrow()
                .setScale(1, RoundingMode.HALF_UP);


        BigDecimal opinionsSum = allOpinions.stream()
                .filter(dishOpinion -> dishOpinion.getDish().getDishCode().equals(dish1.getDishCode()))
                .map(DishOpinionEntity::getProductEvaluation)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal opinions = BigDecimal.valueOf(allOpinions.stream()
                .filter(dishOpinion -> dishOpinion.getDish().getDishCode().equals(dish1.getDishCode()))
                .count());

        BigDecimal averageEvaluationCalculated = opinionsSum.divide(opinions, RoundingMode.HALF_UP).setScale(1, RoundingMode.HALF_UP);

        //then
        assertEquals(averageEvaluationCalculated, averageEvaluationFromDb);
    }
}