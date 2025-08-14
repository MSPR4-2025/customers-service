package io.github.mspr4_2025.customers_service;

import io.github.mspr4_2025.customers_service.enums.Role;
import io.github.mspr4_2025.customers_service.model.user.UserCreateDto;
import io.github.mspr4_2025.customers_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing // Necessary to use jpa's entity listener
@PropertySource("classpath:database.properties")
@RequiredArgsConstructor
public class CustomersServiceApplication implements ApplicationRunner {
    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(CustomersServiceApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        UserCreateDto createDto = new UserCreateDto();
        createDto.setUsername("root");
        createDto.setPassword("root");
        createDto.setRole(Role.ADMIN);

        userService.createUser(createDto);

        log.info("root user created");
    }
}
