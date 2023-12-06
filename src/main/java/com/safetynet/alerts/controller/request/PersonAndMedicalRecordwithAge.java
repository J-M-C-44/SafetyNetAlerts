package com.safetynet.alerts.controller.request;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

public class PersonAndMedicalRecordwithAge {
    private Person person;
    private MedicalRecord medicalRecord;
    private Integer age;
    private String station;

    public PersonAndMedicalRecordwithAge(Person person, MedicalRecord medicalRecord, Integer age, String station) {
        this.person = person;
        this.medicalRecord = medicalRecord;
        this.age = age;
        this.station = station;
    }
    public PersonAndMedicalRecordwithAge(Person person, String station) {
        this.person = person;
        this.medicalRecord = new MedicalRecord();
        this.age = 0;
        this.station = station;
    }

    public Person getPerson() {
        return person;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public Integer getAge() {
        return age;
    }

    public String getStation() {
        return station;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
