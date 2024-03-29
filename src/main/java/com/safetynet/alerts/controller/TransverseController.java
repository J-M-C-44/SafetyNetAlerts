package com.safetynet.alerts.controller;

import com.safetynet.alerts.controller.request.*;
import com.safetynet.alerts.controller.request.tranverse.*;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.IPersonService;
import com.safetynet.alerts.service.ITransverseService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Validated
@RestController
public class TransverseController {

    private static final Logger logger = LogManager.getLogger(TransverseController.class);

    private final IPersonService personService;
    private final ITransverseService transverseService;
    private final MapperDTO mapperDTO;

    public TransverseController(IPersonService personService, ITransverseService transverseService, MapperDTO mapperDTO) {
        this.personService = personService;
        this.transverseService = transverseService;
        this.mapperDTO = mapperDTO;
    }

        // liste des personnes couvertes par station + nb adultes et enfants
    @GetMapping("/firestation")
    public ResponseEntity<PersonsCoveredByStationDTO> getPersonsCoveredByStation(@NotBlank @RequestParam final String stationNumber ) {

        logger.info("ctlr - received request - GET getPersonsCovererdByStation: stationNumber = {}", stationNumber);

        PersonsCoveredByStation personsCoveredByStation = transverseService.getPersonsCoveredByStation(stationNumber);
        ResponseEntity<PersonsCoveredByStationDTO> response = personsCoveredByStation.getPersons().isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(mapperDTO.personsCoveredByStationToPersonsCoveredByStationDTO(personsCoveredByStation), HttpStatus.OK);
        logger.info("ctlr - response request - GET getPersonsCovererdByStation : {}", response);
        return response;
    }

    @GetMapping("/childAlert")
    public ResponseEntity<List<ChildAlertDTO>> getChildrenAndHomeMembers(@NotBlank @RequestParam final String address ) {
        logger.info("ctlr - received request - GET getChildrenAndHomeMembers: address = {}", address);

        List<ChildAndHomeMembers> childrenAndHomeMembers = transverseService.getChildrenAndHomeMembersByAddress(address);
        ResponseEntity<List<ChildAlertDTO>> response = childrenAndHomeMembers.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(mapperDTO.childrenAndHomeMembersToChildAlertDTO(childrenAndHomeMembers), HttpStatus.OK);
        logger.info("ctlr - response request - GET getChildrenAndHomeMembers : {}", response);
        return response;
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<PhonesAlertDTO> getPhonesByStation(@NotBlank @RequestParam("firestation") final String stationNumber ) {
        logger.info("ctlr - received request - GET getPhonesByStation: stationNumber = {}", stationNumber);

        List<Person> personsByStation = transverseService.getPersonsByStation(stationNumber);
        ResponseEntity<PhonesAlertDTO> response = personsByStation.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(mapperDTO.personsToPhonesAlertDTO(personsByStation), HttpStatus.OK);
        logger.info("ctlr - response request - GET getPhonesByStation : {}", response);
        return response;
    }

    @GetMapping("/fire")
    public ResponseEntity<FireDTO> getPersonsForFirebByAddress(@NotBlank @RequestParam final String address ) {
        logger.info("ctlr - received request - GET getPersonsFireByAddress: stationNumber = {}", address);

        StationAndCoveredPersonsAndMedicalRecordWithAge stationAndPersonsForFire = transverseService.getPersonsForFireByAddress(address);
        ResponseEntity<FireDTO> response = stationAndPersonsForFire.getPersonsAndMedicalRecordWithAge().isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(mapperDTO.stationAndCoveredPersonAndMedicalRecordWithAgeToFireDTO(stationAndPersonsForFire), HttpStatus.OK);
        logger.info("ctlr - response request - GET getPersonsFireByAddress : {}", response);
        return response;
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<Map<String, List<PersonFloodDTO>>> getPersonsForFloodByStations(@NotNull @RequestParam("stations") final List<String> stationNumbers ) {
        logger.info("ctlr - received request - GET getPersonsForFloodByStations: stationNumber = {}", stationNumbers);

        Map<String,List<PersonAndMedicalRecordWithAge>> personsForFlood = transverseService.getPersonsForFloodByStations(stationNumbers);
        ResponseEntity<Map<String, List<PersonFloodDTO>>> response = personsForFlood.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(mapperDTO.personAndMedicalRecordWithAgeToFloodDTO(personsForFlood), HttpStatus.OK);
        logger.info("ctlr - response request - GET getPersonsForFloodByStations : {}", response);
        return response;
    }

    @GetMapping("/personInfo")
    public ResponseEntity<List<PersonInfoDTO>> getPersonInfoByName(@NotBlank @RequestParam final String firstName,
                                                              @NotBlank @RequestParam final String lastName ) {
        logger.info("ctlr - received request - GET getPersonInfoByName: firstName = {}, lastName = {}", firstName, lastName);

        List<PersonAndMedicalRecordWithAge> personsInfo = transverseService.getPersonInfoByName(firstName, lastName);
        ResponseEntity<List<PersonInfoDTO>> response = personsInfo.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(mapperDTO.personAndMedicalRecordWithAgeToPersonsInfoDTO(personsInfo), HttpStatus.OK);
        logger.info("ctlr - response request - GET getPersonInfoByName : {}", response);
        return response;
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<CommunityEmailsDTO> getCommunityEmail(@NotBlank @RequestParam final String city) {

        logger.info("ctlr - received request - GET communityEmail: city = {}",  city);

        List<String> emails = personService.getCommunityEmails(city);
        ResponseEntity<CommunityEmailsDTO> response = emails.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(mapperDTO.emailsToCommunityEmailsDTO(emails), HttpStatus.OK);
        logger.info("ctlr - response request - GET communityEmail : {}", response);
        return response;
    }

}
