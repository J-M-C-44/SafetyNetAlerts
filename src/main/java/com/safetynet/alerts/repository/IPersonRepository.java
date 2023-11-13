package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;

import java.io.IOException;
import java.util.Optional;

public interface IPersonRepository {
    public Optional<Person> findByFirstNameAndLastName (String firstname, String lastname) throws IOException;
    public void delete (Person personToDelete) throws IOException;
    public Person add (Person personToAdd) throws IOException;
    public Person update (Person currentPerson, Person personToUpdate) throws IOException;


}
