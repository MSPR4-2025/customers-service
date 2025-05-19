package io.github.MSPR4_2025.customers_service.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CustomerDto extends CustomerCreateDto {
    private UUID uid;
}
