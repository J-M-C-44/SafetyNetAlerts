package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.JsonDataBaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MedicalRecordRepositoryImpl implements IMedicalRecordRepository{
    private static final Logger logger = LogManager.getLogger(MedicalRecordRepositoryImpl.class);
    private final JsonDataBaseService jsonDataBaseService;

    public MedicalRecordRepositoryImpl(JsonDataBaseService jsonDataBaseService) {
        this.jsonDataBaseService = jsonDataBaseService;
    }


    @Override
    public Optional<MedicalRecord> findByFirstNameAndLastName(String firstname, String lastname) {
        logger.debug("    repo - findByFirstNameAndLastName : firstname = {}, lastname = {}", firstname, lastname);
        List<MedicalRecord> medicalRecords = jsonDataBaseService.getMedicalRecords();
        for (MedicalRecord medicalRecord: medicalRecords) {
            if (medicalRecord.getFirstName().equals(firstname) && medicalRecord.getLastName().equals(lastname)) {
                logger.debug("    repo - findByFirstNameAndLastName OK for: firstname = {}, lastname = {}", firstname, lastname);
                return Optional.of(medicalRecord); }
        }
        logger.debug("    repo - findByFirstNameAndLastName not found for firstname = {}, lastname = {}", firstname, lastname);
        return Optional.empty();
    }

    @Override
    public void delete(MedicalRecord medicalRecordToDelete) {
        logger.debug("    repo -  delete  medicalRecord : {}", medicalRecordToDelete);
        List<MedicalRecord> medicalRecords = jsonDataBaseService.getMedicalRecords();
        medicalRecords.remove(medicalRecordToDelete);
        jsonDataBaseService.saveDataBaseInFile();
        logger.debug("    repo -  delete  OK medicalRecord : {} ", medicalRecordToDelete);
    }

    @Override
    public MedicalRecord add(MedicalRecord medicalRecordToAdd) {
        logger.debug("    repo -  add  medicalRecord : {}", medicalRecordToAdd);
        List<MedicalRecord> medicalRecords = jsonDataBaseService.getMedicalRecords();
        medicalRecords.add(medicalRecordToAdd);
        jsonDataBaseService.saveDataBaseInFile();
        logger.debug("    repo -  add medicalRecord OK for {}",  medicalRecordToAdd);
        return medicalRecordToAdd;
    }

    @Override
    public MedicalRecord update(MedicalRecord currentMedicalRecord, MedicalRecord medicalRecordToUpdate) {
        logger.debug("    repo -  update medicalRecord : {}", medicalRecordToUpdate);
        List<MedicalRecord> medicalRecords = jsonDataBaseService.getMedicalRecords();
        medicalRecords.set(medicalRecords.indexOf(currentMedicalRecord), medicalRecordToUpdate);
        jsonDataBaseService.saveDataBaseInFile();
        logger.debug("  repo -  update medicalRecord OK for {}", medicalRecordToUpdate);
        return medicalRecordToUpdate;
    }
}
