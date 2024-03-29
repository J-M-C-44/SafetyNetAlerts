package com.safetynet.alerts.service;

import com.safetynet.alerts.exception.AlreadyExistsException;
import com.safetynet.alerts.exception.NotFoundException;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.IFirestationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class FirestationServiceImpl implements IFirestationService{

    private static final Logger logger = LogManager.getLogger(FirestationServiceImpl.class);
    private final IFirestationRepository firestationRepository;
    public static final String MESSAGE_FIRESTATION_NOT_FOUND = "firestation not found";

    public FirestationServiceImpl(IFirestationRepository firestationRepository) {
        this.firestationRepository = firestationRepository;
    }

    @Override
    public Firestation addFirestation(Firestation firestationToAdd) {
        logger.debug("  serv - addFirestation - going to verify if Firestation already exist : address = {}, station = {}",
                     firestationToAdd.getAddress(), firestationToAdd.getStation());
        Optional<Firestation> firestation = firestationRepository.findByAddressAndStation(firestationToAdd.getAddress(), firestationToAdd.getStation());
        if (firestation.isPresent()) {
            logger.debug("  serv - addFirestation KO - firestation already exist: address = {}, station = {}",
                         firestationToAdd.getAddress(), firestationToAdd.getStation());
            throw new AlreadyExistsException("firestation with these address and station already exist !");
        }
        logger.debug("  serv - addFirestation - going to add firestation : address = {}, station = {}",
                firestationToAdd.getAddress(), firestationToAdd.getStation());
        return firestationRepository.add(firestationToAdd);
    }

    @Override
    public Firestation updateFirestation(Firestation firestationToUpdate) {
        logger.debug("  serv - updateFirestation going to verify if firestation exist with address = {}", firestationToUpdate.getAddress());
        Optional<Firestation> firestation = firestationRepository.findByAddress(firestationToUpdate.getAddress());
        if (firestation.isEmpty()) {
            logger.debug("  serv - updateFirestation KO - firestation not found: address = {}", firestationToUpdate.getAddress());
            throw new NotFoundException(MESSAGE_FIRESTATION_NOT_FOUND);
        }
        logger.debug("  serv - updateFirestation going to update firestation : address = {}", firestationToUpdate.getAddress());
        Firestation currentFirestation = firestation.get();
        return firestationRepository.update(currentFirestation, firestationToUpdate);
    }

    @Override
    public void deleteFirestationByAddress(String address) {
        logger.debug("  serv - deleteFirestation going to verify if firestation exist: address = {}", address);
        Optional<Firestation> firestation = firestationRepository.findByAddress(address);
        if (firestation.isEmpty()) {
            logger.debug("  serv - deleteFirestation KO - firestation not found: address = {}", address);
            throw new NotFoundException(MESSAGE_FIRESTATION_NOT_FOUND);
        }
        logger.debug("  serv - deleteFirestation going to delete firestation : address = {}", address);
        Firestation firestationToDelete = firestation.get();
        firestationRepository.delete(firestationToDelete);
    }

    @Override
    public void deleteFirestationByStation(String station) {
        logger.debug("  serv - deleteFirestation going to verify if firestation exist for station = {}", station);
        List<Firestation> firestations = firestationRepository.findByStation(station);
        if (firestations.isEmpty()) {
            logger.debug("  serv - deleteFirestation KO - firestation not found: station = {}", station);
            throw new NotFoundException(MESSAGE_FIRESTATION_NOT_FOUND);
        } else {
            logger.debug("  serv - deleteFirestation going to delete firestation : station = {}", station);
            firestations.forEach(firestationRepository::delete);
        }
    }

}
