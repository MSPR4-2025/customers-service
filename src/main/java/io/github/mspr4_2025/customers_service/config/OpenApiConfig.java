package io.github.mspr4_2025.customers_service.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "basicAuth", scheme = "basic")
public class OpenApiConfig {
    @Bean
    public OpenAPI getOpenApi() {
        return new OpenAPI()
            .info(new Info()
                .title("Customers Service")
                .version("0.1"));
    }
}
