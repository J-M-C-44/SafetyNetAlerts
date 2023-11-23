package com.safetynet.alerts.service;

import com.safetynet.alerts.exception.AlreadyExistsException;
import com.safetynet.alerts.exception.NotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.IPersonRepository;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.Optional;

@Service
public class PersonServiceImpl implements IPersonService {
    private static final Logger logger = LogManager.getLogger(PersonServiceImpl.class);
    private final IPersonRepository personRepository;

    public PersonServiceImpl(IPersonRepository personRepository) {
        this.personRepository = personRepository;
    }

//    public Person getPerson(String firstname, String lastname) throws NoContainException, IOException {
//        logger.debug("  serv - getPerson - going to find person : firstname = {}, lastname = {}", firstname, lastname);
//        Optional<Person> person = personRepository.findByFirstNameAndLastName(firstname, lastname);
//        if (person.isPresent()) {
//            logger.debug("  serv - getPerson - find person OK for firstname = {}, lastname = {}", firstname, lastname);
//            return person.get();
//        } else {
//            logger.debug("  serv - getPerson KO - person not found: firstname = {}, lastname = {}", firstname, lastname);
//           //throw new NotFoundException("person not found"); --> pas une exception / revoir le if du dessus du coup
//            throw new NoContainException();
//        }
    public Optional<Person> getPerson(String firstname, String lastname) {
        logger.debug("  serv - getPerson - going to find person : firstname = {}, lastname = {}", firstname, lastname);
        return personRepository.findByFirstNameAndLastName(firstname, lastname);
//        if (person.isPresent()) {
//            logger.debug("  serv - getPerson - find person OK for firstname = {}, lastname = {}", firstname, lastname);
//            return person.get();
//        } else {
//            logger.debug("  serv - getPerson KO - person not found: firstname = {}, lastname = {}", firstname, lastname);
//            //throw new NotFoundException("person not found"); --> pas une exception / revoir le if du dessus du coup
//            throw new NoContainException();

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
        } else {
            logger.debug("  serv - addPerson - going to add person : firstname = {}, lastname = {}",
                         personToAdd.getFirstName(), personToAdd.getLastName());
            return personRepository.add(personToAdd);
        }

    }
    @Override
    public Person updatePerson(String firstname, String lastname, Person personToUpdate) {
        logger.debug("  serv - updatePerson going to verify if person exist: firstname = {}, lastname = {}", firstname, lastname);
        Optional<Person> person = personRepository.findByFirstNameAndLastName(firstname, lastname);
        if (person.isPresent()) {
            logger.debug("  serv - updatePerson going to update person : firstname = {}, lastname = {}", firstname, lastname);
            Person currentPerson = person.get();
            return personRepository.update(currentPerson, personToUpdate);
        } else {
            logger.debug("  serv - updatePerson KO - person not found: firstname = {}, lastname = {}", firstname, lastname);
            throw new NotFoundException("person not found");
        }
    }

    @Override
    public void deletePerson(String firstname, String lastname) {
        logger.debug("  serv - deletePerson going to verify if person exist: firstname = {}, lastname = {}", firstname, lastname);
        Optional<Person> person = personRepository.findByFirstNameAndLastName(firstname, lastname);
        if (person.isPresent()) {
            logger.debug("  serv - deletePerson going to delete person : firstname = {}, lastname = {}", firstname, lastname);
            Person personToDelete = person.get();
            personRepository.delete(personToDelete);
        } else {
            logger.debug("  serv - deletePerson KO - person not found: firstname = {}, lastname = {}", firstname, lastname);
            throw new NotFoundException("person not found");
        }
    }
}
