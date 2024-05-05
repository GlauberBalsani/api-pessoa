package com.balsani.apipessoa;

import com.balsani.apipessoa.domain.models.Person;
import com.balsani.apipessoa.domain.models.dto.PersonDTO;
import com.balsani.apipessoa.repository.AddressRepository;
import com.balsani.apipessoa.repository.PersonRepository;
import com.balsani.apipessoa.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PersonServiceTest {

    @InjectMocks
    PersonService personService;

    @Mock
    PersonRepository personRepository;



    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() {
        PersonDTO personDTO = new PersonDTO(null, "Test", LocalDate.of(2000, 1, 1));

        Person person = new Person();
        person.setName(personDTO.name());
        person.setBirthDate(personDTO.birthDate());

        when(personRepository.save(any(Person.class))).thenReturn(person);

        PersonDTO savedPerson = personService.save(personDTO);

        assertEquals(personDTO.name(), savedPerson.name());
        assertEquals(personDTO.birthDate(), savedPerson.birthDate());
    }

    @Test
    public void testGetById() {
        Long id = 1L;
        Person person = new Person();
        person.setId(id);
        person.setName("Test");
        person.setBirthDate(LocalDate.of(2000, 1, 1));

        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        Optional<Person> foundPerson = personService.getById(id);

        assertEquals(id, foundPerson.get().getId());
        assertEquals("Test", foundPerson.get().getName());
        assertEquals(LocalDate.of(2000, 1, 1), foundPerson.get().getBirthDate());
    }

    @Test
    public void testUpdatePerson() {
        Long id = 1L;
        PersonDTO personDTO = new PersonDTO(null, "Test Updated", LocalDate.of(2000, 1, 1));

        Person person = new Person();
        person.setId(id);
        person.setName("Test");
        person.setBirthDate(LocalDate.of(2000, 1, 1));

        when(personRepository.findById(id)).thenReturn(Optional.of(person));
        when(personRepository.save(any(Person.class))).thenReturn(person);

        PersonDTO updatedPerson = personService.updatePerson(id, personDTO);

        assertEquals(personDTO.name(), updatedPerson.name());
        assertEquals(personDTO.birthDate(), updatedPerson.birthDate());
    }

}
