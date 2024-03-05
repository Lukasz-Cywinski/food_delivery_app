package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.infrastructure.database.entity.DishCategoryEntity;
import pl.project.infrastructure.database.repository.jpa.*;
import pl.project.infrastructure.security.db.UserRepository;
import pl.project.integration.configuration.Initializer;
import pl.project.integration.configuration.MyJpaConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.project.util.db.DishCategoryInstance.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class DishCategoryRepositoryTest extends MyJpaConfiguration {

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
    void thatDishCategoryCanBeSavedCorrectly(){
        //given
        DishCategoryEntity dishCategory1 = someDishCategory1();
        DishCategoryEntity dishCategory2 = someDishCategory2();
        DishCategoryEntity dishCategory3 = someDishCategory3();

        //when
        List<DishCategoryEntity> dishCategories = dishCategoryJpaRepository.findAll();

        //then
        assertThat(dishCategories)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "dishes")
                .contains(dishCategory1, dishCategory2, dishCategory3);

        assertEquals(3, dishCategories.size());
    }

    @Test
    void thatDescriptionCanBeModifiedCorrectly(){
        //given
        String newDescription = "new description";

        //when
        Integer dishCategoryId = dishCategoryJpaRepository.findAll().getFirst().getId();
        dishCategoryJpaRepository.changeDescription(newDescription, dishCategoryId);

        DishCategoryEntity dishCategoryFromDb = dishCategoryJpaRepository.findById(dishCategoryId).orElseThrow();

        //then
        assertEquals(newDescription, dishCategoryFromDb.getDescription());
    }
}