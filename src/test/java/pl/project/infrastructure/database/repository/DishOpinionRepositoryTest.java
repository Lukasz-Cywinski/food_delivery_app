package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pl.project.infrastructure.database.entity.*;
import pl.project.infrastructure.database.repository.jpa.*;
import pl.project.integration.configuration.MyJpaConfiguration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.project.util.db.CustomerInstance.*;
import static pl.project.util.db.DishCategoryInstance.*;
import static pl.project.util.db.DishInstance.*;
import static pl.project.util.db.DishOpinionInstance.*;
import static pl.project.util.db.DishPhotoInstance.*;
import static pl.project.util.db.RestaurantInstance.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class DishOpinionRepositoryTest extends MyJpaConfiguration {

    private DishCategoryJpaRepository dishCategoryJpaRepository;
    private DishOpinionJpaRepository dishOpinionJpaRepository;
    private RestaurantJpaRepository restaurantJpaRepository;
    private DishPhotoJpaRepository dishPhotoJpaRepository;
    private CustomerJpaRepository customerJpaRepository;
    private DishJpaRepository dishJpaRepository;

    private final static DishOpinionEntity dishOpinion1 = someDishOpinion1();
    private final static DishOpinionEntity dishOpinion2 = someDishOpinion2();
    private final static DishOpinionEntity dishOpinion3 = someDishOpinion3();

    @Test
    void thatDishOpinionCanBeSavedCorrectly(){
        //given
        initDataInDb();

        //when
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
        initDataInDb();

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
        initDataInDb();

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
        initDataInDb();

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
        initDataInDb();

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

    void initDataInDb(){
        RestaurantEntity restaurant1 = someRestaurant1();
        RestaurantEntity restaurant2 = someRestaurant2();
        RestaurantEntity restaurant3 = someRestaurant3();

        DishEntity dish1 = someDish1();
        DishEntity dish2 = someDish2();
        DishEntity dish3 = someDish3();

        DishPhotoEntity dishPhoto1 = someDishPhoto1();
        DishPhotoEntity dishPhoto2 = someDishPhoto2();
        DishPhotoEntity dishPhoto3 = someDishPhoto3();

        DishCategoryEntity dishCategory1 = someDishCategory1();
        DishCategoryEntity dishCategory2 = someDishCategory2();
        DishCategoryEntity dishCategory3 = someDishCategory3();

        CustomerEntity customer1 = someCustomer1();
        CustomerEntity customer2 = someCustomer2();
        CustomerEntity customer3 = someCustomer3();

        dish1.setRestaurant(restaurant1);
        dish2.setRestaurant(restaurant2);
        dish3.setRestaurant(restaurant3);

        dish1.setDishPhoto(dishPhoto1);
        dish2.setDishPhoto(dishPhoto2);
        dish3.setDishPhoto(dishPhoto3);

        dish1.setDishCategory(dishCategory1);
        dish2.setDishCategory(dishCategory2);
        dish3.setDishCategory(dishCategory3);

        dishOpinion1.setDish(dish1);
        dishOpinion2.setDish(dish2);
        dishOpinion3.setDish(dish3);

        dishOpinion1.setCustomer(customer1);
        dishOpinion2.setCustomer(customer2);
        dishOpinion3.setCustomer(customer3);

        restaurantJpaRepository.save(restaurant1);
        restaurantJpaRepository.save(restaurant2);
        restaurantJpaRepository.save(restaurant3);

        dishPhotoJpaRepository.save(dishPhoto1);
        dishPhotoJpaRepository.save(dishPhoto2);
        dishPhotoJpaRepository.save(dishPhoto3);

        dishCategoryJpaRepository.save(dishCategory1);
        dishCategoryJpaRepository.save(dishCategory2);
        dishCategoryJpaRepository.save(dishCategory3);

        dishJpaRepository.save(dish1);
        dishJpaRepository.save(dish2);
        dishJpaRepository.save(dish3);

        customerJpaRepository.save(customer1);
        customerJpaRepository.save(customer2);
        customerJpaRepository.save(customer3);

        dishOpinionJpaRepository.save(dishOpinion1);
        dishOpinionJpaRepository.save(dishOpinion2);
        dishOpinionJpaRepository.save(dishOpinion3);
    }
}