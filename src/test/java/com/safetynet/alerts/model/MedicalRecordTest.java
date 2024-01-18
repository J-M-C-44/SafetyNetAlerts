package com.safetynet.alerts.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MedicalRecordTest {

    @Test
    void toString_ShouldCreateMedicalRecordAndDisplayAttributes() {
        //act
        MedicalRecord medicalRecord = new MedicalRecord("Harry","Covert","12/31/2000", List.of("suppo:1u", "paracetamol:500mg"), List.of("bouleau"));
        String result= medicalRecord.toString();
        String expected = "MedicalRecord{firstName='Harry', lastName='Covert', birthdate='12/31/2000', medications=[suppo:1u, paracetamol:500mg], allergies=[bouleau]}";

        //assert
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void settersAndGuettersMedicalRecord_ShouldCreateMedicalrecordAndSetAndGetAttributes() {
        //act
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("Harry");
        medicalRecord.setLastName("Covert");
        medicalRecord.setBirthdate("12/31/2000");
        medicalRecord.setMedications(List.of("suppo:1u", "paracetamol:500mg"));
        medicalRecord.setAllergies(List.of("bouleau"));
        String resultFirstName = medicalRecord.getFirstName();
        String resultLastName = medicalRecord.getLastName();
        String resultBirthdate = medicalRecord.getBirthdate();
        List<String> resultMedications = medicalRecord.getMedications();
        List<String> resultAllergies = medicalRecord.getAllergies();

        //assert
        assertThat(resultFirstName).isEqualTo("Harry");
        assertThat(resultLastName).isEqualTo("Covert");
        assertThat(resultBirthdate).isEqualTo("12/31/2000");
        assertThat(resultMedications).isEqualTo(List.of("suppo:1u", "paracetamol:500mg"));
        assertThat(resultAllergies).isEqualTo(List.of("bouleau"));
    }

}