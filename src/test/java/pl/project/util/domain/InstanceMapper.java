package pl.project.util.domain;

import pl.project.domain.model.*;
import pl.project.infrastructure.database.entity.*;
import pl.project.infrastructure.database.repository.mapper.*;
import pl.project.infrastructure.database.repository.mapper.imp.*;


public class InstanceMapper {

    private final RestaurantOwnerEntityMapper restaurantOwnerEntityMapper = new RestaurantOwnerEntityMapperImp();
    private final RestaurantEntityMapper restaurantEntityMapper = new RestaurantEntityMapperImp(restaurantOwnerEntityMapper);
    private final ServedAddressEntityMapper servedAddressEntityMapper = new ServedAddressEntityMapperImp(restaurantEntityMapper);
    private final DeliveryAddressEntityMapper deliveryAddressEntityMapper = new DeliveryAddressEntityMapperImp();
    private final CustomerEntityMapper customerEntityMapper = new CustomerEntityMapperImp(deliveryAddressEntityMapper);
    private final DeliveryManEntityMapper deliveryManEntityMapper = new DeliveryManEntityMapperImp();
    private final DeliveryServiceEntityMapper deliveryServiceEntityMapper = new DeliveryServiceEntityMapperImp(deliveryManEntityMapper);
    private final OrderEntityMapper orderEntityMapper = new OrderEntityMapperImp(customerEntityMapper, deliveryServiceEntityMapper);
    private final DishPhotoEntityMapper dishPhotoEntityMapper = new DishPhotoEntityMapperImp();
    private final DishCategoryEntityMapper dishCategoryEntityMapper = new DishCategoryEntityMapperImp();
    private final DishEntityMapper dishEntityMapper = new DishEntityMapperImp(restaurantEntityMapper, dishPhotoEntityMapper, dishCategoryEntityMapper);
    private final DishCompositionEntityMapper dishCompositionEntityMapper = new DishCompositionEntityMapperImp(dishEntityMapper, orderEntityMapper);
    private final DishOpinionEntityMapper dishOpinionEntityMapper = new DishOpinionEntityMapperImp(dishEntityMapper, customerEntityMapper);

    public Customer mapFromEntity(CustomerEntity customer){
        return customerEntityMapper.mapFromEntity(customer);
    }

    public DeliveryAddress mapFromEntity(DeliveryAddressEntity deliveryAddress){
        return deliveryAddressEntityMapper.mapFromEntity(deliveryAddress);
    }

    public DeliveryMan mapFromEntity(DeliveryManEntity deliveryMan){
        return deliveryManEntityMapper.mapFromEntity(deliveryMan);
    }

    public DeliveryService mapFromEntity(DeliveryServiceEntity deliveryService){
        return deliveryServiceEntityMapper.mapFromEntity(deliveryService);
    }

    public DishCategory mapFromEntity(DishCategoryEntity dishCategory){
        return dishCategoryEntityMapper.mapFromEntity(dishCategory);
    }

    public DishComposition mapFromEntity(DishCompositionEntity dishComposition){
        return dishCompositionEntityMapper.mapFromEntity(dishComposition);
    }

    public DishOpinion mapFromEntity(DishOpinionEntity dishOpinion){
        return dishOpinionEntityMapper.mapFromEntity(dishOpinion);
    }

    public DishPhoto mapFromEntity(DishPhotoEntity dishPhoto){
        return dishPhotoEntityMapper.mapFromEntity(dishPhoto);
    }

    public Dish mapFromEntity(DishEntity dish){
        return dishEntityMapper.mapFromEntity(dish);
    }

    public Order mapFromEntity(OrderEntity order){
        return orderEntityMapper.mapFromEntity(order);
    }

    public RestaurantOwner mapFromEntity(RestaurantOwnerEntity restaurantOwner){
        return restaurantOwnerEntityMapper.mapFromEntity(restaurantOwner);
    }

    public Restaurant mapFromEntity(RestaurantEntity restaurant){
        return restaurantEntityMapper.mapFromEntity(restaurant);
    }

    public ServedAddress mapFromEntity(ServedAddressEntity servedAddress){
        return servedAddressEntityMapper.mapFromEntity(servedAddress);
    }
}
