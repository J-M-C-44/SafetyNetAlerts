package com.safetynet.alerts.controller.request;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

import java.util.List;

public class ChildAndHomeMembers {
    private MedicalRecord medicalRecord;
    private Integer childAge;
    private List<Person> otherHomeMembers;

    public ChildAndHomeMembers(MedicalRecord medicalRecord, Integer childAge, List<Person> otherHomeMembers) {
        this.medicalRecord = medicalRecord;
        this.childAge = childAge;
        this.otherHomeMembers = otherHomeMembers;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public Integer getChildAge() {
        return childAge;
    }

    public List<Person> getOtherHomeMembers() {
        return otherHomeMembers;
    }

}
