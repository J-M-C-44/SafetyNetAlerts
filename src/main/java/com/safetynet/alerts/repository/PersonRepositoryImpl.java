package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.JsonDataBaseService;
import org.springframework.stereotype.Repository;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepositoryImpl implements IPersonRepository {
    private static final Logger logger = LogManager.getLogger(PersonRepositoryImpl.class);
    private final JsonDataBaseService jsonDataBaseService;

    public PersonRepositoryImpl(JsonDataBaseService jsonDataBaseService) {
        this.jsonDataBaseService = jsonDataBaseService;
    }

    @Override
    public Optional<Person> findByFirstNameAndLastName(String firstname, String lastname) {
        logger.debug("    repo - findByFirstNameAndLastName : firstname = {}, lastname = {}", firstname, lastname);
        List<Person> persons = jsonDataBaseService.getPersons();
        for (Person person: persons) {
            if (person.getFirstName().equals(firstname) && person.getLastName().equals(lastname)) {
                logger.debug("    repo - findByFirstNameAndLastName OK for: firstname = {}, lastname = {}", firstname, lastname);
                return Optional.of(person); }
        }
        logger.debug("    repo - findByFirstNameAndLastName not found for firstname = {}, lastname = {}", firstname, lastname);
        return Optional.empty();
    }

    @Override
    public List<Person> findByAddress(String address) {
        logger.debug("    repo - findByAddress : address = {}", address);

        List<Person> persons = jsonDataBaseService.getPersons()
                .stream()
                .filter(person -> person.getAddress().equals(address))
                .toList();
        if (persons.isEmpty()) {
            logger.debug("    repo - findByAddress not found for address = {}", address);
        } else {
            logger.debug("    repo - findByAddress OK for address = {} -> {} found" , address, persons.size());
        }
        return persons;
    }

    @Override
    public List<Person> findAllByFirstNameAndLastName(String firstName, String lastName) {
        logger.debug("    repo - findAllByFirstNameAndLastName : firstname = {}, lastname = {}", firstName, lastName);

        List<Person> persons = jsonDataBaseService.getPersons()
                .stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .toList();

        if (persons.isEmpty()) {
            logger.debug("    repo - findAllByFirstNameAndLastName not found for firstname = {}, lastname = {}", firstName, lastName);
        } else {
            logger.debug("    repo - findAllByFirstNameAndLastName OK for: firstname = {}, lastname = {}", firstName, lastName);
        }
        return persons;
    }

    @Override
    public void delete(Person personToDelete) {
        logger.debug("    repo -  delete  person : {}", personToDelete);
        List<Person> persons = jsonDataBaseService.getPersons();
        persons.remove(personToDelete);
        jsonDataBaseService.saveDataBaseInFile();
        logger.debug("    repo -  delete  OK person : {} ", personToDelete);

    }

    @Override
    public Person add(Person personToAdd) {
        logger.debug("    repo -  add  person : {}", personToAdd);
        List<Person> persons = jsonDataBaseService.getPersons();
        persons.add(personToAdd);
        jsonDataBaseService.saveDataBaseInFile();
        logger.debug("    repo -  add person OK for {}",  personToAdd);
        return personToAdd;
    }

    @Override
    public Person update(Person currentPerson, Person personToUpdate){
        logger.debug("    repo -  update person : {}", personToUpdate);
        List<Person> persons = jsonDataBaseService.getPersons();
        persons.set(persons.indexOf(currentPerson), personToUpdate);
        jsonDataBaseService.saveDataBaseInFile();
        logger.debug("  repo -  update person OK for {}", personToUpdate);
        return personToUpdate;
    }

    @Override
    public List<String> findEmailsBycity(String city) {
        logger.debug("    repo - findEmailsBycity : city = {}", city);

        List<String> emailsForCity = jsonDataBaseService.getPersons()
                .stream()
                .filter(person -> person.getCity().equals(city))
//                .map(Person -> Person.getEmail())
                .map(Person::getEmail)
                .distinct()
                .toList();

        if (emailsForCity.isEmpty()) {
            logger.debug("    repo - findEmailsBycity not found for city = {}", city);
        } else {
            logger.debug("    repo - findEmailsBycity OK for city = {} -> {} found" , city, emailsForCity.size());
        }
        return emailsForCity;
    }
}
