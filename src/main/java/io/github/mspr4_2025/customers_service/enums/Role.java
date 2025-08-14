package io.github.mspr4_2025.customers_service.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER(Role.Values.USER),
    ADMIN(Role.Values.ADMIN);

    @JsonValue
    private final String value;

    public static final class Values {
        public static final String USER = "USER";
        public static final String ADMIN = "ADMIN";
    }
}
