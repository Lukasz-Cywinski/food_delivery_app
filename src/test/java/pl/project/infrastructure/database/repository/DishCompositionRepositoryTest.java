package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.infrastructure.database.entity.DishCompositionEntity;
import pl.project.infrastructure.database.entity.OrderEntity;
import pl.project.infrastructure.database.repository.jpa.*;
import pl.project.infrastructure.security.db.UserRepository;
import pl.project.integration.configuration.Initializer;
import pl.project.integration.configuration.MyJpaConfiguration;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.project.util.db.DishCompositionInstance.*;
import static pl.project.util.db.RestaurantInstance.someRestaurant1;
import static pl.project.util.db.RestaurantInstance.someRestaurant2;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class DishCompositionRepositoryTest extends MyJpaConfiguration {

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
        DishCompositionEntity dishComposition1 = someDishComposition1();
        DishCompositionEntity dishComposition2 = someDishComposition2();
        DishCompositionEntity dishComposition3 = someDishComposition3();

        //when
        List<DishCompositionEntity> dishCompositions = dishCompositionJpaRepository.findAll();

        //then
        assertThat(dishCompositions)
                .usingRecursiveFieldByFieldElementComparatorOnFields("quantity")
                .contains(dishComposition1, dishComposition2, dishComposition3);

        assertEquals(dishComposition1.getDish().getDishCode(), dishCompositions.getFirst().getDish().getDishCode());
        assertEquals(dishComposition1.getOrder().getOrderCode(), dishCompositions.getFirst().getOrder().getOrderCode());
    }

    @Test
    void thatDishCompositionByOrderSelectedCorrectly(){
        //given
        //when
        List<DishCompositionEntity> dishCompositions = dishCompositionJpaRepository.findAll();
        OrderEntity order1 = dishCompositions.getFirst().getOrder();
        List<DishCompositionEntity> dishCompositionsByOrder = dishCompositionJpaRepository.findByOrder(order1);

        //then
        assertThat(dishCompositionsByOrder)
                .satisfiesExactly(dishComposition -> assertThat(dishComposition.getOrder().getOrderCode()).isEqualTo(order1.getOrderCode()));
    }

    @Test
    void isUncompletedOrdersForRestaurantIsSelectedCorrectly(){
        //given
        //when
        Set<OrderEntity> noCompletedOrders = dishCompositionJpaRepository.findActiveOrdersForRestaurant(
                someRestaurant1().getRestaurantCode());
        Set<OrderEntity> completedOrders = dishCompositionJpaRepository.findActiveOrdersForRestaurant(
                someRestaurant2().getRestaurantCode());

        //then
        assertEquals(1, noCompletedOrders.size());
        assertEquals(0, completedOrders.size());
    }
}