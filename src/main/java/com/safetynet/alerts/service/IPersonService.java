package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;

import java.io.IOException;
import java.util.Optional;

public interface IPersonService {
    // sélectionner 1 personne
    public Optional<Person> getPerson(String firstname, String lastname) throws IOException;

    // ajouter  1 personne
    public Person addPerson(Person personToSave) throws IOException;

    // maj  1 personne
    public Person updatePerson(Person currentPerson, Person personToSave) throws IOException;

    // supprimer  1 personne
    public void deletePerson(Person personToDelete) throws IOException;



}
