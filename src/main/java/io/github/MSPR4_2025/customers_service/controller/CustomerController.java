package io.github.MSPR4_2025.customers_service.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import io.github.MSPR4_2025.customers_service.entity.Customer;
import io.github.MSPR4_2025.customers_service.service.CustomerService;

@Controller
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    
    @GetMapping("/{id}")
    public Customer getCustomerById(Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping("/register")
    public Customer createCustomer(Customer customer) {
        return customerService.createCustomer(customer);
    }

}
