package io.github.MSPR4_2025.customers_service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureTestDatabase
@SpringBootTest
class CustomersServiceApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    void testShouldFail() {
        Assertions.assertThat(false).isTrue();
    }
}
