package com.safetynet.alerts.controller.request;

import java.util.List;

public class FireDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private Integer age;
    private List<String> medications;
    private List<String> allergies;
    private String stationNumber;

    public FireDTO(String firstName, String lastName, String phone, Integer age, List<String> medications, List<String> allergies, String stationNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
        this.stationNumber = stationNumber;
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

    public String getStationNumber() {
        return stationNumber;
    }

    @Override
    public String toString() {
        return "FireDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", medications=" + medications +
                ", allergies=" + allergies +
                ", stationNumber='" + stationNumber + '\'' +
                '}';
    }
}
