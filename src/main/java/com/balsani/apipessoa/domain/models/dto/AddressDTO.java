package com.balsani.apipessoa.domain.models.dto;

import com.balsani.apipessoa.domain.models.Address;

import jakarta.validation.constraints.NotNull;

public record AddressDTO(
        Long id,
        @NotNull
        String street,
        @NotNull
        String city,
        @NotNull
        String state,
        @NotNull
        String zipCode
) {
    public static AddressDTO fromModel(Address address) {
        return new AddressDTO(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZipCode()
        );
    }
}
