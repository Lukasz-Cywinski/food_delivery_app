package pl.project.infrastructure.security;

import jakarta.persistence.*;
import lombok.*;
import pl.project.infrastructure.security.RoleEntity;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"userName"})
public class User {

    String userName;
    String password;
    Boolean active;
    Set<RoleEntity> roles;

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", active=" + active +
                '}';
    }
}
