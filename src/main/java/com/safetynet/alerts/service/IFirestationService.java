package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Firestation;

import java.util.Optional;

public interface IFirestationService {

    Optional<Firestation> getFirestation(String address, String station);

    Firestation addFirestation(Firestation firestationToAdd);

    Firestation updateFirestation(Firestation firestationToUpdate);

    // supprimer  1 firestation par adresse
    void deleteFirestationByAddress(String address);

    // supprimer  x firestations par numéro de station
    void deleteFirestationByStation(String station);
}
