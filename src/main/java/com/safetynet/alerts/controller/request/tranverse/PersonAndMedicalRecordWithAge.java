package com.safetynet.alerts.controller.request.tranverse;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

public class PersonAndMedicalRecordWithAge {
    private final Person person;
    private MedicalRecord medicalRecord;
    private Integer age;

    public PersonAndMedicalRecordWithAge(Person person) {
        this.person = person;
        this.medicalRecord = new MedicalRecord();
        this.age = 0;
    }

    public PersonAndMedicalRecordWithAge(Person person, MedicalRecord medicalRecord, Integer age) {
        this.person = person;
        this.medicalRecord = medicalRecord;
        this.age = age;
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

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
