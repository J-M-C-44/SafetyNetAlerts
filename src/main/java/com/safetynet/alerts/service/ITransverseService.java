package com.safetynet.alerts.service;

import com.safetynet.alerts.controller.request.tranverse.ChildAndHomeMembers;
import com.safetynet.alerts.controller.request.tranverse.PersonAndMedicalRecordwithAge;
import com.safetynet.alerts.controller.request.tranverse.PersonsCoveredByStation;
import com.safetynet.alerts.controller.request.tranverse.StationAndCoveredPersonsAndMedicalRecordwithAge;
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
