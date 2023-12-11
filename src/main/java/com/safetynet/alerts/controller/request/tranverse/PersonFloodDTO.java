package com.safetynet.alerts.controller.request.tranverse;

import java.util.List;

public class PersonFloodDTO {
    private final String firstName;
    private final String lastName;
    private final String phone;
    private final Integer age;
    private final List<String> medications;
    private final List<String> allergies;

    public PersonFloodDTO(String firstName, String lastName, String phone, Integer age, List<String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
    }

    @Override
    public String toString() {
        return "PersonFloodDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", medications=" + medications +
                ", allergies=" + allergies +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
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
}
