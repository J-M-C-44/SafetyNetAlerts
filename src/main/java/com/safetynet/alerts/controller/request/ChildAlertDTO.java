package com.safetynet.alerts.controller.request;

import com.safetynet.alerts.model.Person;

import java.util.List;

public class ChildAlertDTO {
    private String firstName;
    private String lastName;
    private Integer age;
    private List<Person> homeMembers;

    public ChildAlertDTO(String firstName, String lastName, Integer age, List<Person> homeMembers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.homeMembers = homeMembers;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Person> getHomeMembers() {
        return homeMembers;
    }

    public void setHomeMembers(List<Person> homeMembers) {
        this.homeMembers = homeMembers;
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
