package com.safetynet.alerts.service;

import com.safetynet.alerts.controller.request.ChildAndHomeMembers;
import com.safetynet.alerts.controller.request.PersonsCoveredByStation;
import com.safetynet.alerts.controller.request.PersonAndMedicalRecordwithAge;
import com.safetynet.alerts.model.Person;

import java.util.List;

public interface ITransverseService {
    PersonsCoveredByStation getPersonsCoveredByStation(String stationNumber);

    List<ChildAndHomeMembers> getChildrenAndHomeMembersByAddress(String address);

    List<Person> getPersonsByStation (String stationNumber);

    List<PersonAndMedicalRecordwithAge> getPersonsForFirebyAddress(String address);
}
