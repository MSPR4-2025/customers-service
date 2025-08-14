package io.github.mspr4_2025.customers_service.model.user;

import io.github.mspr4_2025.customers_service.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDto {
    private UUID uid;

    private String username;

    private Role role;
}
