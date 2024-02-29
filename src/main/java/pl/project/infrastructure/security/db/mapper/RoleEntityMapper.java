package pl.project.infrastructure.security.db.mapper;

import pl.project.infrastructure.security.Role;
import pl.project.infrastructure.security.db.RoleEntity;

public interface RoleEntityMapper {

    Role mapFromEntity(RoleEntity entity);
    RoleEntity mapToEntity(Role domainObj);
}
