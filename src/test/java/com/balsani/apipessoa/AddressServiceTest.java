package com.balsani.apipessoa.services;

import com.balsani.apipessoa.domain.models.Address;
import com.balsani.apipessoa.domain.models.Person;
import com.balsani.apipessoa.domain.models.dto.AddressDTO;
import com.balsani.apipessoa.exceptions.ResourceNotFoundException;
import com.balsani.apipessoa.repository.AddressRepository;
import com.balsani.apipessoa.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private AddressService addressService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateAddress() {
        AddressDTO addressDTO = new AddressDTO(null, "Street", "Number", "City", "State", "12345");
        Person person = new Person();
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        when(addressRepository.save(any(Address.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AddressDTO result = addressService.createAddress(1L, addressDTO);

        assertEquals("Street", result.street());
        assertEquals("Number", result.number());
        assertEquals("City", result.city());
        assertEquals("State", result.state());
        assertEquals("12345", result.zipCode());
        assertEquals(person, person.getMainAddress().getPerson());
    }

    @Test
    public void testGetAddressById() {
        Address address = new Address();
        address.setId(1L);
        address.setStreet("Street");
        address.setNumber("Number");
        address.setCity("City");
        address.setState("State");
        address.setZipCode("12345");

        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

        AddressDTO result = addressService.getAddressById(1L);

        assertEquals(address.getId(), result.id());
        assertEquals(address.getStreet(), result.street());
        assertEquals(address.getNumber(), result.number());
        assertEquals(address.getCity(), result.city());
        assertEquals(address.getState(), result.state());
        assertEquals(address.getZipCode(), result.zipCode());
    }

    @Test
    public void testDeleteAddress() {
        when(addressRepository.existsById(1L)).thenReturn(true);

        addressService.deleteAddress(1L);

        verify(addressRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteAddressNotFound() {
        when(addressRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> addressService.deleteAddress(1L));
    }
}
