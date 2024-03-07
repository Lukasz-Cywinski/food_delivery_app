package pl.project.infrastructure.database.repository.mapper.imp;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.project.domain.model.ServedAddress;
import pl.project.infrastructure.database.entity.ServedAddressEntity;
import pl.project.infrastructure.database.repository.mapper.RestaurantEntityMapper;
import pl.project.infrastructure.database.repository.mapper.ServedAddressEntityMapper;

import java.util.Objects;

@Component
@AllArgsConstructor
public class ServedAddressEntityMapperImp implements ServedAddressEntityMapper {

    private final RestaurantEntityMapper restaurantEntityMapper;

    public ServedAddress mapFromEntity(ServedAddressEntity entity){
        return ServedAddress.builder()
                .id(entity.getId())
                .city(entity.getCity())
                .street(entity.getStreet())
                .restaurant(Objects.isNull(entity.getRestaurant()) ?
                        null : restaurantEntityMapper.mapFromEntity(entity.getRestaurant()))
                .build();
    }

    public ServedAddressEntity mapToEntity(ServedAddress domainObj){
        return ServedAddressEntity.builder()
                .id(domainObj.getId())
                .city(domainObj.getCity())
                .street(domainObj.getStreet())
                .restaurant(Objects.isNull(domainObj.getRestaurant()) ?
                        null : restaurantEntityMapper.mapToEntity(domainObj.getRestaurant()))
                .build();
    }
}
