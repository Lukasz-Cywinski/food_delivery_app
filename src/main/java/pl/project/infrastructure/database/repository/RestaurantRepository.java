package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.RestaurantDAO;
import pl.project.domain.model.Dish;
import pl.project.domain.model.Restaurant;
import pl.project.domain.model.RestaurantOwner;
import pl.project.domain.model.ServedAddress;
import pl.project.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import pl.project.infrastructure.database.repository.mapper.DishEntityMapper;
import pl.project.infrastructure.database.repository.mapper.RestaurantEntityMapper;
import pl.project.infrastructure.database.repository.mapper.RestaurantOwnerEntityMapper;
import pl.project.infrastructure.database.repository.mapper.ServedAddressEntityMapper;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RestaurantRepository implements RestaurantDAO {

    private final RestaurantJpaRepository restaurantJpaRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;
    private final RestaurantOwnerEntityMapper restaurantOwnerEntityMapper;
    private final DishEntityMapper dishEntityMapper;
    private final ServedAddressEntityMapper servedAddressEntityMapper;

    @Override
    public Optional<Restaurant> createRestaurant(Restaurant restaurant) {
        return Optional.ofNullable(
                restaurantEntityMapper.mapFromEntity(
                        restaurantJpaRepository.save(
                                restaurantEntityMapper.mapToEntity(restaurant)
                        )
                ));
    }

    @Override
    public Optional<Restaurant> findRestaurantByRestaurantCode(String restaurantCode) {
        return restaurantJpaRepository.findByRestaurantCode(restaurantCode)
                .map(restaurantEntityMapper::mapFromEntity);
    }

    @Override
    public List<Restaurant> findActiveRestaurants(Pageable pageable) {
        return restaurantJpaRepository.findActive(pageable).getContent().stream()
                .map(restaurantEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public List<Dish> findActiveDishesForRestaurant(Restaurant restaurant, Pageable pageable) {
        return restaurantJpaRepository.findActiveDishes(
                restaurantEntityMapper.mapToEntity(restaurant), pageable).getContent().stream()
                .map(dishEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public List<ServedAddress> findServedAddressesForRestaurant(Restaurant restaurant) {
        return restaurantJpaRepository.findServedAddresses(
                        restaurantEntityMapper.mapToEntity(restaurant)).stream()
                .map(servedAddressEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Integer changeRestaurantName(String newRestaurantName, String restaurantCode) {
        return restaurantJpaRepository.changeName(newRestaurantName, restaurantCode);
    }

    @Override
    public Integer changeRestaurantOwner(RestaurantOwner newRestaurantOwner, String restaurantCode) {
        return restaurantJpaRepository.changeOwner(
                restaurantOwnerEntityMapper.mapToEntity(newRestaurantOwner),
                restaurantCode);
    }

    @Override
    public Integer deactivateRestaurant(String restaurantCode) {
        return restaurantJpaRepository.deactivate(restaurantCode);
    }
}
