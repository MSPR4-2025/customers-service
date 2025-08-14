package io.github.mspr4_2025.customers_service.model.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreateDto {
    private String name;

    private String username;

    private String firstName;

    private String lastName;

    private Address address;

    private Profile profile;

    private Company company;

    @Getter
    @Setter
    public static class Address {
        private String postalCode;

        private String city;
    }

    @Getter
    @Setter
    public static class Profile {
        private String firstName;

        private String lastName;
    }

    @Getter
    @Setter
    public static class Company {
        private String companyName;
    }
}
