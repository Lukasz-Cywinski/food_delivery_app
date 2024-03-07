package pl.project.infrastructure.database.repository.mapper.imp;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.project.domain.model.DishOpinion;
import pl.project.infrastructure.database.entity.DishOpinionEntity;
import pl.project.infrastructure.database.repository.mapper.CustomerEntityMapper;
import pl.project.infrastructure.database.repository.mapper.DishEntityMapper;
import pl.project.infrastructure.database.repository.mapper.DishOpinionEntityMapper;

import java.util.Objects;

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
                .dish(Objects.isNull(entity.getDish()) ?
                        null : dishEntityMapper.mapFromEntity(entity.getDish()))
                .customer(Objects.isNull(entity.getCustomer()) ?
                        null : customerEntityMapper.mapFromEntity(entity.getCustomer()))
                .build();
    }

    public DishOpinionEntity mapToEntity(DishOpinion domainObj){
        return DishOpinionEntity.builder()
                .id(domainObj.getId())
                .opinion(domainObj.getOpinion())
                .productEvaluation(domainObj.getProductEvaluation())
                .dish(Objects.isNull(domainObj.getDish()) ?
                        null : dishEntityMapper.mapToEntity(domainObj.getDish()))
                .customer(Objects.isNull(domainObj.getCustomer()) ?
                        null : customerEntityMapper.mapToEntity(domainObj.getCustomer()))
                .build();
    }
}
