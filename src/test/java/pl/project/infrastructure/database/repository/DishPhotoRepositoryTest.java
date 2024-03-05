package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.infrastructure.database.entity.DishPhotoEntity;
import pl.project.infrastructure.database.repository.jpa.*;
import pl.project.infrastructure.security.db.UserRepository;
import pl.project.integration.configuration.Initializer;
import pl.project.integration.configuration.MyJpaConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.project.util.db.DishPhotoInstance.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class DishPhotoRepositoryTest extends MyJpaConfiguration {

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
    void initializeDbData() {
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
    void thatDishPhotoCanBeSavedCorrectly(){
        //given
        DishPhotoEntity dishPhoto1 = someDishPhoto1();
        DishPhotoEntity dishPhoto2 = someDishPhoto2();
        DishPhotoEntity dishPhoto3 = someDishPhoto3();

        //when
        List<DishPhotoEntity> dishCategories = dishPhotoJpaRepository.findAll();

        //then
        assertThat(dishCategories)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "dishes")
                .contains(dishPhoto1, dishPhoto2, dishPhoto3);

        assertEquals(3, dishCategories.size());
    }

    @Test
    void thatDishPhotoCanBeModifiedCorrectly(){
        //given
        Integer dishPhotoId = initializer.SAVED_DISH_PHOTOS.getFirst().getId();
        String newName = "new name";

        //when
        dishPhotoJpaRepository.changePhotoName(newName, dishPhotoId);
        DishPhotoEntity dishPhotoFromDb = dishPhotoJpaRepository.findById(dishPhotoId).orElseThrow();

        //then
        assertEquals(newName, dishPhotoFromDb.getName());
    }

    @Test
    void thatDishPhotoCanBeDeletedCorrectly(){
        //given
        DishPhotoEntity dishPhoto1 = initializer.SAVED_DISH_PHOTOS.getFirst();

        //when
        dishPhotoJpaRepository.delete(dishPhoto1);
        List<DishPhotoEntity> photos = dishPhotoJpaRepository.findAll();

        //then
        assertEquals(2, photos.size());
    }
}