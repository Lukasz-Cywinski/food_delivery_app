package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.ServedAddressDAO;
import pl.project.infrastructure.database.repository.jpa.ServedAddressJpaRepository;
import pl.project.infrastructure.database.repository.mapper.ServedAddressMapper;

@Repository
@AllArgsConstructor
public class ServedAddressRepository implements ServedAddressDAO {

    private final ServedAddressJpaRepository servedAddressJpaRepository;
    private final ServedAddressMapper servedAddressMapper;

}
