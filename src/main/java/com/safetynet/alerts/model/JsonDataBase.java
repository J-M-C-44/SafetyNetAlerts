package com.safetynet.alerts.model;

import java.util.List;

public class JsonDataBase {
    private List<Person> persons;
    private List<Firestation> firestations;
    private List<Medicalrecord> medicalrecords;


    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Firestation> getFirestations() {
        return firestations;
    }

    public void setFireStations(List<Firestation> firestations) {
        this.firestations = firestations;
    }

    public List<Medicalrecord> getMedicalrecords() {
        return medicalrecords;
    }

    public void setMedicalRecords(List<Medicalrecord> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }

}
