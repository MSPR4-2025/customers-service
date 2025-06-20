package io.github.mspr4_2025.customers_service.controller;


import io.github.mspr4_2025.customers_service.entity.CustomerEntity;
import io.github.mspr4_2025.customers_service.mapper.CustomerMapper;
import io.github.mspr4_2025.customers_service.model.CustomerCreateDto;
import io.github.mspr4_2025.customers_service.model.CustomerDto;
import io.github.mspr4_2025.customers_service.model.CustomerUpdateDto;
import io.github.mspr4_2025.customers_service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final RabbitTemplate rabbitTemplate;


    @Value("events-exchange")
    private String eventsExchange;

    @Value("customers-topic-exchange")
    private String customersExchange;

    @Value("customers.update")
    private String customerUpdateRouting;

    @Value("${app.rabbitmq.routingkey:customers.get.all}")
    private String customersGetAllRouting;
    
    @Value("customers.get")
    private String customersGetRouting;

    @Value("customers.create")
    private String customersCreateRouting;
    
    @Value("customers.delete")
    private String customersDeleteRouting;




    @GetMapping("/")
    public ResponseEntity<List<CustomerDto>> listCustomers() {
        List<CustomerEntity> customerEntities = customerService.getAllCustomers();
        rabbitTemplate.convertAndSend(customersExchange, customersGetAllRouting, "GET /api/customers/ called");
        rabbitTemplate.convertAndSend(eventsExchange, customersGetAllRouting, "GET /api/customers/ called");


        return ResponseEntity.ok(customerMapper.fromEntities(customerEntities));
    }

    @PostMapping("/")
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerCreateDto customerCreate) {
        
        CustomerEntity customerEntity = customerService.createCustomer(customerCreate);

        // Get the url to GET the created customer
        URI customerUri = MvcUriComponentsBuilder
            .fromMethodCall(MvcUriComponentsBuilder
                .on(CustomerController.class)
                .getCustomerByUid(customerEntity.getUid()))
            .build()
            .toUri();

        rabbitTemplate.convertAndSend(customersExchange, customersCreateRouting, customerMapper.fromEntity(customerEntity).toString());
        return ResponseEntity.created(customerUri).build();
        
    }

    @GetMapping("/{uid}")
    public ResponseEntity<CustomerDto> getCustomerByUid(@PathVariable UUID uid) {
        CustomerEntity customerEntity = customerService.getCustomerByUid(uid);
        
        rabbitTemplate.convertAndSend(customersExchange, customersGetRouting, customerMapper.fromEntity(customerEntity).toString());

        return ResponseEntity.ok(customerMapper.fromEntity(customerEntity));
    }

    @PutMapping("/{uid}")
    public ResponseEntity<Void> updateCustomer(@PathVariable UUID uid,
                                               @RequestBody CustomerUpdateDto customerUpdate) {
        // customerService.updateCustomer(uid, customerUpdate);
        System.out.println("Customer with UID " + uid + " updated successfully. customersUpdateRouting: " + customerUpdateRouting);
        customerMapper.updateEntityFromDto(customerUpdate, customerService.getCustomerByUid(uid));
        rabbitTemplate.convertAndSend(customersExchange, customerUpdateRouting, uid.toString());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID uid) {
        customerService.deleteCustomer(uid);
        
        rabbitTemplate.convertAndSend(customersExchange, customersDeleteRouting, uid.toString());

        return ResponseEntity.ok().build();
    }
    
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "customers-queue", durable = "true"),
            exchange = @Exchange(value = "orders-exchange", type = "topic"),
            key = "customers.get"
    ))
    public void handleGetCustomerRequest(String message){
        
        
        try{
            Thread.sleep(1000); // Simulate processing delay
            System.out.println("handleGetCustomerRequest Received message: " + message);

            CustomerCreateDto customerCreateDto = new CustomerCreateDto();
            customerService.createCustomer(customerCreateDto);
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }
}
