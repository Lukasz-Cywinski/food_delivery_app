package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.RestaurantDAO;
import pl.project.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import pl.project.infrastructure.database.repository.mapper.RestaurantMapper;

@Repository
@AllArgsConstructor
public class RestaurantRepository implements RestaurantDAO {

    private final RestaurantJpaRepository restaurantOwnerJpaRepository;
    private final RestaurantMapper restaurantMapper;
}
