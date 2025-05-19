package io.github.MSPR4_2025.customers_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.MSPR4_2025.customers_service.entity.CustomerEntity;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByUid(UUID uid);
}
