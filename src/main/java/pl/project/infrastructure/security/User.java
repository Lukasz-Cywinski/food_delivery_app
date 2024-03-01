package pl.project.infrastructure.security;

import lombok.*;
import pl.project.infrastructure.security.db.RoleEntity;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"userName"})
public class User {

    Integer id;
    String userName;
    String email;
    String password;
    Boolean active;
    Set<Role> roles;

    @Override
    public String toString() {
        String active = getActive() ? "active" : "not active";
        String roles = getRoles().stream()
                .map(Enum::name)
                .reduce("", (l, r) -> "%s %n %s".formatted(l, r).trim());
        return "%s user with roles: %n %s".formatted(active, roles);
    }
}
