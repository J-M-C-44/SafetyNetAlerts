package com.safetynet.alerts.controller.request;

import java.util.List;

public class PersonInfoDTO {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final Integer age;
    private final List<String> medications;
    private final List<String> allergies;

    public PersonInfoDTO(String firstName, String lastName, String email, Integer age, List<String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

    public List<String> getMedications() {
        return medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    @Override
    public String toString() {
        return "PersonInfoDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", medications=" + medications +
                ", allergies=" + allergies +
                '}';
    }
}
