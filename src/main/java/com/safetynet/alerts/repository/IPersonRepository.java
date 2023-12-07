package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;

import java.util.List;
import java.util.Optional;

public interface IPersonRepository {
    Optional<Person> findByFirstNameAndLastName(String firstname, String lastname);
    List<Person> findByAddress(String address);
    List<Person> findAllByFirstNameAndLastName(String firstName, String lastName);

    void delete(Person personToDelete);
    Person add(Person personToAdd);
    Person update(Person currentPerson, Person personToUpdate);

    List<String> findEmailsBycity(String city);
}
