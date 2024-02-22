package pl.project.infrastructure.database.repository.mapper.imp;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.project.domain.model.ServedAddress;
import pl.project.infrastructure.database.entity.ServedAddressEntity;
import pl.project.infrastructure.database.repository.mapper.RestaurantEntityMapper;
import pl.project.infrastructure.database.repository.mapper.ServedAddressEntityMapper;

@Component
@AllArgsConstructor
public class ServedAddressEntityMapperImp implements ServedAddressEntityMapper {

    private final RestaurantEntityMapper restaurantEntityMapper;

    public ServedAddress mapFromEntity(ServedAddressEntity entity){
        return ServedAddress.builder()
                .city(entity.getCity())
                .street(entity.getStreet())
                .restaurant(restaurantEntityMapper.mapFromEntity(entity.getRestaurant()))
                .build();
    }

    public ServedAddressEntity mapToEntity(ServedAddress domainObj){
        return ServedAddressEntity.builder()
                .city(domainObj.getCity())
                .street(domainObj.getStreet())
                .restaurant(restaurantEntityMapper.mapToEntity(domainObj.getRestaurant()))
                .build();
    }
}
