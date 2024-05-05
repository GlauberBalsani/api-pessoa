package com.balsani.apipessoa.services;

import com.balsani.apipessoa.domain.models.Person;
import com.balsani.apipessoa.domain.models.dto.PersonDTO;
import com.balsani.apipessoa.domain.models.dto.PersonResponseDTO;
import com.balsani.apipessoa.exceptions.ResourceNotFoundException;
import com.balsani.apipessoa.repository.AddressRepository;
import com.balsani.apipessoa.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;

    public PersonService(PersonRepository personRepository, AddressRepository addressRepository) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public PersonDTO save(PersonDTO personRequestDTO) {
        Person person = new Person();
        person.setName(personRequestDTO.name());
        person.setBirthDate(personRequestDTO.birthDate());

        Person personSaved = personRepository.save(person);

        return PersonDTO.fromEntity(personSaved);
    }

    public List<PersonResponseDTO> getAll() {
        return personRepository
                .findAll()
                .stream()
                .map(person -> new PersonResponseDTO(
                        person.getId(),
                        person.getName(),
                        person.getBirthDate()))
                .collect(Collectors.toList());
    }


    public Optional<Person> getById(Long id) {
        var person = personRepository.findById(id);

        if (!person.isPresent()) {
            throw new ResourceNotFoundException("Pessoa não encontrada com o ID: " + id);

        }
        return person;

    }


    @Transactional
    public PersonDTO updatePerson(Long id, PersonDTO personRequestDTO) {
        var person = personRepository.findById(id);
        if (!person.isPresent()) {
            throw new ResourceNotFoundException("Person with id " + id + " not found");
        }

        person.map(p -> {
            p.setName(personRequestDTO.name());
            p.setBirthDate(personRequestDTO.birthDate());

            return personRepository.save(p);
        });
        return personRequestDTO;
    }

    @Transactional
    public void deletePerson(Long id) {
        var person = personRepository.findById(id);
        if (person.isPresent()) {
            personRepository.delete(person.get());
        }
        personRepository.deleteById(id);
    }


    @Transactional
    public void setMainAddress(Long userId, Long addressId) {
        var person = personRepository.findById(userId);
        if (!person.isPresent()) {
            throw new ResourceNotFoundException("Person with id " + userId + " not found");
        }
        var address = addressRepository.findById(addressId);
        if (!address.isPresent()) {
            throw new ResourceNotFoundException("Address with id " + addressId + " not found");
        }
        person.get().setMainAddress(address.get());
    }


}
