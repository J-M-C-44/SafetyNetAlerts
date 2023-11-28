package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.JsonDataBaseService;
import org.springframework.stereotype.Repository;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .collect(Collectors.toList());

        if (emailsForCity.isEmpty()) {
            logger.debug("    repo - findEmailsBycity not found for city = {}", city);
        } else {
            logger.debug("    repo - findEmailsBycity OK for city = {} -> {} found" , city, emailsForCity.size());
        }
        return emailsForCity;
    }
}
