package io.github.mspr4_2025.customers_service.controller;


import io.github.mspr4_2025.customers_service.entity.CustomerEntity;
import io.github.mspr4_2025.customers_service.mapper.CustomerMapper;
import io.github.mspr4_2025.customers_service.model.customer.CustomerCreateDto;
import io.github.mspr4_2025.customers_service.model.customer.CustomerDto;
import io.github.mspr4_2025.customers_service.model.customer.CustomerUpdateDto;
import io.github.mspr4_2025.customers_service.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@SecurityRequirement(name = "basicAuth")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> listCustomers() {
        List<CustomerEntity> customerEntities = customerService.getAllCustomers();

        return ResponseEntity.ok(customerMapper.fromEntities(customerEntities));
    }

    @PostMapping
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerCreateDto customerCreate) {
        CustomerEntity customerEntity = customerService.createCustomer(customerCreate);

        // Get the url to GET the created customer
        URI customerUri = MvcUriComponentsBuilder
            .fromMethodCall(MvcUriComponentsBuilder
                .on(CustomerController.class)
                .getCustomerByUid(customerEntity.getUid()))
            .build()
            .toUri();

        return ResponseEntity.created(customerUri).build();
    }

    @GetMapping("/{uid}")
    public ResponseEntity<CustomerDto> getCustomerByUid(@PathVariable UUID uid) {
        CustomerEntity customerEntity = customerService.getCustomerByUid(uid);

        return ResponseEntity.ok(customerMapper.fromEntity(customerEntity));
    }

    @PutMapping("/{uid}")
    public ResponseEntity<Void> updateCustomer(@PathVariable UUID uid,
                                               @RequestBody CustomerUpdateDto customerUpdate) {
        customerService.updateCustomer(uid, customerUpdate);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID uid) {
        customerService.deleteCustomer(uid);

        return ResponseEntity.ok().build();
    }
}
