package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.DeliveryServiceDAO;
import pl.project.infrastructure.database.repository.jpa.DeliveryServiceJpaRepository;
import pl.project.infrastructure.database.repository.mapper.DeliveryServiceMapper;

@Repository
@AllArgsConstructor
public class DeliveryServiceRepository implements DeliveryServiceDAO {

    private final DeliveryServiceJpaRepository deliveryServiceJpaRepository;
    private final DeliveryServiceMapper deliveryServiceMapper;
}
