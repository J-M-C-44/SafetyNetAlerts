package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Firestation;

import java.util.Optional;

public interface IFirestationService {
    // sélectionner 1 firestation
    Optional<Firestation> getFirestation(String address, String station);

    // ajouter  1 firestation
    Firestation addFirestation(Firestation firestationToAdd);

    // maj  1 firestation à partir d'une adresse
    Firestation updateFirestation(Firestation firestationToUpdate);

    // supprimer  1 firestation par adresse
    void deleteFirestationByAddress(String address);

    // supprimer  x firestation par numero de station
    void deleteFirestationByStation(String station);
}
