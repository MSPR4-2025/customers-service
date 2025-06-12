package io.github.MSPR4_2025.customers_service.controller;

import io.github.MSPR4_2025.customers_service.model.CustomerCreateDto;
import io.github.MSPR4_2025.customers_service.model.CustomerDto;
import io.github.MSPR4_2025.customers_service.model.CustomerUpdateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // Recreate the database after each test
@AutoConfigureTestDatabase // Autoconfigure an in memory test database
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTest {
    @Autowired
    WebTestClient client;

    @Test
    void testShouldFail() {
        assertThat(false).isTrue();
    }

    @Sql("classpath:/sql/CustomerController/customers.sql")
    @Test
    void testGetCustomerList() {
        EntityExchangeResult<List<CustomerDto>> result = client.get()
            .uri("/api/customers/")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(CustomerDto.class)
            .returnResult();

        assertThat(result.getResponseBody()).isNotEmpty();
        assertThat(result.getResponseBody()).hasSize(2);
        assertThat(result.getResponseBody()).anyMatch(customerDto ->
            customerDto.getName().equals("Pierre Jacques"));
        assertThat(result.getResponseBody()).anyMatch(customerDto ->
            customerDto.getName().equals("Jeanne Mercier"));
    }

    @Test
    void testCreateCustomer() {
        CustomerCreateDto customerCreate = new CustomerCreateDto();
        customerCreate.setName("Alex Bric");

        EntityExchangeResult<Void> result = client.post()
            .uri("/api/customers/")
            .bodyValue(customerCreate)
            .exchange()
            .expectStatus().isCreated()
            .expectBody().isEmpty();

        String customerLocation = result.getResponseHeaders().getFirst("Location");
        assertThat(customerLocation).isNotEmpty();

        EntityExchangeResult<CustomerDto> customerResult = client.get()
            .uri(customerLocation)
            .exchange()
            .expectStatus().isOk()
            .expectBody(CustomerDto.class)
            .returnResult();

        assertThat(customerResult.getResponseBody()).isNotNull();
        assertThat(customerResult.getResponseBody().getName()).isEqualTo(customerCreate.getName());
    }

    @Sql("classpath:/sql/CustomerController/customers.sql")
    @Test
    void testGetCustomer() {
        String customerUid = "939c7754-d2b8-43c9-b710-6740f3a0ee61";

        EntityExchangeResult<CustomerDto> result = client.get()
            .uri("/api/customers/{uid}", customerUid)
            .exchange()
            .expectStatus().isOk()
            .expectBody(CustomerDto.class)
            .returnResult();

        assertThat(result.getResponseBody()).isNotNull();
        assertThat(result.getResponseBody().getName()).isEqualTo("Pierre Jacques");
    }

    @Sql("classpath:/sql/CustomerController/customers.sql")
    @Test
    void testUpdateCustomer() {
        String customerUid = "939c7754-d2b8-43c9-b710-6740f3a0ee61";
        CustomerUpdateDto customerUpdate = new CustomerUpdateDto();
        customerUpdate.setName("Arthur Morgan");

        client.put()
            .uri("/api/customers/{uid}", customerUid)
            .bodyValue(customerUpdate)
            .exchange()
            .expectStatus().isOk()
            .expectBody().isEmpty();

        EntityExchangeResult<CustomerDto> result = client.get()
            .uri("/api/customers/{uid}", customerUid)
            .exchange()
            .expectStatus().isOk()
            .expectBody(CustomerDto.class)
            .returnResult();

        assertThat(result.getResponseBody()).isNotNull();
        assertThat(result.getResponseBody().getName()).isEqualTo(customerUpdate.getName());
    }

    @Sql("classpath:/sql/CustomerController/customers.sql")
    @Test
    void testDeleteCustomer() {
        String customerUid = "939c7754-d2b8-43c9-b710-6740f3a0ee61";

        client.delete()
            .uri("api/customers/{uid}", customerUid)
            .exchange()
            .expectStatus().isOk()
            .expectBody().isEmpty();

        client.get()
            .uri("/api/customers/{uid}", customerUid)
            .exchange()
            .expectStatus().isNotFound();
    }
}
