package com.balsani.apipessoa.controller;

import com.balsani.apipessoa.domain.models.Address;
import com.balsani.apipessoa.domain.models.dto.AddressDTO;
import com.balsani.apipessoa.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/{personId}/address")
    public ResponseEntity<AddressDTO> create(@PathVariable Long personId, @RequestBody @Valid AddressDTO request,
                                             UriComponentsBuilder uriBuilder) {
        AddressDTO body = addressService.createAddress(personId, request);
        URI uri = uriBuilder.path("/v1/person/{personId}/address/{id}").buildAndExpand(personId, body.id()).toUri();
        return ResponseEntity.created(uri).body(body);
    }


    @GetMapping
    public ResponseEntity<List<Address>> getAll() {
        List<Address> addresses = addressService.listAddresses();
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDTO> getById(@PathVariable Long addressId) {
        AddressDTO address = addressService.getAddressById(addressId);
        return ResponseEntity.ok(address);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDTO> update(@PathVariable Long addressId, @RequestBody @Valid AddressDTO request) {
        AddressDTO body = addressService.updateAddress(addressId, request);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> delete(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{personId}/setMain/{addressId}")
    public ResponseEntity<Void> setMainAddress(@PathVariable Long personId, @PathVariable Long addressId) {
        addressService.setMainAddress(personId, addressId);
        return ResponseEntity.noContent().build();
    }
}
