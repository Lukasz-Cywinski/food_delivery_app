package pl.project.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.project.business.dao.OrderDAO;
import pl.project.infrastructure.database.repository.jpa.OrderJpaRepository;
import pl.project.infrastructure.database.repository.mapper.OrderMapper;

@Repository
@AllArgsConstructor
public class OrderRepository implements OrderDAO {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderMapper orderMapper;
}
