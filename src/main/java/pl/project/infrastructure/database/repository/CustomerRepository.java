package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.CustomerDAO;
import pl.project.infrastructure.database.repository.jpa.CustomerJpaRepository;
import pl.project.infrastructure.database.repository.mapper.CustomerMapper;

@Repository
@AllArgsConstructor
public class CustomerRepository implements CustomerDAO {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerMapper customerMapper;

}
