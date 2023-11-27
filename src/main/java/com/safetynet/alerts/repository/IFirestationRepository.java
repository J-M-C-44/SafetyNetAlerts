package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;

import java.util.List;
import java.util.Optional;

public interface IFirestationRepository {
    Optional<Firestation> findByAddressAndStation(String address, String station);
    Optional<Firestation> findByAddress(String address);
    List<Firestation> findByStation(String station);
    void delete(Firestation firestationToDelete);
    Firestation add(Firestation firestationToAdd);
    Firestation update(Firestation currentFirestation, Firestation firestationToUpdate);
}
