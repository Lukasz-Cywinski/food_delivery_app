package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.RestaurantOwnerDAO;
import pl.project.infrastructure.database.repository.jpa.RestaurantOwnerJpaRepository;
import pl.project.infrastructure.database.repository.mapper.RestaurantOwnerMapper;

@Repository
@AllArgsConstructor
public class RestaurantOwnerRepository implements RestaurantOwnerDAO {

    private final RestaurantOwnerJpaRepository restaurantOwnerJpaRepository;
    private final RestaurantOwnerMapper restaurantOwnerMapper;
}
