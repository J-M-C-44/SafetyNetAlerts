package com.safetynet.alerts.service;

import com.safetynet.alerts.exception.AlreadyExistsException;
import com.safetynet.alerts.exception.NotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.IMedicalRecordRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicalRecordServiceImpl implements IMedicalRecordService{
    private static final Logger logger = LogManager.getLogger(MedicalRecordServiceImpl.class);
    private final IMedicalRecordRepository medicalRecordRepository;

    public MedicalRecordServiceImpl(IMedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }
    
    @Override
    public Optional<MedicalRecord> getMedicalRecord(String firstname, String lastname) {
        logger.debug("  serv - getMedicalRecord - going to find medicalRecord : firstname = {}, lastname = {}", firstname, lastname);
        return medicalRecordRepository.findByFirstNameAndLastName(firstname, lastname);
    }

    @Override
    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecordToAdd) {
        logger.debug("  serv - addMedicalRecord - going to verify if medicalRecord already exist : firstname = {}, lastname = {}",
                     medicalRecordToAdd.getFirstName(), medicalRecordToAdd.getLastName());
        Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(medicalRecordToAdd.getFirstName(), medicalRecordToAdd.getLastName());
        if (medicalRecord.isPresent()) {
            logger.debug("  serv - addMedicalRecord KO - medicalRecord already exist: firstname = {}, lastname = {}",
                         medicalRecordToAdd.getFirstName(), medicalRecordToAdd.getLastName());
            throw new AlreadyExistsException("medicalRecord with these firstname and lastname already exist !");
        } else {
            logger.debug("  serv - addMedicalRecord - going to add medicalRecord : firstname = {}, lastname = {}",
                         medicalRecordToAdd.getFirstName(), medicalRecordToAdd.getLastName());
            return medicalRecordRepository.add(medicalRecordToAdd);
        }
        
    }

    @Override
    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecordToUpdate) {
        logger.debug("  serv - updateMedicalRecord going to verify if medicalRecord exist: firstname = {}, lastname = {}",
                    medicalRecordToUpdate.getFirstName(), medicalRecordToUpdate.getLastName());
        Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(
                    medicalRecordToUpdate.getFirstName(), medicalRecordToUpdate.getLastName());
        if (medicalRecord.isPresent()) {
            logger.debug("  serv - updateMedicalRecord going to update medicalRecord : firstname = {}, lastname = {}",
                    medicalRecordToUpdate.getFirstName(), medicalRecordToUpdate.getLastName());
            MedicalRecord currentMedicalRecord = medicalRecord.get();
            return medicalRecordRepository.update(currentMedicalRecord, medicalRecordToUpdate);
        } else {
            logger.debug("  serv - updateMedicalRecord KO - medicalRecord not found: firstname = {}, lastname = {}",
                    medicalRecordToUpdate.getFirstName(), medicalRecordToUpdate.getLastName());
            throw new NotFoundException("medicalRecord not found");
        }
        
    }

    @Override
    public void deleteMedicalRecord(String firstname, String lastname) {
        logger.debug("  serv - deleteMedicalRecord going to verify if medicalRecord exist: firstname = {}, lastname = {}", firstname, lastname);
        Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(firstname, lastname);
        if (medicalRecord.isPresent()) {
            logger.debug("  serv - deleteMedicalRecord going to delete medicalRecord : firstname = {}, lastname = {}", firstname, lastname);
            MedicalRecord medicalRecordToDelete = medicalRecord.get();
            medicalRecordRepository.delete(medicalRecordToDelete);
        } else {
            logger.debug("  serv - deleteMedicalRecord KO - medicalRecord not found: firstname = {}, lastname = {}", firstname, lastname);
            throw new NotFoundException("medicalRecord not found");
        }
    }

}
