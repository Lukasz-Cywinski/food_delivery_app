package pl.project.infrastructure.security.db.mapper;

import pl.project.infrastructure.security.User;
import pl.project.infrastructure.security.db.UserEntity;

public interface UserEntityMapper {

    User mapFromEntity(UserEntity entity);
    UserEntity mapToEntity(User domainObj);
}
