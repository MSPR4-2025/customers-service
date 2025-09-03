package io.github.mspr4_2025.customers_service.service;

import io.github.mspr4_2025.customers_service.entity.CustomerEntity;
import io.github.mspr4_2025.customers_service.mapper.CustomerMapper;
import io.github.mspr4_2025.customers_service.model.CustomerCreateDto;
import io.github.mspr4_2025.customers_service.model.CustomerUpdateDto;
import io.github.mspr4_2025.customers_service.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RabbitTemplate rabbitTemplate;


    @Value("order_events_exchange")
    private String orderEventsExchange;

    @Value("stock_confirmation_queue")
    private String stockConfirmationQueue;

    @Value("stock_check_queue")
    private String stockCheckQueue;

    @Value("customer_verification_queue")
    private String customerVerificationQueue;

    @Value("customer_confirmation_queue")
    private String customerConfirmationQueue;

    @Value("order.created")
    private String orderCreatedKey;

    @Value("customer.verification.requested")
    private String customerVerificationRequestedKey;
    
    @Value("customer.verification.confirmed")
    private String customerVerificationConfirmedKey;

    @Value("product.verification.requested")
    private String productVerificationRequestedKey;

    @Value("product.verification.confirmed")
    private String productVerificationConfirmedKey;

    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * @throws ResponseStatusException when no entity exist with the given uid.
     *                                 This exception is handled by the controllers, returning a response with the corresponding http status.
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

    @RabbitListener(
        bindings = @QueueBinding(
            value = @Queue(value = "customer_verification_queue"),
            exchange = @Exchange(value = "order_events_exchange", type="topic"),
            key = "order.created"
        )
    )
    public void handleCreateOrderMessage(String message) {
        try {
            log.info("message received, creating order. Message: " + message);
            System.out.println("creating order");

            JsonNode orderNode = objectMapper.readTree(message);
            JsonNode customerNode = orderNode.get("order").get("customerUid");
            JsonNode orderUidNode = orderNode.get("orderUid");

            if (customerNode == null  || orderUidNode == null ) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid message");
            }

            log.info("parsed customerUid: " + customerNode.asText());
            
            try{
                customerRepository.findByUid(UUID.fromString(customerNode.asText()));
            } catch (Exception ex) {
            log.error("Exception in handleCreateOrderMessage: " + ex.getMessage());
            
            
            if (orderUidNode != null) {
                log.info("orderUidNode != null");
                
                sendOrderStatus(UUID.fromString(orderUidNode.asText()), "FAILED");
            }
        }
            UUID orderUid = UUID.fromString(orderUidNode.asText());
            sendOrderStatus(orderUid, "CONFIRMED");

        } catch (Exception e) {
            // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            log.error("CustomerService.handleCreateOrderMessage exception: " + e.getMessage());
        }
    }


    public void sendOrderStatus(UUID orderUid, String customerCheckStatus){
        try{
            String confirmationJson = objectMapper.createObjectNode()
                            .put("orderUid", orderUid.toString())
                            .put("customerCheckStatus", customerCheckStatus)
                            .toString();
            log.info("sendOrderStatus message sent : " + confirmationJson);
            rabbitTemplate.convertAndSend(orderEventsExchange, customerVerificationConfirmedKey, confirmationJson );
        } catch(Exception ex) {
            log.info("productServices.sendOrderStatus exception: " + ex);
        }
    }
}
