package pl.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.infrastructure.database.entity.*;
import pl.project.infrastructure.security.db.UserRepository;
import pl.project.integration.configuration.MyJpaConfiguration;
import pl.project.infrastructure.database.repository.jpa.*;
import pl.project.util.db.DishCompositionInstance;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.project.util.db.CustomerInstance.*;
import static pl.project.util.db.DeliveryServiceInstance.*;
import static pl.project.util.db.DishCategoryInstance.*;
import static pl.project.util.db.DishInstance.*;
import static pl.project.util.db.DishPhotoInstance.*;
import static pl.project.util.db.OrderInstance.*;
import static pl.project.util.db.RestaurantInstance.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class DishCompositionRepositoryTest extends MyJpaConfiguration {

    private DeliveryServiceJpaRepository deliveryServiceJpaRepository;
    private DishCompositionJpaRepository dishCompositionJpaRepository;
    private DishCategoryJpaRepository dishCategoryJpaRepository;
    private RestaurantJpaRepository restaurantJpaRepository;
    private DishPhotoJpaRepository dishPhotoJpaRepository;
    private CustomerJpaRepository customerJpaRepository;
    private OrderJpaRepository orderJpaRepository;
    private DishJpaRepository dishJpaRepository;
    private UserRepository userRepository;

    private final DishCompositionEntity dishComposition1 = DishCompositionInstance.someDishComposition1();
    private final DishCompositionEntity dishComposition2 = DishCompositionInstance.someDishComposition2();
    private final DishCompositionEntity dishComposition3 = DishCompositionInstance.someDishComposition3();

    @Test
    void thatDishOpinionCanBeSavedCorrectly(){
        //given
        initDataInDb();

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
        initDataInDb();

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
        initDataInDb();

        //when
        Set<OrderEntity> noCompletedOrders = dishCompositionJpaRepository.findActiveOrdersForRestaurant(
                restaurantJpaRepository.findByRestaurantCode(someRestaurant1().getRestaurantCode()).orElseThrow());
        Set<OrderEntity> completedOrders = dishCompositionJpaRepository.findActiveOrdersForRestaurant(
                restaurantJpaRepository.findByRestaurantCode(someRestaurant2().getRestaurantCode()).orElseThrow());

        //then
        assertEquals(1, noCompletedOrders.size());
        assertEquals(0, completedOrders.size());
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

        DeliveryServiceEntity deliveryService1 = someDeliveryService1();
        DeliveryServiceEntity deliveryService2 = someDeliveryService2();
        DeliveryServiceEntity deliveryService3 = someDeliveryService3();

        OrderEntity order1 = someOrder1();
        OrderEntity order2 = someOrder2();
        OrderEntity order3 = someOrder3();

        dish1.setRestaurant(restaurant1);
        dish2.setRestaurant(restaurant2);
        dish3.setRestaurant(restaurant3);

        dish1.setDishPhoto(dishPhoto1);
        dish2.setDishPhoto(dishPhoto2);
        dish3.setDishPhoto(dishPhoto3);

        dish1.setDishCategory(dishCategory1);
        dish2.setDishCategory(dishCategory2);
        dish3.setDishCategory(dishCategory3);

        order1.setCustomer(customer1);
        order2.setCustomer(customer2);
        order3.setCustomer(customer3);

        order1.setDeliveryService(deliveryService1);
        order2.setDeliveryService(deliveryService2);
        order3.setDeliveryService(deliveryService3);

        //simplified
        userRepository.save(customer1.getUser());
        userRepository.save(customer2.getUser());
        userRepository.save(customer3.getUser());

        userRepository.save(deliveryService1.getDeliveryMan().getUser());
        userRepository.save(deliveryService2.getDeliveryMan().getUser());
        userRepository.save(deliveryService3.getDeliveryMan().getUser());

        userRepository.save(restaurant1.getRestaurantOwner().getUser());
        userRepository.save(restaurant2.getRestaurantOwner().getUser());
        userRepository.save(restaurant3.getRestaurantOwner().getUser());
        //simplified

        dishComposition1.setDish(dish1);
        dishComposition2.setDish(dish2);
        dishComposition3.setDish(dish3);

        dishComposition1.setOrder(order1);
        dishComposition2.setOrder(order2);
        dishComposition3.setOrder(order3);

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

        deliveryServiceJpaRepository.save(deliveryService1);
        deliveryServiceJpaRepository.save(deliveryService2);
        deliveryServiceJpaRepository.save(deliveryService3);

        orderJpaRepository.save(order1);
        orderJpaRepository.save(order2);
        orderJpaRepository.save(order3);

        dishCompositionJpaRepository.save(dishComposition1);
        dishCompositionJpaRepository.save(dishComposition2);
        dishCompositionJpaRepository.save(dishComposition3);
    }

}