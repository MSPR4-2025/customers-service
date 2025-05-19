package io.github.MSPR4_2025.customers_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.MSPR4_2025.customers_service.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {


}
