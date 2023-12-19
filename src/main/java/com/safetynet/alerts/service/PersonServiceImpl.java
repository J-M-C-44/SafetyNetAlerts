package com.safetynet.alerts.service;

import com.safetynet.alerts.exception.AlreadyExistsException;
import com.safetynet.alerts.exception.NotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.IPersonRepository;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements IPersonService {
    private static final Logger logger = LogManager.getLogger(PersonServiceImpl.class);
    private final IPersonRepository personRepository;

    public PersonServiceImpl(IPersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Optional<Person> getPerson(String firstname, String lastname) {
        logger.debug("  serv - getPerson - going to find person : firstname = {}, lastname = {}", firstname, lastname);
        return personRepository.findByFirstNameAndLastName(firstname, lastname);
    }

    @Override
    public Person addPerson(Person personToAdd) {
        logger.debug("  serv - addPerson - going to verify if person already exist : firstname = {}, lastname = {}",
                     personToAdd.getFirstName(), personToAdd.getLastName());
        Optional<Person> person = personRepository.findByFirstNameAndLastName(personToAdd.getFirstName(), personToAdd.getLastName());
        if (person.isPresent()) {
            logger.debug("  serv - addPerson KO - person already exist: firstname = {}, lastname = {}",
                         personToAdd.getFirstName(), personToAdd.getLastName());
            throw new AlreadyExistsException("person with these firstname and lastname already exist !");
        }
        logger.debug("  serv - addPerson - going to add person : firstname = {}, lastname = {}",
                     personToAdd.getFirstName(), personToAdd.getLastName());
        return personRepository.add(personToAdd);
    }

    @Override
    public Person updatePerson(Person personToUpdate) {
        logger.debug("  serv - updatePerson going to verify if person exist: firstname = {}, lastname = {}",
                        personToUpdate.getFirstName(), personToUpdate.getLastName());
        Optional<Person> person = personRepository.findByFirstNameAndLastName(personToUpdate.getFirstName(), personToUpdate.getLastName());
        if (person.isEmpty()) {
            logger.debug("  serv - updatePerson KO - person not found: firstname = {}, lastname = {}",
                            personToUpdate.getFirstName(), personToUpdate.getLastName());
            throw new NotFoundException("person not found");
        }
        logger.debug("  serv - updatePerson going to update person : firstname = {}, lastname = {}",
                personToUpdate.getFirstName(), personToUpdate.getLastName());
        Person currentPerson = person.get();
        return personRepository.update(currentPerson, personToUpdate);
    }

    @Override
    public void deletePerson(String firstname, String lastname) {
        logger.debug("  serv - deletePerson going to verify if person exist: firstname = {}, lastname = {}", firstname, lastname);
        Optional<Person> person = personRepository.findByFirstNameAndLastName(firstname, lastname);
        if (person.isEmpty()) {
            logger.debug("  serv - deletePerson KO - person not found: firstname = {}, lastname = {}", firstname, lastname);
            throw new NotFoundException("person not found");
        }
        logger.debug("  serv - deletePerson going to delete person : firstname = {}, lastname = {}", firstname, lastname);
        Person personToDelete = person.get();
        personRepository.delete(personToDelete);
    }

    @Override
    public List<String> getCommunityEmails(String city) {
        logger.debug("  serv - getCommunityEmail - going to find emails for city = {}", city);
        return personRepository.findEmailsBycity(city);
    }

}
