package com.safetynet.alerts.service;


import com.safetynet.alerts.model.Person;

import java.util.Optional;


public interface IPersonService {
    // sélectionner 1 personne
    Optional<Person> getPerson(String firstname, String lastname);

    // ajouter  1 personne
    Person addPerson(Person personToSave);

    // maj  1 personne
    Person updatePerson(String firstname, String lastname, Person personToSave);

    // supprimer  1 personne
    void deletePerson(String firstname, String lastname);



}
