package com.safetynet.alerts.service;

import com.safetynet.alerts.exception.AlreadyExistsException;
import com.safetynet.alerts.exception.NoContainException;
import com.safetynet.alerts.exception.NotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.IPersonRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class PersonServiceImpl implements IPersonService {
    private final IPersonRepository personRepository;

    public PersonServiceImpl(IPersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person getPerson(String firstname, String lastname) throws NotFoundException, NoContainException, IOException {
        Optional<Person> person = personRepository.findByFirstNameAndLastName(firstname, lastname);
        if (person.isPresent()) {
            return person.get();
        } else {
           //throw new NotFoundException("person not found");
            throw new NoContainException();
        }
    }

    @Override
    public Person addPerson(Person personToAdd) throws IOException, AlreadyExistsException {
        Optional<Person> person = personRepository.findByFirstNameAndLastName(personToAdd.getFirstName(), personToAdd.getLastName());
        if (person.isPresent()) {
            throw new AlreadyExistsException("person with these firstname and lastname already exist !");
        } else {
            return personRepository.add(personToAdd);
        }

    }
    @Override
    public Person updatePerson(String firstname, String lastname, Person personToUpdate) throws IOException, NotFoundException {
        Optional<Person> person = personRepository.findByFirstNameAndLastName(firstname, lastname);
        if (person.isPresent()) {
            Person currentPerson = person.get();
            return personRepository.update(currentPerson, personToUpdate);
        } else {
            throw new NotFoundException("person not found");
        }
    }

    @Override
    public void deletePerson(String firstname, String lastname) throws IOException, NotFoundException {

        Optional<Person> person = personRepository.findByFirstNameAndLastName(firstname, lastname);
        if (person.isPresent()) {
            Person personToDelete = person.get();
            personRepository.delete(personToDelete);
        } else {
            throw new NotFoundException("person not found");
        }
    }
}
