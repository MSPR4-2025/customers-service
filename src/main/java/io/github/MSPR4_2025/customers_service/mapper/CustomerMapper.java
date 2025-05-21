package io.github.MSPR4_2025.customers_service.mapper;

import io.github.MSPR4_2025.customers_service.entity.CustomerEntity;
import io.github.MSPR4_2025.customers_service.model.CustomerCreateDto;
import io.github.MSPR4_2025.customers_service.model.CustomerDto;
import io.github.MSPR4_2025.customers_service.model.CustomerUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    List<CustomerDto> fromEntities(Collection<CustomerEntity> entities);

    CustomerDto fromEntity(CustomerEntity entity);

    CustomerEntity fromCreateDto(CustomerCreateDto dto);

    void updateEntityFromDto(CustomerUpdateDto dto, @MappingTarget CustomerEntity entity);
}
