package com.safetynet.alerts.service;

import com.safetynet.alerts.exception.AlreadyExistsException;
import com.safetynet.alerts.exception.NoContainException;
import com.safetynet.alerts.exception.NotFoundException;
import com.safetynet.alerts.model.Person;

import java.io.IOException;


public interface IPersonService {
    // sélectionner 1 personne
    Person getPerson(String firstname, String lastname) throws IOException, NotFoundException, NoContainException;

    // ajouter  1 personne
    Person addPerson(Person personToSave) throws IOException, AlreadyExistsException;

    // maj  1 personne
    Person updatePerson(String firstname, String lastname, Person personToSave) throws IOException, NotFoundException;

    // supprimer  1 personne
    void deletePerson(String firstname, String lastname) throws IOException, NotFoundException;



}
