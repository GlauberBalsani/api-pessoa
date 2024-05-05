package com.balsani.apipessoa.repository;

import com.balsani.apipessoa.domain.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByPersonId(Long personId);
}
