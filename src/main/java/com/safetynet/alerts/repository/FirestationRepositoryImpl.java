package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.JsonDataBaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FirestationRepositoryImpl implements IFirestationRepository{

    private static final Logger logger = LogManager.getLogger(FirestationRepositoryImpl.class);
    private final JsonDataBaseService jsonDataBaseService;

    public FirestationRepositoryImpl(JsonDataBaseService jsonDataBaseService) {
        this.jsonDataBaseService = jsonDataBaseService;
    }

    @Override
    public Optional<Firestation> findByAddressAndStation(String address, String station) {
        logger.debug("    repo - findByAddressAndStation : address = {}, station = {}", address, station);
        List<Firestation> firestations = jsonDataBaseService.getFirestations();
        for (Firestation firestation: firestations) {
            if (firestation.getAddress().equals(address) && firestation.getStation().equals(station)) {
                logger.debug("    repo - findByAddressAndStation OK for: address = {}, station = {}", address, station);
                return Optional.of(firestation); }
        }
        logger.debug("    repo - findByAddressAndStation not found for address = {}, station = {}", address, station);;
        return Optional.empty();
    }

    @Override
    public Optional<Firestation> findByAddress(String address) {
        logger.debug("    repo - findByAddress : address = {}", address);
        List<Firestation> firestations = jsonDataBaseService.getFirestations();
        for (Firestation firestation: firestations) {
            if (firestation.getAddress().equals(address)) {
                logger.debug("    repo - findByAddress OK for: address = {}", address);
                return Optional.of(firestation); }
        }
        logger.debug("    repo - findByAddress not found for address = {}", address);;
        return Optional.empty();
    }

    @Override
    public List<Firestation> findByStation(String station) {
        logger.debug("    repo - findByAddress : station = {}", station);

        //méthode 1
//        List<Firestation> firestations = jsonDataBaseService.getFirestations();
//        List<Firestation> foundedFirestations = new ArrayList<>();
//        for (Firestation firestation: firestations) {
//            if (firestation.getStation().equals(station)) {
//                foundedFirestations.add(firestation);
//            };
//        };

        //méthode 2
        List<Firestation> foundedFirestations = jsonDataBaseService.getFirestations()
                                                .stream()
                                                .filter(firestation -> firestation.getStation().equals(station))
                                                .collect(Collectors.toList());

        if (foundedFirestations.size() > 0) {
            logger.debug("    repo - findByStation OK for station = {} -> {} found" , station, foundedFirestations.size());
        } else {
            logger.debug("    repo - findByAddress not found for station = {}", station);
        }
        return foundedFirestations;

    }

    @Override
    public void delete(Firestation firestationToDelete) {
        logger.debug("    repo -  delete  firestation : {}", firestationToDelete);
        List<Firestation> firestations = jsonDataBaseService.getFirestations();
        firestations.remove(firestationToDelete);
        jsonDataBaseService.saveDataBaseInFile();
        logger.debug("    repo -  delete  OK firestation : {} ", firestationToDelete);
    }

    @Override
    public Firestation add(Firestation firestationToAdd) {
        logger.debug("    repo -  add  firestation : {}", firestationToAdd);
        List<Firestation> firestations = jsonDataBaseService.getFirestations();
        firestations.add(firestationToAdd);
        jsonDataBaseService.saveDataBaseInFile();
        logger.debug("    repo -  add firestation OK for {}",  firestationToAdd);
        return firestationToAdd;
    }

    @Override
    public Firestation update(Firestation currentFirestation, Firestation firestationToUpdate) {
        logger.debug("    repo -  update firestation : {}", firestationToUpdate);
        List<Firestation> firestations = jsonDataBaseService.getFirestations();
        firestations.set(firestations.indexOf(currentFirestation), firestationToUpdate);
        jsonDataBaseService.saveDataBaseInFile();
        logger.debug("  repo -  update firestation OK for {}", firestationToUpdate);
        return firestationToUpdate;
    }
}
