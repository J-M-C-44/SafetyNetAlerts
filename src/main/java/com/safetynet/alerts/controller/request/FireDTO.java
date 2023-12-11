package com.safetynet.alerts.controller.request;

import java.util.List;

public class FireDTO {
    private String station;
    private List<PersonForFireDTO> persons;

    public FireDTO(String station, List<PersonForFireDTO> persons) {
        this.station = station;
        this.persons = persons;
    }

    public String getStation() {
        return station;
    }

    public List<PersonForFireDTO> getPersons() {
        return persons;
    }

    @Override
    public String toString() {
        return "FireDTO{" +
                "station='" + station + '\'' +
                ", personsForFireDTO=" + persons +
                '}';
    }

}
