package com.safetynet.alerts.controller.request.tranverse;

import com.safetynet.alerts.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonsCoveredByStation {
    private List<Person> persons;
    private Integer adultsCount;
    private Integer childrenCount;

    public PersonsCoveredByStation() {
        this.persons = new ArrayList<>();
        this.adultsCount = 0;
        this.childrenCount = 0;
    }

    public PersonsCoveredByStation(List<Person> persons, Integer adultsCount, Integer childrenCount) {
        this.persons = persons;
        this.adultsCount = adultsCount;
        this.childrenCount = childrenCount;
    }

    public void addPerson(Person person) {
        this.persons.add(person);
    }
    public void incrementAdults() {
        this.adultsCount++;
    }
    public void incrementChildren() {
        this.childrenCount++;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public Integer getAdultsCount() {
        return adultsCount;
    }

    public Integer getChildrenCount() {
        return childrenCount;
    }

}
