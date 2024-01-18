package com.safetynet.alerts.service;

import com.safetynet.alerts.controller.request.tranverse.ChildAndHomeMembers;
import com.safetynet.alerts.controller.request.tranverse.PersonAndMedicalRecordWithAge;
import com.safetynet.alerts.controller.request.tranverse.PersonsCoveredByStation;
import com.safetynet.alerts.controller.request.tranverse.StationAndCoveredPersonsAndMedicalRecordWithAge;
import com.safetynet.alerts.model.Person;

import java.util.List;
import java.util.Map;

public interface ITransverseService {
    PersonsCoveredByStation getPersonsCoveredByStation(String stationNumber);

    List<ChildAndHomeMembers> getChildrenAndHomeMembersByAddress(String address);

    List<Person> getPersonsByStation (String stationNumber);

    StationAndCoveredPersonsAndMedicalRecordWithAge getPersonsForFireByAddress(String address);

    Map<String, List<PersonAndMedicalRecordWithAge>> getPersonsForFloodByStations(List<String> stationNumbers);

    List<PersonAndMedicalRecordWithAge> getPersonInfoByName(String firstName, String lastName);
}
