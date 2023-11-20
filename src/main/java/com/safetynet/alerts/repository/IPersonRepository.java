package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;

import java.io.IOException;
import java.util.Optional;

public interface IPersonRepository {
    Optional<Person> findByFirstNameAndLastName (String firstname, String lastname) throws IOException;
    void delete (Person personToDelete) throws IOException;
    Person add (Person personToAdd) throws IOException;
    Person update (Person currentPerson, Person personToUpdate) throws IOException;


}
