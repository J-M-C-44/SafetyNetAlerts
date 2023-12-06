package com.safetynet.alerts.service;

import com.safetynet.alerts.controller.request.ChildAndHomeMembers;
import com.safetynet.alerts.controller.request.PersonAndMedicalRecordwithAge;
import com.safetynet.alerts.controller.request.PersonsCoveredByStation;
import com.safetynet.alerts.controller.request.PersonAndMedicalRecordwithAgeAndStation;
import com.safetynet.alerts.model.Person;

import java.util.List;
import java.util.Map;

public interface ITransverseService {
    PersonsCoveredByStation getPersonsCoveredByStation(String stationNumber);

    List<ChildAndHomeMembers> getChildrenAndHomeMembersByAddress(String address);

    List<Person> getPersonsByStation (String stationNumber);

    List<PersonAndMedicalRecordwithAgeAndStation> getPersonsForFirebyAddress(String address);

    Map<String, List<PersonAndMedicalRecordwithAge>> getPersonsForFloodByStations(List<String> stationNumbers);
}
