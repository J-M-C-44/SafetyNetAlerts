package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;

import java.util.Optional;

public interface IMedicalRecordService {
    // sélectionner 1 medicalrecord
    Optional<MedicalRecord> getMedicalRecord(String firstname, String lastname);

    // ajouter  1 medicalrecord
    MedicalRecord addMedicalRecord(MedicalRecord medicalRecordToSave);

    // maj  1 medicalrecord
    MedicalRecord updateMedicalRecord(MedicalRecord medicalRecordToSave);

    // supprimer  1 medicalrecord
    void deleteMedicalRecord(String firstname, String lastname);
}
