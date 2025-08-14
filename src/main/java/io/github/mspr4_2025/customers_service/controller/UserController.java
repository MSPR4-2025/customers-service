package io.github.mspr4_2025.customers_service.controller;

import io.github.mspr4_2025.customers_service.annotation.IsAdmin;
import io.github.mspr4_2025.customers_service.entity.UserEntity;
import io.github.mspr4_2025.customers_service.mapper.UserMapper;
import io.github.mspr4_2025.customers_service.model.user.UserCreateDto;
import io.github.mspr4_2025.customers_service.model.user.UserDto;
import io.github.mspr4_2025.customers_service.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@IsAdmin
@SecurityRequirement(name = "basicAuth")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> listUsers() {
        List<UserEntity> userEntities = userService.getAllUsers();

        return ResponseEntity.ok(userMapper.fromEntities(userEntities));
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserCreateDto userCreateDto) {
        UserEntity userEntity = userService.createUser(userCreateDto);

        // Get the url to GET the created user
        URI userUri = MvcUriComponentsBuilder
            .fromMethodCall(MvcUriComponentsBuilder
                .on(getClass())
                .getUserByUid(userEntity.getUid()))
            .build()
            .toUri();

        return ResponseEntity.created(userUri).build();
    }

    @GetMapping("/{uid}")
    public ResponseEntity<UserDto> getUserByUid(@PathVariable UUID uid) {
        UserEntity userEntity = userService.getUserByUid(uid);

        return ResponseEntity.ok(userMapper.fromEntity(userEntity));
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID uid) {
        userService.deleteUser(uid);

        return ResponseEntity.ok().build();
    }
}
