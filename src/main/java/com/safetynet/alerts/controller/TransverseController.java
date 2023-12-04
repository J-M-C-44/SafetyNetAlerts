package com.safetynet.alerts.controller;

import com.safetynet.alerts.controller.request.*;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.IPersonService;
import com.safetynet.alerts.service.ITransverseService;
import jakarta.validation.constraints.NotBlank;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
                ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(mapperDTO.personsCoveredByStationToPersonsCoveredByStationDTO(personsCoveredByStation), HttpStatus.OK);
        logger.info("ctlr - response request - GET getPersonsCovererdByStation : {}", response);
        return response;

    }

    @GetMapping("/childAlert")
    public ResponseEntity<List<ChildAlertDTO>> getChildrenAndHomeMembers(@NotBlank @RequestParam final String address ) {
        logger.info("ctlr - received request - GET getChildrenAndHomeMembers: address = {}", address);

        List<ChildAndHomeMembers> childrenAndHomeMembers = transverseService.getChildrenAndHomeMembersByAddress(address);
        ResponseEntity<List<ChildAlertDTO>> response = childrenAndHomeMembers.isEmpty()
                ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(mapperDTO.childrenAndHomeMembersToChildAlertDTO(childrenAndHomeMembers), HttpStatus.OK);
        logger.info("ctlr - response request - GET getChildrenAndHomeMembers : {}", response);
        return response;

    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<PhonesAlertDTO> getPhonesByStation(@NotBlank @RequestParam("firestation") final String stationNumber ) {
        logger.info("ctlr - received request - GET getPhonesByStation: stationNumber = {}", stationNumber);

        List<Person> personsByStation = transverseService.getPersonsByStation(stationNumber);
        ResponseEntity<PhonesAlertDTO> response = personsByStation.isEmpty()
                ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(mapperDTO.personsToPhonesAlertDTO(personsByStation), HttpStatus.OK);
        logger.info("ctlr - response request - GET getPhonesByStation : {}", response);
        return response;

    }


    @GetMapping("/communityEmail")
    public ResponseEntity<CommunityEmailsDTO> getCommunityEmail(@NotBlank @RequestParam final String city) {

        logger.info("ctlr - received request - GET communityEmail: city = {}",  city);

        List<String> emails = personService.getCommunityEmails(city);
        ResponseEntity<CommunityEmailsDTO> response = emails.isEmpty()
                ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(mapperDTO.emailsToCommunityEmailsDTO(emails), HttpStatus.OK);
        logger.info("ctlr - response request - GET communityEmail : {}", response);
        return response;

    }



}
