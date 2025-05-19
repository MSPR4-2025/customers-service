package io.github.MSPR4_2025.customers_service.controller;


import io.github.MSPR4_2025.customers_service.entity.CustomerEntity;
import io.github.MSPR4_2025.customers_service.mapper.CustomerMapper;
import io.github.MSPR4_2025.customers_service.model.CustomerCreateDto;
import io.github.MSPR4_2025.customers_service.model.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.github.MSPR4_2025.customers_service.service.CustomerService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping("/")
    public ResponseEntity<List<CustomerDto>> listCustomers() {
        List<CustomerEntity> customerEntities = customerService.getAllCustomers();

        return ResponseEntity.ok(customerMapper.fromEntities(customerEntities));
    }

    @PostMapping("/")
    public ResponseEntity<CustomerDto> createCustomer(CustomerCreateDto customerCreate) {
        CustomerEntity customerEntity = customerService.createCustomer(customerCreate);

        return ResponseEntity.ok(customerMapper.fromEntity(customerEntity));
    }

    @GetMapping("/{uid}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable UUID uid) {
        Optional<CustomerEntity> customerEntity = customerService.getCustomerByUid(uid);

        return customerEntity
                .map(entity ->
                        ResponseEntity.ok(customerMapper.fromEntity(entity)))
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }
}
