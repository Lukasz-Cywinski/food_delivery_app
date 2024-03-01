package pl.project.infrastructure.security.db.mapper.imp;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.project.infrastructure.database.repository.mapper.DeliveryAddressEntityMapper;
import pl.project.infrastructure.security.Role;
import pl.project.infrastructure.security.User;
import pl.project.infrastructure.security.db.RoleEntity;
import pl.project.infrastructure.security.db.UserEntity;
import pl.project.infrastructure.security.db.mapper.RoleEntityMapper;
import pl.project.infrastructure.security.db.mapper.UserEntityMapper;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserEntityMapperImp implements UserEntityMapper {

    private final RoleEntityMapper roleEntityMapper;

    public User mapFromEntity(UserEntity entity){
        if(Objects.isNull(entity.getRoles())) entity.setRoles(Set.of());
        return User.builder()
                .id(entity.getId())
                .userName(entity.getUserName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .active(entity.getActive())
                .roles(entity.getRoles().stream()
                        .map(roleEntityMapper::mapFromEntity)
                        .collect(Collectors.toSet()))
                .build();
    }

    public UserEntity mapToEntity(User domainObj){
        if(Objects.isNull(domainObj.getRoles())) domainObj = domainObj.withRoles(Set.of());
        return UserEntity.builder()
                .id(domainObj.getId())
                .userName(domainObj.getUserName())
                .email(domainObj.getEmail())
                .password(domainObj.getPassword())
                .active(domainObj.getActive())
                .roles(domainObj.getRoles().stream()
                        .map(roleEntityMapper::mapToEntity)
                        .collect(Collectors.toSet()))
                .build();
    }
}
