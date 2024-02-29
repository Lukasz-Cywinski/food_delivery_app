package pl.project.infrastructure.security;

import lombok.Getter;

@Getter
public enum Role {

    CUSTOMER(1),
    RESTAURANT_OWNER(2),
    DELIVERY_MAN(3),
    REST_API(4),
    ADMIN(5);

    private final Integer id;

    Role(Integer id) {
        this.id = id;
    }
}
