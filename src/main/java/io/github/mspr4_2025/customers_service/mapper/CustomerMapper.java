package io.github.mspr4_2025.customers_service.mapper;

import io.github.mspr4_2025.customers_service.entity.CustomerEntity;
import io.github.mspr4_2025.customers_service.model.CustomerCreateDto;
import io.github.mspr4_2025.customers_service.model.CustomerDto;
import io.github.mspr4_2025.customers_service.model.CustomerUpdateDto;
import jakarta.annotation.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;

@Mapper
public abstract class CustomerMapper {
    // Read value from properties file
    @Value("${app.zoneId:UTC}")
    private String zoneId;

    public abstract List<CustomerDto> fromEntities(Collection<CustomerEntity> entities);

    public abstract CustomerDto fromEntity(CustomerEntity entity);

    public abstract CustomerEntity fromCreateDto(CustomerCreateDto dto);

    public abstract void updateEntityFromDto(CustomerUpdateDto dto, @MappingTarget CustomerEntity entity);

    /**
     * Convert an {@link Instant} into the corresponding {@link OffsetDateTime}
     * at the zoneId specified in the properties file
     */
    @Nullable
    protected OffsetDateTime fromInstant(@Nullable Instant instant) {
        return instant == null ? null : OffsetDateTime.ofInstant(instant, ZoneId.of(zoneId));
    }
}
