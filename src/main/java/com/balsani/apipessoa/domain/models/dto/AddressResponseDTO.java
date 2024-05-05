package com.balsani.apipessoa.domain.models.dto;


import com.balsani.apipessoa.domain.models.Address;

public record AddressResponseDTO(
        Long id,
        String street,
        String city,
        String state,
        String zipCode
) {
    public static AddressResponseDTO fromEntity(final Address address) {
        return new AddressResponseDTO(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZipCode()
        );
    }
}
