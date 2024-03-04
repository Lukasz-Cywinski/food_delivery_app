package pl.project.infrastructure.database.repository.mapper.imp;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.project.domain.model.DishOpinion;
import pl.project.infrastructure.database.entity.DishOpinionEntity;
import pl.project.infrastructure.database.repository.mapper.CustomerEntityMapper;
import pl.project.infrastructure.database.repository.mapper.DishEntityMapper;
import pl.project.infrastructure.database.repository.mapper.DishOpinionEntityMapper;

@Component
@AllArgsConstructor
public class DishOpinionEntityMapperImp implements DishOpinionEntityMapper {

    private final DishEntityMapper dishEntityMapper;
    private final CustomerEntityMapper customerEntityMapper;

    public DishOpinion mapFromEntity(DishOpinionEntity entity){
        return DishOpinion.builder()
                .id(entity.getId())
                .opinion(entity.getOpinion())
                .productEvaluation(entity.getProductEvaluation())
                .dish(dishEntityMapper.mapFromEntity(entity.getDish()))
                .customer(customerEntityMapper.mapFromEntity(entity.getCustomer()))
                .build();
    }

    public DishOpinionEntity mapToEntity(DishOpinion domainObj){
        return DishOpinionEntity.builder()
                .id(domainObj.getId())
                .opinion(domainObj.getOpinion())
                .productEvaluation(domainObj.getProductEvaluation())
                .dish(dishEntityMapper.mapToEntity(domainObj.getDish()))
                .customer(customerEntityMapper.mapToEntity(domainObj.getCustomer()))
                .build();
    }
}
