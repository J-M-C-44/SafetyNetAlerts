package com.safetynet.alerts.controller.request;

import com.safetynet.alerts.model.Person;

import java.util.List;

public class ChildAlertDTO {
    private final String firstName;
    private final String lastName;
    private final Integer age;
    private final List<Person> homeMembers;

    public ChildAlertDTO(String firstName, String lastName, Integer age, List<Person> homeMembers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.homeMembers = homeMembers;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public Integer getAge() {
        return age;
    }

    public List<Person> getHomeMembers() {
        return homeMembers;
    }

    @Override
    public String toString() {
        return "ChildAlertDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", homeMembers=" + homeMembers +
                '}';
    }
}
