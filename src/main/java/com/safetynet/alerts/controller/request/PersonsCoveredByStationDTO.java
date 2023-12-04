package com.safetynet.alerts.controller.request;

import java.util.List;

public class PersonsCoveredByStationDTO {

    private List<PersonCoveredByStationDTO> persons;
    private Integer adultsCount;
    private Integer childrenCount;

    public PersonsCoveredByStationDTO(List<PersonCoveredByStationDTO> persons, Integer adultsCount, Integer childrenCount) {
        this.persons = persons;
        this.adultsCount = adultsCount;
        this.childrenCount = childrenCount;
    }

    public List<PersonCoveredByStationDTO> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonCoveredByStationDTO> persons) {
        this.persons = persons;
    }

    public Integer getAdultsCount() {
        return adultsCount;
    }

    public void setAdultsCount(Integer adultsCount) {
        this.adultsCount = adultsCount;
    }

    public Integer getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(Integer childrenCount) {
        this.childrenCount = childrenCount;
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
