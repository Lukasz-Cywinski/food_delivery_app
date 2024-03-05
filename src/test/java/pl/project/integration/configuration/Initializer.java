package pl.project.integration.configuration;

import lombok.Setter;
import pl.project.infrastructure.database.entity.*;
import pl.project.infrastructure.database.repository.jpa.*;
import pl.project.infrastructure.security.db.UserEntity;
import pl.project.infrastructure.security.db.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static pl.project.util.db.CustomerInstance.*;
import static pl.project.util.db.DeliveryAddressInstance.*;
import static pl.project.util.db.DeliveryManInstance.*;
import static pl.project.util.db.DeliveryServiceInstance.*;
import static pl.project.util.db.DishCategoryInstance.*;
import static pl.project.util.db.DishCompositionInstance.*;
import static pl.project.util.db.DishInstance.*;
import static pl.project.util.db.DishOpinionInstance.*;
import static pl.project.util.db.DishPhotoInstance.*;
import static pl.project.util.db.OrderInstance.*;
import static pl.project.util.db.RestaurantInstance.*;
import static pl.project.util.db.RestaurantOwnerInstance.*;
import static pl.project.util.db.ServedAddressInstance.*;
import static pl.project.util.db.UserInstance.*;

@Setter
public class Initializer {

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

    public final List<UserEntity> SAVED_CUSTOMER_USERS = new ArrayList<>();
    public final List<UserEntity> SAVED_RESTAURANT_OWNER_USERS = new ArrayList<>();
    public final List<UserEntity> SAVED_DELIVERY_MAN_USERS = new ArrayList<>();
    public final List<DeliveryAddressEntity> SAVED_DELIVERY_ADDRESSES = new ArrayList<>();
    public final List<DeliveryManEntity> SAVED_DELIVERY_MEN = new ArrayList<>();
    public final List<RestaurantOwnerEntity> SAVED_RESTAURANT_OWNERS = new ArrayList<>();
    public final List<DishPhotoEntity> SAVED_DISH_PHOTOS = new ArrayList<>();
    public final List<DishCategoryEntity> SAVED_DISH_CATEGORIES = new ArrayList<>();
    public final List<RestaurantEntity> SAVED_RESTAURANTS = new ArrayList<>();
    public final List<ServedAddressEntity> SAVED_SERVED_ADDRESSES = new ArrayList<>();
    public final List<DishEntity> SAVED_DISHES = new ArrayList<>();
    public final List<CustomerEntity> SAVED_CUSTOMERS = new ArrayList<>();
    public final List<DeliveryServiceEntity> SAVED_DELIVERY_SERVICES = new ArrayList<>();
    public final List<OrderEntity> SAVED_ORDERS = new ArrayList<>();
    public final List<DishOpinionEntity> SAVED_DISH_OPINIONS = new ArrayList<>();
    public final List<DishCompositionEntity> SAVED_DISH_COMPOSITIONS = new ArrayList<>();

