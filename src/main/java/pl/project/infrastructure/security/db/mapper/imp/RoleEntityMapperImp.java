package pl.project.infrastructure.security.db.mapper.imp;

import org.springframework.stereotype.Component;
import pl.project.infrastructure.security.Role;
import pl.project.infrastructure.security.db.RoleEntity;
import pl.project.infrastructure.security.db.mapper.RoleEntityMapper;

@Component
public class RoleEntityMapperImp implements RoleEntityMapper {
    @Override
    public Role mapFromEntity(RoleEntity entity) {
        return Role.valueOf(entity.getRole());
    }

    @Override
    public RoleEntity mapToEntity(Role domainObj) {
        return RoleEntity.builder()
                .id(domainObj.getId())
                .role(domainObj.name())
                .build();
    }
}
