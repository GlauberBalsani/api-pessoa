package com.balsani.apipessoa.repository;

import com.balsani.apipessoa.domain.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    public boolean existsByName(String name);
}
