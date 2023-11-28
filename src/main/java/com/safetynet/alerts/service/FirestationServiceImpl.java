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

    public FirestationServiceImpl(IFirestationRepository firestationRepository) {
        this.firestationRepository = firestationRepository;
    }

    @Override
    public Optional<Firestation> getFirestation(String address, String station) {
        logger.debug("  serv - getFirestationn - going to find firestation : address = {}, station = {}", address, station);
        return firestationRepository.findByAddressAndStation(address, station);
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
        } else {
            logger.debug("  serv - addFirestation - going to add firestation : address = {}, station = {}",
                    firestationToAdd.getAddress(), firestationToAdd.getStation());
            return firestationRepository.add(firestationToAdd);
        }
    }

    @Override
    public Firestation updateFirestation(Firestation firestationToUpdate) {
        logger.debug("  serv - updateFirestation going to verify if firestation exist with address = {}", firestationToUpdate.getAddress());
        Optional<Firestation> firestation = firestationRepository.findByAddress(firestationToUpdate.getAddress());
        if (firestation.isPresent()) {
            logger.debug("  serv - updateFirestation going to update firestation : address = {}", firestationToUpdate.getAddress());
            Firestation currentFirestation = firestation.get();
            return firestationRepository.update(currentFirestation, firestationToUpdate);
        } else {
            logger.debug("  serv - updateFirestation KO - firestation not found: address = {}", firestationToUpdate.getAddress());
            throw new NotFoundException("firestation not found");
        }
    }

    @Override
    public void deleteFirestationByAddress(String address) {
        logger.debug("  serv - deleteFirestation going to verify if firestation exist: address = {}", address);
        Optional<Firestation> firestation = firestationRepository.findByAddress(address);
        if (firestation.isPresent()) {
            logger.debug("  serv - deleteFirestation going to delete firestation : address = {}", address);
            Firestation firestationToDelete = firestation.get();
            firestationRepository.delete(firestationToDelete);
        } else {
            logger.debug("  serv - deleteFirestation KO - firestation not found: address = {}", address);
            throw new NotFoundException("firestation not found");
        }

    }

    @Override
    public void deleteFirestationByStation(String station) {
        logger.debug("  serv - deleteFirestation going to verify if firestation exist for station = {}", station);
        List<Firestation> firestations = firestationRepository.findByStation(station);
        if (firestations.isEmpty()) {
            logger.debug("  serv - deleteFirestation KO - firestation not found: station = {}", station);
            throw new NotFoundException("firestation not found");
        } else {
            logger.debug("  serv - deleteFirestation going to delete firestation : station = {}", station);
            // methode 1 : boucle for
            // for (Firestation firestationToDelete : firestations) {
            //     firestationRepository.delete(firestationToDelete);
            // }
            // méthode 2: boucle foreach
            // firestations.forEach(firestationToDelete -> firestationRepository.delete(firestationToDelete));
            // méthode 3 : foreach + methode reférence
            firestations.forEach(firestationRepository::delete);
        }

    }

}
