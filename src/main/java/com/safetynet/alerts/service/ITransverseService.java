package com.safetynet.alerts.service;

import com.safetynet.alerts.controller.request.*;
import com.safetynet.alerts.model.Person;

import java.util.List;
import java.util.Map;

public interface ITransverseService {
    PersonsCoveredByStation getPersonsCoveredByStation(String stationNumber);

    List<ChildAndHomeMembers> getChildrenAndHomeMembersByAddress(String address);

    List<Person> getPersonsByStation (String stationNumber);

    StationAndCoveredPersonsAndMedicalRecordwithAge getPersonsForFirebyAddress(String address);

    Map<String, List<PersonAndMedicalRecordwithAge>> getPersonsForFloodByStations(List<String> stationNumbers);

    List<PersonAndMedicalRecordwithAge> getPersonInfobyName(String firstName, String lastName);
}
