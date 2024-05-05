package com.balsani.apipessoa.controller;

import com.balsani.apipessoa.domain.models.Person;
import com.balsani.apipessoa.domain.models.dto.AddressDTO;
import com.balsani.apipessoa.domain.models.dto.PersonDTO;
import com.balsani.apipessoa.domain.models.dto.PersonResponseDTO;
import com.balsani.apipessoa.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<PersonDTO> create(@RequestBody @Valid PersonDTO request,
                                            UriComponentsBuilder uriBuilder) {
        PersonDTO body = personService.save(request);
        URI uri = uriBuilder.path("/v1/person/{id}").buildAndExpand(body.id()).toUri();
        return ResponseEntity.created(uri).body(body);
    }

    @GetMapping
    public ResponseEntity<List<PersonResponseDTO>> getAll() {
        List<PersonResponseDTO> persons = personService.getAll();
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/{personId}")
    public ResponseEntity<Optional<Person>> getById(@PathVariable("personId") Long personId) {
        var person = personService.getById(personId);

        return ResponseEntity.ok(person);
    }

    @PutMapping("/{personId}")
    public ResponseEntity<PersonDTO> update(@PathVariable Long personId, @RequestBody @Valid PersonDTO request) {
        PersonDTO body = personService.updatePerson(personId,request);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity delete(@PathVariable Long personId) {
        personService.deletePerson(personId);
        return ResponseEntity.noContent().build();
    }





}
