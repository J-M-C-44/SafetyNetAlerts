package com.safetynet.alerts.controller.request.tranverse;

import java.util.List;

public class StationAndCoveredPersonsAndMedicalRecordWithAge {
    private final String station;
    private final List<PersonAndMedicalRecordWithAge> personsAndMedicalRecordWithAge;

    public StationAndCoveredPersonsAndMedicalRecordWithAge(String station, List<PersonAndMedicalRecordWithAge> personsAndMedicalRecordWithAge) {
        this.station = station;
        this.personsAndMedicalRecordWithAge = personsAndMedicalRecordWithAge;
    }

    public String getStation() {
        return station;
    }

    public List<PersonAndMedicalRecordWithAge> getPersonsAndMedicalRecordWithAge() {
        return personsAndMedicalRecordWithAge;
    }

    @Override
    public String toString() {
        return "StationAndCoveredPersonsAndMedicalRecordWithAge{" +
                "station='" + station + '\'' +
                ", personsAndMedicalRecordWithAge=" + personsAndMedicalRecordWithAge +
                '}';
    }
}