    public void initializedBData(){
        SAVED_CUSTOMER_USERS.add(userRepository.save(someUserCustomer1()));
        SAVED_CUSTOMER_USERS.add(userRepository.save(someUserCustomer2()));
        SAVED_CUSTOMER_USERS.add(userRepository.save(someUserCustomer3()));

        SAVED_RESTAURANT_OWNER_USERS.add(userRepository.save(someUserRestaurantOwner1()));
        SAVED_RESTAURANT_OWNER_USERS.add(userRepository.save(someUserRestaurantOwner2()));
        SAVED_RESTAURANT_OWNER_USERS.add(userRepository.save(someUserRestaurantOwner3()));

        SAVED_DELIVERY_MAN_USERS.add(userRepository.save(someUserDeliveryMan1()));
        SAVED_DELIVERY_MAN_USERS.add(userRepository.save(someUserDeliveryMan2()));
        SAVED_DELIVERY_MAN_USERS.add(userRepository.save(someUserDeliveryMan3()));

        SAVED_DELIVERY_ADDRESSES.add(deliveryAddressJpaRepository.save(someDeliveryAddress1()));
        SAVED_DELIVERY_ADDRESSES.add(deliveryAddressJpaRepository.save(someDeliveryAddress2()));
        SAVED_DELIVERY_ADDRESSES.add(deliveryAddressJpaRepository.save(someDeliveryAddress3()));

        SAVED_DISH_PHOTOS.add(dishPhotoJpaRepository.save(someDishPhoto1()));
        SAVED_DISH_PHOTOS.add(dishPhotoJpaRepository.save(someDishPhoto2()));
        SAVED_DISH_PHOTOS.add(dishPhotoJpaRepository.save(someDishPhoto3()));

        SAVED_DISH_CATEGORIES.add(dishCategoryJpaRepository.save(someDishCategory1()));
        SAVED_DISH_CATEGORIES.add(dishCategoryJpaRepository.save(someDishCategory2()));
        SAVED_DISH_CATEGORIES.add(dishCategoryJpaRepository.save(someDishCategory3()));

        DeliveryManEntity deliveryMan1 = someDeliveryMan1();
        DeliveryManEntity deliveryMan2 = someDeliveryMan2();
        DeliveryManEntity deliveryMan3 = someDeliveryMan3();
        deliveryMan1.setUser(SAVED_DELIVERY_MAN_USERS.get(0));
        deliveryMan2.setUser(SAVED_DELIVERY_MAN_USERS.get(1));
        deliveryMan3.setUser(SAVED_DELIVERY_MAN_USERS.get(2));

        SAVED_DELIVERY_MEN.add(deliveryManJpaRepository.save(deliveryMan1));
        SAVED_DELIVERY_MEN.add(deliveryManJpaRepository.save(deliveryMan2));
        SAVED_DELIVERY_MEN.add(deliveryManJpaRepository.save(deliveryMan3));

        RestaurantOwnerEntity restaurantOwner1 = someRestaurantOwner1();
        RestaurantOwnerEntity restaurantOwner2 = someRestaurantOwner2();
        RestaurantOwnerEntity restaurantOwner3 = someRestaurantOwner3();
        restaurantOwner1.setUser(SAVED_RESTAURANT_OWNER_USERS.get(0));
        restaurantOwner2.setUser(SAVED_RESTAURANT_OWNER_USERS.get(1));
        restaurantOwner3.setUser(SAVED_RESTAURANT_OWNER_USERS.get(2));

        SAVED_RESTAURANT_OWNERS.add(restaurantOwnerJpaRepository.save(restaurantOwner1));
        SAVED_RESTAURANT_OWNERS.add(restaurantOwnerJpaRepository.save(restaurantOwner2));
        SAVED_RESTAURANT_OWNERS.add(restaurantOwnerJpaRepository.save(restaurantOwner3));

        RestaurantEntity restaurant1 = someRestaurant1();
        RestaurantEntity restaurant2 = someRestaurant2();
        RestaurantEntity restaurant3 = someRestaurant3();
        restaurant1.setRestaurantOwner(SAVED_RESTAURANT_OWNERS.get(0));
        restaurant2.setRestaurantOwner(SAVED_RESTAURANT_OWNERS.get(1));
        restaurant3.setRestaurantOwner(SAVED_RESTAURANT_OWNERS.get(2));

        SAVED_RESTAURANTS.add(restaurantJpaRepository.save(restaurant1));
        SAVED_RESTAURANTS.add(restaurantJpaRepository.save(restaurant2));
        SAVED_RESTAURANTS.add(restaurantJpaRepository.save(restaurant3));

        ServedAddressEntity servedAddress1 = someServedAddress1();
        ServedAddressEntity servedAddress2 = someServedAddress2();
        ServedAddressEntity servedAddress3 = someServedAddress3();
        servedAddress1.setRestaurant(SAVED_RESTAURANTS.get(0));
        servedAddress3.setRestaurant(SAVED_RESTAURANTS.get(2));
        servedAddress2.setRestaurant(SAVED_RESTAURANTS.get(1));

        SAVED_SERVED_ADDRESSES.add(servedAddressJpaRepository.save(servedAddress1));
        SAVED_SERVED_ADDRESSES.add(servedAddressJpaRepository.save(servedAddress2));
        SAVED_SERVED_ADDRESSES.add(servedAddressJpaRepository.save(servedAddress3));

        DishEntity dish1 = someDish1();
        DishEntity dish2 = someDish2();
        DishEntity dish3 = someDish3();
        dish1.setRestaurant(SAVED_RESTAURANTS.get(0));
        dish1.setDishPhoto(SAVED_DISH_PHOTOS.get(0));
        dish1.setDishCategory(SAVED_DISH_CATEGORIES.get(0));
        dish2.setRestaurant(SAVED_RESTAURANTS.get(1));
        dish2.setDishPhoto(SAVED_DISH_PHOTOS.get(1));
        dish2.setDishCategory(SAVED_DISH_CATEGORIES.get(1));
        dish3.setRestaurant(SAVED_RESTAURANTS.get(2));
        dish3.setDishPhoto(SAVED_DISH_PHOTOS.get(2));
        dish3.setDishCategory(SAVED_DISH_CATEGORIES.get(2));

        SAVED_DISHES.add(dishJpaRepository.save(dish1));
        SAVED_DISHES.add(dishJpaRepository.save(dish2));
        SAVED_DISHES.add(dishJpaRepository.save(dish3));

        CustomerEntity customer1 = someCustomer1();
        CustomerEntity customer2 = someCustomer2();
        CustomerEntity customer3 = someCustomer3();
        customer1.setDeliveryAddress(SAVED_DELIVERY_ADDRESSES.get(0));
        customer1.setUser(SAVED_CUSTOMER_USERS.get(0));
        customer2.setDeliveryAddress(SAVED_DELIVERY_ADDRESSES.get(1));
        customer2.setUser(SAVED_CUSTOMER_USERS.get(1));
        customer3.setDeliveryAddress(SAVED_DELIVERY_ADDRESSES.get(2));
        customer3.setUser(SAVED_CUSTOMER_USERS.get(2));

        SAVED_CUSTOMERS.add(customerJpaRepository.save(customer1));
        SAVED_CUSTOMERS.add(customerJpaRepository.save(customer2));
        SAVED_CUSTOMERS.add(customerJpaRepository.save(customer3));

        DeliveryServiceEntity deliveryService1 = someDeliveryService1();
        DeliveryServiceEntity deliveryService2 = someDeliveryService2();
        DeliveryServiceEntity deliveryService3 = someDeliveryService3();
        deliveryService1.setDeliveryMan(SAVED_DELIVERY_MEN.get(0));
        deliveryService2.setDeliveryMan(SAVED_DELIVERY_MEN.get(1));
        deliveryService3.setDeliveryMan(SAVED_DELIVERY_MEN.get(2));

        SAVED_DELIVERY_SERVICES.add(deliveryServiceJpaRepository.save(deliveryService1));
        SAVED_DELIVERY_SERVICES.add(deliveryServiceJpaRepository.save(deliveryService2));
        SAVED_DELIVERY_SERVICES.add(deliveryServiceJpaRepository.save(deliveryService3));

        OrderEntity order1 = someOrder1();
        OrderEntity order2 = someOrder2();
        OrderEntity order3 = someOrder3();
        order1.setCustomer(SAVED_CUSTOMERS.get(0));
        order1.setDeliveryService(SAVED_DELIVERY_SERVICES.get(0));
        order2.setCustomer(SAVED_CUSTOMERS.get(1));
        order2.setDeliveryService(SAVED_DELIVERY_SERVICES.get(1));
        order3.setCustomer(SAVED_CUSTOMERS.get(2));
        order3.setDeliveryService(SAVED_DELIVERY_SERVICES.get(2));

        SAVED_ORDERS.add(orderJpaRepository.save(order1));
        SAVED_ORDERS.add(orderJpaRepository.save(order2));
        SAVED_ORDERS.add(orderJpaRepository.save(order3));

        DishOpinionEntity dishOpinion1 = someDishOpinion1();
        DishOpinionEntity dishOpinion2 = someDishOpinion2();
        DishOpinionEntity dishOpinion3 = someDishOpinion3();
        dishOpinion1.setCustomer(SAVED_CUSTOMERS.get(0));
        dishOpinion1.setDish(SAVED_DISHES.get(0));
        dishOpinion2.setCustomer(SAVED_CUSTOMERS.get(1));
        dishOpinion2.setDish(SAVED_DISHES.get(1));
        dishOpinion3.setCustomer(SAVED_CUSTOMERS.get(2));
        dishOpinion3.setDish(SAVED_DISHES.get(2));

        SAVED_DISH_OPINIONS.add(dishOpinionJpaRepository.save(dishOpinion1));
        SAVED_DISH_OPINIONS.add(dishOpinionJpaRepository.save(dishOpinion2));
        SAVED_DISH_OPINIONS.add(dishOpinionJpaRepository.save(dishOpinion3));

        DishCompositionEntity dishComposition1 = someDishComposition1();
        DishCompositionEntity dishComposition2 = someDishComposition2();
        DishCompositionEntity dishComposition3 = someDishComposition3();
        dishComposition1.setOrder(SAVED_ORDERS.get(0));
        dishComposition1.setDish(SAVED_DISHES.get(0));
        dishComposition2.setOrder(SAVED_ORDERS.get(1));
        dishComposition2.setDish(SAVED_DISHES.get(1));
        dishComposition3.setOrder(SAVED_ORDERS.get(2));
        dishComposition3.setDish(SAVED_DISHES.get(2));

        SAVED_DISH_COMPOSITIONS.add(dishCompositionJpaRepository.save(dishComposition1));
        SAVED_DISH_COMPOSITIONS.add(dishCompositionJpaRepository.save(dishComposition2));
        SAVED_DISH_COMPOSITIONS.add(dishCompositionJpaRepository.save(dishComposition3));
    }

//    public void initializedBData(){
//        UserEntity customerUser1 = someUserCustomer1();
//        UserEntity customerUser2 = someUserCustomer2();
//        UserEntity customerUser3 = someUserCustomer3();
//
//        UserEntity restaurantOwnerUser1 = someUserRestaurantOwner1();
//        UserEntity restaurantOwnerUser2 = someUserRestaurantOwner2();
//        UserEntity restaurantOwnerUser3 = someUserRestaurantOwner3();
//
//        UserEntity deliveryManUser1 = someUserDeliveryMan1();
//        UserEntity deliveryManUser2 = someUserDeliveryMan2();
//        UserEntity deliveryManUser3 = someUserDeliveryMan3();
//
//        DeliveryAddressEntity deliveryAddressEntity1 = DeliveryAddressInstance.someDeliveryAddress1();
//        DeliveryAddressEntity deliveryAddressEntity2 = DeliveryAddressInstance.someDeliveryAddress2();
//        DeliveryAddressEntity deliveryAddressEntity3 = DeliveryAddressInstance.someDeliveryAddress3();
//
//        CustomerEntity customer1 = someCustomer1();
//        CustomerEntity customer2 = someCustomer2();
//        CustomerEntity customer3 = someCustomer3();
//
//        DeliveryManEntity deliveryMan1 = someDeliveryMan1();
//        DeliveryManEntity deliveryMan2 = someDeliveryMan2();
//        DeliveryManEntity deliveryMan3 = someDeliveryMan3();
//
//        DeliveryServiceEntity deliveryService1 = someDeliveryService1();
//        DeliveryServiceEntity deliveryService2 = someDeliveryService2();
//        DeliveryServiceEntity deliveryService3 = someDeliveryService3();
//
//        OrderEntity order1 = someOrder1();
//        OrderEntity order2 = someOrder2();
//        OrderEntity order3 = someOrder3();
//
//        RestaurantOwnerEntity restaurantOwner1 = someRestaurantOwner1();
//        RestaurantOwnerEntity restaurantOwner2 = someRestaurantOwner2();
//        RestaurantOwnerEntity restaurantOwner3 = someRestaurantOwner3();
//
//        ServedAddressEntity servedAddress1 = ServedAddressInstance.someServedAddress1();
//        ServedAddressEntity servedAddress2 = ServedAddressInstance.someServedAddress2();
//        ServedAddressEntity servedAddress3 = ServedAddressInstance.someServedAddress3();
//
//        RestaurantEntity restaurant1 = someRestaurant1();
//        RestaurantEntity restaurant2 = someRestaurant2();
//        RestaurantEntity restaurant3 = someRestaurant3();
//
//        DishPhotoEntity dishPhoto1 = someDishPhoto1();
//        DishPhotoEntity dishPhoto2 = someDishPhoto2();
//        DishPhotoEntity dishPhoto3 = someDishPhoto3();
//
//        DishCategoryEntity dishCategory1 = someDishCategory1();
//        DishCategoryEntity dishCategory2 = someDishCategory2();
//        DishCategoryEntity dishCategory3 = someDishCategory3();
//
//        DishEntity dish1 = someDish1();
//        DishEntity dish2 = someDish2();
//        DishEntity dish3 = someDish3();
//
//        DishOpinionEntity dishOpinion1 = someDishOpinion1();
//        DishOpinionEntity dishOpinion2 = someDishOpinion2();
//        DishOpinionEntity dishOpinion3 = someDishOpinion3();
//
//        DishCompositionEntity dishComposition1 = DishCompositionInstance.someDishComposition1();
//        DishCompositionEntity dishComposition2 = DishCompositionInstance.someDishComposition2();
//        DishCompositionEntity dishComposition3 = DishCompositionInstance.someDishComposition3();
//    }
}
