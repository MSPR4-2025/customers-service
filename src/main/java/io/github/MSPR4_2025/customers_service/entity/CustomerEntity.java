package io.github.MSPR4_2025.customers_service.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "customers")
@EntityListeners(AuditingEntityListener.class) // Necessary for @CreatedAt
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private UUID uid = UUID.randomUUID();

    private String name;

    private String username;

    private String firstName;

    private String lastName;

    @Embedded
    private Address address;

    @Embedded
    private Profile profile;

    @Embedded
    private Company company;

    @CreatedDate
    private Instant createdAt;

    @Getter
    @Setter
    @Embeddable
    public static class Address {
        private String postalCode;

        private String city;
    }

    @Getter
    @Setter
    @Embeddable
    public static class Profile {
        private String firstName;

        private String lastName;
    }

    @Getter
    @Setter
    @Embeddable
    public static class Company {
        private String companyName;
    }
}
