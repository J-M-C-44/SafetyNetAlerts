package com.safetynet.alerts.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public class MedicalRecordWithoutNameDTO {

    @NotBlank
    @Pattern(regexp = "^(0[1-9]|1[0-2])/(0[1-9]|1[0-9]|2[0-9]|3[0-1])/(19|20)[0-9]{2}$",
            message = "must have a format like MM/dd/yyyy - ex: 06/16/1977")
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;

    public MedicalRecordWithoutNameDTO(String birthdate, List<String> medications, List<String> allergies) {

        this.birthdate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }


    @Override
    public String toString() {
        return "MedicalRecordWithoutNameDTO{" +
                "birthdate=" + birthdate +
                ", medications=" + medications +
                ", allergies=" + allergies +
                '}';
    }
}
