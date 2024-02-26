package pl.project.infrastructure.database.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.project.infrastructure.database.entity.CustomerEntity;
import pl.project.infrastructure.database.entity.DishEntity;
import pl.project.infrastructure.database.entity.DishOpinionEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface DishOpinionJpaRepository extends JpaRepository<DishOpinionEntity, Integer> {

    @Query("""
                SELECT d_op FROM DishOpinionEntity d_op
                WHERE d_op.dish = ?1
                """)
    Page<DishOpinionEntity> findByDish(DishEntity dish, Pageable pageable);

    @Query("""
                SELECT d_op FROM DishOpinionEntity d_op
                WHERE d_op.customer = ?1
                """)
    List<DishOpinionEntity> findByCustomer(CustomerEntity customer);

    @Query("""
                SELECT d_op FROM DishOpinionEntity d_op
                WHERE d_op.productEvaluation BETWEEN ?1 AND ?2
                """)
    Page<DishOpinionEntity> findByEvaluationRange(BigDecimal from, BigDecimal to, Pageable pageable);

    @Query(value = """
                SELECT AVG(productEvaluation)
                FROM DishOpinionEntity d_op
                WHERE dish = ?1
                """)
    Optional<BigDecimal> getAverageDishEvaluation(DishEntity dish);
}
