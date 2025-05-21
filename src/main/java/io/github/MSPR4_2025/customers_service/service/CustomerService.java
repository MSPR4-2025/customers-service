package io.github.MSPR4_2025.customers_service.service;

import io.github.MSPR4_2025.customers_service.mapper.CustomerMapper;
import io.github.MSPR4_2025.customers_service.model.CustomerCreateDto;
import io.github.MSPR4_2025.customers_service.model.CustomerUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import io.github.MSPR4_2025.customers_service.entity.CustomerEntity;
import io.github.MSPR4_2025.customers_service.repository.CustomerRepository;
import org.springframework.web.server.ResponseStatusException;

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

    /**
     * @throws ResponseStatusException when no entity exist with the given uid.
     * This exception is handled by the controllers, returning a response with the corresponding http status.
     */
    public CustomerEntity getCustomerByUid(UUID uid) throws ResponseStatusException {
        Optional<CustomerEntity> entity = customerRepository.findByUid(uid);

        if (entity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return entity.get();
    }

    public CustomerEntity createCustomer(CustomerCreateDto customerCreate) {
        CustomerEntity entity = customerMapper.fromCreateDto(customerCreate);

        return customerRepository.save(entity);
    }

    public void updateCustomer(UUID uid, CustomerUpdateDto customerUpdate) {
        CustomerEntity entity = this.getCustomerByUid(uid);

        customerMapper.updateEntityFromDto(customerUpdate, entity);

        customerRepository.save(entity);
    }

    public void deleteCustomer(UUID uid) {
        CustomerEntity entity = this.getCustomerByUid(uid);

        customerRepository.delete(entity);
    }
}
