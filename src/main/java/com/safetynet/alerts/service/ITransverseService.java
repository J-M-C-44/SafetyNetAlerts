package com.safetynet.alerts.service;

import com.safetynet.alerts.controller.request.ChildAndHomeMembers;
import com.safetynet.alerts.controller.request.PersonsCoveredByStation;

import java.util.List;

public interface ITransverseService {
    PersonsCoveredByStation getPersonsCoveredByStation(String stationNumber);

    List<ChildAndHomeMembers> getChildrenAndHomeMembersByAddress(String address);
}
