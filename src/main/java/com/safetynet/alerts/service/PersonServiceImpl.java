package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class PersonServiceImpl implements IPersonService {
    @Autowired
    private IPersonRepository personRepository;

    public Optional<Person> getPerson(String firstname, String lastname) throws IOException {
        //return Optional.empty();
        return personRepository.findByFirstNameAndLastName(firstname, lastname);
    }

    @Override
    public Person addPerson(Person personToAdd) throws IOException {
        return personRepository.add(personToAdd);
    }
    @Override
    public Person updatePerson(Person currentPerson, Person personToUpdate) throws IOException {
        return personRepository.update(currentPerson, personToUpdate);
    }

    @Override
    public void deletePerson(Person personToDelete) throws IOException {
        personRepository.delete(personToDelete);
    }
}
