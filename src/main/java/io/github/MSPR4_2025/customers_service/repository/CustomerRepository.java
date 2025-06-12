package io.github.MSPR4_2025.customers_service.repository;

import io.github.MSPR4_2025.customers_service.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByUid(UUID uid);
}
