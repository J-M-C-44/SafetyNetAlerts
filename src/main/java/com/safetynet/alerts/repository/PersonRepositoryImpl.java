package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.JsonDataBaseService;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepositoryImpl implements IPersonRepository {
//    @Autowired
//    JsonDataBaseService jsonDataBaseService;

    private final JsonDataBaseService jsonDataBaseService;

    public PersonRepositoryImpl(JsonDataBaseService jsonDataBaseService) {
        this.jsonDataBaseService = jsonDataBaseService;
    }

    @Override
    public Optional<Person> findByFirstNameAndLastName(String firstname, String lastname) {
        System.out.println("personRespository - findByFirstNameAndLastName "+firstname+lastname );
        List<Person> persons = jsonDataBaseService.getPersons();
        for (Person person: persons) {
            //System.out.println(person.toString());
            if (person.getFirstName().equals(firstname) && person.getLastName().equals(lastname)) {
                return Optional.of(person); }
        }

        return Optional.empty();
    }

    @Override
    public void delete(Person personToDelete) throws IOException {
        System.out.println("personRespository - deleteByFirstNameAndLastName "+ personToDelete );
        List<Person> persons = jsonDataBaseService.getPersons();
        persons.remove(personToDelete);
        jsonDataBaseService.saveDataBaseInFile();

    }

    @Override
    public Person add(Person personToAdd) throws IOException {
        System.out.println("personRespository - add "
                +personToAdd.getFirstName() +" "
                +personToAdd.getLastName() +" "
                +personToAdd.getAddress() +" "
                +personToAdd.getCity() +" "
                +personToAdd.getZip() +" "
                +personToAdd.getPhone() +" "
                +personToAdd.getEmail() +" ");
        List<Person> persons = jsonDataBaseService.getPersons();
        persons.add(personToAdd);
        jsonDataBaseService.saveDataBaseInFile();
        return personToAdd;
    }

    @Override
    public Person update(Person currentPerson, Person personToUpdate) throws IOException {
        System.out.println("personRespository - update "+personToUpdate );
        List<Person> persons = jsonDataBaseService.getPersons();
        persons.set(persons.indexOf(currentPerson), personToUpdate);
        jsonDataBaseService.saveDataBaseInFile();
        return personToUpdate;
    }
}
