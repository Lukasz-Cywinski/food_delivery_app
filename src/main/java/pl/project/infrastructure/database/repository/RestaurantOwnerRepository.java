package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.RestaurantOwnerDAO;
import pl.project.domain.model.RestaurantOwner;
import pl.project.infrastructure.database.repository.jpa.RestaurantOwnerJpaRepository;
import pl.project.infrastructure.database.repository.mapper.RestaurantOwnerEntityMapper;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RestaurantOwnerRepository implements RestaurantOwnerDAO {

    private final RestaurantOwnerJpaRepository restaurantOwnerJpaRepository;
    private final RestaurantOwnerEntityMapper restaurantOwnerEntityMapper;


    @Override
    public Optional<RestaurantOwner> createRestaurantOwner(RestaurantOwner restaurantOwner) {
        return Optional.ofNullable(
                restaurantOwnerEntityMapper.mapFromEntity(
                        restaurantOwnerJpaRepository.save(
                                restaurantOwnerEntityMapper.mapToEntity(restaurantOwner)
                        )
                )
        );
    }

    @Override
    public Optional<RestaurantOwner> findRestaurantOwnerByEmail(String email) {
        return restaurantOwnerJpaRepository.findByEmail(email)
                .map(restaurantOwnerEntityMapper::mapFromEntity);
    }

    @Override
    public List<RestaurantOwner> findActiveRestaurantOwner() {
        return restaurantOwnerJpaRepository.findActive().stream()
                .map(restaurantOwnerEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Integer changeRestaurantOwnerEmail(String newEmail, String oldEmail) {
        return restaurantOwnerJpaRepository.changeEmail(newEmail, oldEmail);
    }

    @Override
    public Integer changeRestaurantOwnerPhoneNumber(String newPhoneNumber, String email) {
        return restaurantOwnerJpaRepository.changePhoneNumber(newPhoneNumber, email);
    }

    @Override
    public Integer deactivateRestaurantOwner(String email) {
        return restaurantOwnerJpaRepository.deactivate(email);
    }
}
