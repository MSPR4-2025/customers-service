package io.github.mspr4_2025.customers_service.repository;

import io.github.mspr4_2025.customers_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUid(UUID uid);

    Optional<UserEntity> findByUsername(String username);
}
