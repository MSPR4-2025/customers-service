package io.github.mspr4_2025.customers_service.annotation;

import io.github.mspr4_2025.customers_service.enums.Role;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('" + Role.Values.ADMIN + "')")
public @interface IsAdmin {}
