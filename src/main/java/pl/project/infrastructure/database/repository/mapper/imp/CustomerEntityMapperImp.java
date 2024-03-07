package pl.project.infrastructure.database.repository.mapper.imp;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.project.domain.model.Customer;
import pl.project.infrastructure.database.entity.CustomerEntity;
import pl.project.infrastructure.database.repository.mapper.CustomerEntityMapper;
import pl.project.infrastructure.database.repository.mapper.DeliveryAddressEntityMapper;
import pl.project.infrastructure.security.db.mapper.UserEntityMapper;

import java.util.Objects;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerEntityMapperImp implements CustomerEntityMapper {

    private final DeliveryAddressEntityMapper deliveryAddressEntityMapper;
    private final UserEntityMapper userEntityMapper;

    public Customer mapFromEntity(CustomerEntity entity){
        return Customer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .deliveryAddress(Objects.isNull(entity.getDeliveryAddress()) ?
                        null : deliveryAddressEntityMapper.mapFromEntity(entity.getDeliveryAddress()))
                .isActive(entity.isActive())
                .user(Objects.isNull(entity.getUser()) ?
                        null : userEntityMapper.mapFromEntity(entity.getUser()))
                .build();
    }

    public CustomerEntity mapToEntity(Customer domainObj){
        return CustomerEntity.builder()
                .id(domainObj.getId())
                .name(domainObj.getName())
                .surname(domainObj.getSurname())
                .phoneNumber(domainObj.getPhoneNumber())
                .email(domainObj.getEmail())
                .deliveryAddress(Objects.isNull(domainObj.getDeliveryAddress()) ?
                        null : deliveryAddressEntityMapper.mapToEntity(domainObj.getDeliveryAddress()))
                .isActive(domainObj.isActive())
                .user(Objects.isNull(domainObj.getUser()) ?
                        null : userEntityMapper.mapToEntity(domainObj.getUser()))
                .build();
    }

}
