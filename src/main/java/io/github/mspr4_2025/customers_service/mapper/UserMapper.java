package io.github.mspr4_2025.customers_service.mapper;

import io.github.mspr4_2025.customers_service.entity.UserEntity;
import io.github.mspr4_2025.customers_service.model.user.UserCreateDto;
import io.github.mspr4_2025.customers_service.model.user.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

@Mapper
public abstract class UserMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public abstract UserDto fromEntity(UserEntity entity);

    public abstract List<UserDto> fromEntities(Collection<UserEntity> entities);

    @Mapping(target = "password", qualifiedByName = "encodePassword")
    public abstract UserEntity fromCreateDto(UserCreateDto createDto);

    @Named("encodePassword")
    protected String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
