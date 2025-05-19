package io.github.MSPR4_2025.customers_service.service;

import io.github.MSPR4_2025.customers_service.mapper.CustomerMapper;
import io.github.MSPR4_2025.customers_service.model.CustomerCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import io.github.MSPR4_2025.customers_service.entity.CustomerEntity;
import io.github.MSPR4_2025.customers_service.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<CustomerEntity> getCustomerByUid(UUID uid) {
        return customerRepository.findByUid(uid);
    }

    public CustomerEntity createCustomer(CustomerCreateDto customerCreate) {
        CustomerEntity entity = customerMapper.fromCreateDto(customerCreate);

        customerRepository.save(entity);

        return entity;
    }
}
