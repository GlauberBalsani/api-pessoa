package com.balsani.apipessoa.domain.models.dto;

import com.balsani.apipessoa.domain.models.Person;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record PersonDTO(
        Long id,

        @NotNull
        String name,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // yyyy-MM-dd
        LocalDate birthDate

) {
    public static PersonDTO fromEntity(Person person) {
        return new PersonDTO(
                person.getId(),
                person.getName(),
                person.getBirthDate()
        );
    }
}
