package io.github.mspr4_2025.customers_service.model.user;

import io.github.mspr4_2025.customers_service.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDto {
    private String username;

    private String password;

    private Role role;
}
