package com.balsani.apipessoa.domain.models.dto;

import com.balsani.apipessoa.domain.models.Person;

import java.time.LocalDate;
import java.util.List;

public record PersonResponseDTO(
        Long id,
        String name,
        LocalDate birthDate

) {
    public static PersonResponseDTO toDto(Person person) {
        return new PersonResponseDTO(
                person.getId(),
                person.getName(),
                person.getBirthDate()
        );
    }
}
