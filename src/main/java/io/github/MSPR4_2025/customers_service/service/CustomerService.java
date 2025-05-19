package io.github.MSPR4_2025.customers_service.service;

import org.springframework.stereotype.Service;
import io.github.MSPR4_2025.customers_service.entity.Customer;
import io.github.MSPR4_2025.customers_service.repository.CustomerRepository;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }
    public Customer createCustomer(Customer customer) {

        if (customer.getId() != null) {
            throw new IllegalArgumentException("Customer ID should not be provided for new customers");
        }

        if (customerRepository.findById(customer.getId()).isPresent()) {
            throw new IllegalArgumentException("Customer with this ID already exists");
        }
        
        return customerRepository.save(customer);
    }

}
