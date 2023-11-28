package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;

import java.util.Optional;

public interface IMedicalRecordRepository {
    Optional<MedicalRecord> findByFirstNameAndLastName(String firstname, String lastname);
    void delete(MedicalRecord medicalRecordToDelete);
    MedicalRecord add(MedicalRecord medicalRecordToAdd);
    MedicalRecord update(MedicalRecord currentMedicalRecord, MedicalRecord medicalRecordToUpdate);
}
