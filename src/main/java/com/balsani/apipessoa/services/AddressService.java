package com.balsani.apipessoa.services;

import com.balsani.apipessoa.domain.models.Address;
import com.balsani.apipessoa.domain.models.Person;
import com.balsani.apipessoa.domain.models.dto.AddressDTO;
import com.balsani.apipessoa.exceptions.ResourceNotFoundException;
import com.balsani.apipessoa.repository.AddressRepository;
import com.balsani.apipessoa.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final PersonRepository personRepository;

    public AddressService(AddressRepository addressRepository, PersonRepository personRepository) {
        this.addressRepository = addressRepository;
        this.personRepository = personRepository;
    }

    @Transactional
    public AddressDTO createAddress(Long personId, AddressDTO addressRequestDTO) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person with id " + personId + " not found"));
        Address address = new Address();
        address.setStreet(addressRequestDTO.street());
        address.setCity(addressRequestDTO.city());
        address.setNumber(addressRequestDTO.number());
        address.setState(addressRequestDTO.state());
        address.setZipCode(addressRequestDTO.zipCode());
        if(person.getMainAddress() == null) {
            person.setMainAddress(address);
        }
        address.setPerson(person);
        address = addressRepository.save(address);
        return AddressDTO.fromModel(address);
    }



    public AddressDTO getAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address with id " + id + " not found"));
        return AddressDTO.fromModel(address);
    }


    public List<Address> listAddresses() {
        return addressRepository.findAll();
    }

    public List<Address> listAddressesByPerson(Long personId) {
        return addressRepository.findAllByPersonId(personId);
    }

    @Transactional
    public AddressDTO updateAddress(Long id, AddressDTO addressRequestDTO) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address with id " + id + " not found"));
        address.setStreet(addressRequestDTO.street());
        address.setCity(addressRequestDTO.city());
        address.setState(addressRequestDTO.state());
        address.setZipCode(addressRequestDTO.zipCode());
        address = addressRepository.save(address);
        return AddressDTO.fromModel(address);
    }

    @Transactional
    public void deleteAddress(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new ResourceNotFoundException("Address with id " + id + " not found");
        }
        addressRepository.deleteById(id);
    }

    @Transactional
    public AddressDTO setMainAddress(Long personId, Long addressId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person with id " + personId + " not found"));
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address with id " + addressId + " not found"));
        person.setMainAddress(address);
        personRepository.save(person);
        return AddressDTO.fromModel(address);
    }
}
