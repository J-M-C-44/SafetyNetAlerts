package com.safetynet.alerts.controller.request.tranverse;

import java.util.List;

public class StationAndCoveredPersonsAndMedicalRecordwithAge {
    private final String station;
    private final List<PersonAndMedicalRecordwithAge> personsAndMedicalRecordwithAge;

    public StationAndCoveredPersonsAndMedicalRecordwithAge(String station, List<PersonAndMedicalRecordwithAge> personsAndMedicalRecordwithAge) {
        this.station = station;
        this.personsAndMedicalRecordwithAge = personsAndMedicalRecordwithAge;
    }

    public String getStation() {
        return station;
    }

    public List<PersonAndMedicalRecordwithAge> getPersonsAndMedicalRecordwithAge() {
        return personsAndMedicalRecordwithAge;
    }

    @Override
    public String toString() {
        return "StationAndCoveredPersonsAndMedicalRecordwithAge{" +
                "station='" + station + '\'' +
                ", personsAndMedicalRecordwithAge=" + personsAndMedicalRecordwithAge +
                '}';
    }
}