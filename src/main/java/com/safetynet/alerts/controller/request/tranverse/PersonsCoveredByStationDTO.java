package com.safetynet.alerts.controller.request.tranverse;

import java.util.List;

public class PersonsCoveredByStationDTO {

    private final List<PersonCoveredByStationDTO> persons;
    private final Integer adultsCount;
    private final Integer childrenCount;

    public PersonsCoveredByStationDTO(List<PersonCoveredByStationDTO> persons, Integer adultsCount, Integer childrenCount) {
        this.persons = persons;
        this.adultsCount = adultsCount;
        this.childrenCount = childrenCount;
    }

    public List<PersonCoveredByStationDTO> getPersons() {
        return persons;
    }

    public Integer getAdultsCount() {
        return adultsCount;
    }

    public Integer getChildrenCount() {
        return childrenCount;
    }

    @Override
    public String toString() {
        return "PersonsCoveredByStationDTO{" +
                "persons=" + persons +
                ", adultsCount=" + adultsCount +
                ", childrenCount=" + childrenCount +
                '}';
    }
}
