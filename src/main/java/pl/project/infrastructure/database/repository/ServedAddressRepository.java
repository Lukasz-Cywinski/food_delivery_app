package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.ServedAddressDAO;
import pl.project.domain.exception.restaurant_owner.OwnerResourceReadException;
import pl.project.domain.model.Restaurant;
import pl.project.domain.model.ServedAddress;
import pl.project.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import pl.project.infrastructure.database.repository.jpa.ServedAddressJpaRepository;
import pl.project.infrastructure.database.repository.mapper.RestaurantEntityMapper;
import pl.project.infrastructure.database.repository.mapper.ServedAddressEntityMapper;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ServedAddressRepository implements ServedAddressDAO {

    private final ServedAddressJpaRepository servedAddressJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final ServedAddressEntityMapper servedAddressEntityMapper;
    private final RestaurantEntityMapper restaurantEntityMapper;

    @Override
    public Optional<ServedAddress> createServedAddress(ServedAddress servedAddress) {
        Restaurant restaurant = restaurantEntityMapper.mapFromEntity(
                restaurantJpaRepository.findByRestaurantCode(servedAddress.getRestaurant().getRestaurantCode())
                        .orElseThrow(() -> new OwnerResourceReadException("Fail to attach restaurant %s to served address"
                                .formatted(servedAddress.getRestaurant().getRestaurantCode()))));
        ServedAddress toSave = servedAddress.withRestaurant(restaurant);
        return Optional.ofNullable(
                servedAddressEntityMapper.mapFromEntity(
                        servedAddressJpaRepository.save(
                                servedAddressEntityMapper.mapToEntity(toSave)
                        )
                )
        );
    }

    @Override
    public Integer changeCity(String newCity, Integer servedAddressId) {
        return servedAddressJpaRepository.changeCity(newCity, servedAddressId);
    }

    @Override
    public Integer changeStreet(String newStreet, Integer servedAddressId) {
        return servedAddressJpaRepository.changeStreet(newStreet, servedAddressId);
    }

    @Override
    public void deleteServedAddress(Integer servedAddressId) {
        servedAddressJpaRepository.deleteById(servedAddressId);
    }

    @Override
    public List<ServedAddress> getServedAddresses(Restaurant restaurant) {
        return servedAddressJpaRepository.findServedAddressesForRestaurant(
                        restaurantEntityMapper.mapToEntity(restaurant)).stream()
                .map(servedAddressEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public List<Restaurant> getRestaurantByServedAddress(ServedAddress servedAddress) {
        return servedAddressJpaRepository.findRestaurantsForServedAddress(
                        servedAddressEntityMapper.mapToEntity(servedAddress)).stream()
                .map(restaurantEntityMapper::mapFromEntity)
                .toList();
    }
}
