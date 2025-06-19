package io.github.mspr4_2025.customers_service.model;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class CustomerDto extends CustomerUpdateDto {
    private UUID uid;

    private OffsetDateTime createdAt;
}
