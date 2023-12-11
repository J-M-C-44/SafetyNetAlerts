package com.safetynet.alerts.controller.request.medicalrecord;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public class MedicalRecordDTO {
    @NotBlank
    private final String firstName;
    @NotBlank
    private final String lastName;
    @Pattern(regexp = "^(0[1-9]|1[0-2])/(0[1-9]|1[0-9]|2[0-9]|3[0-1])/(19|20)[0-9]{2}$",
             message = "must have a format like MM/dd/yyyy ! - ex: 06/16/1977")
    private final String birthdate;
    private final List<String> medications;
    private final List<String> allergies;

    public MedicalRecordDTO(String firstName, String lastName, String birthdate, List<String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public List<String> getMedications() {
        return medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    @Override
    public String toString() {
        return "MedicalRecordDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate=" + birthdate +
                ", medications=" + medications +
                ", allergies=" + allergies +
                '}';
    }
}

