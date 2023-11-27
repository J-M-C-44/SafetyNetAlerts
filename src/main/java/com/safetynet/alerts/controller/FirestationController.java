package com.safetynet.alerts.controller;

import com.safetynet.alerts.controller.request.*;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.IFirestationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Validated
@RestController
@RequestMapping("/firestation")
public class FirestationController {
    private static final Logger logger = LogManager.getLogger(com.safetynet.alerts.controller.FirestationController.class);
    private final IFirestationService firestationService;
    private final MapperDTO mapperDTO;

    public FirestationController(IFirestationService firestationService, MapperDTO mapperDTO) {
        this.firestationService = firestationService;
        this.mapperDTO = mapperDTO;
    }

    @GetMapping("")
    public ResponseEntity<FirestationDTO> getFirestation(@NotBlank @RequestParam final String address,
                                                         @NotBlank @RequestParam final String station ) {

        logger.info("ctlr - received request - GET Firestation: address = {}, station = {}",  address, station);
        Optional<Firestation> firestation = firestationService.getFirestation(address, station);
        ResponseEntity<FirestationDTO> response = firestation.isPresent()
                ? new ResponseEntity<>(mapperDTO.firestationToFirestationDTO(firestation.get()), HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        logger.info("ctlr - response request - GET Firestation : {}", response);
        return response;

    }
    // ajouter une firestation
    @PostMapping("")
    public  ResponseEntity<FirestationDTO> createFirestation(@Valid @RequestBody FirestationDTO firestationTransmitted) {
        logger.info("ctlr - received request - POST Firestation : {}", firestationTransmitted);

        Firestation fTransmitted = mapperDTO.firestationDTOToFirestation(firestationTransmitted);
        Firestation f = firestationService.addFirestation(fTransmitted);
        ResponseEntity<FirestationDTO> response = new ResponseEntity<>(mapperDTO.firestationToFirestationDTO(f), HttpStatus.CREATED);
        logger.info("ctlr - response request - POST Firestation : {}", response);
        return response;

    }


    // modifier n° de station
    @PutMapping("/{address}")
    public  ResponseEntity<FirestationDTO> updateFirestation(@NotBlank @PathVariable("address") final String address,
                                                             @Valid @RequestBody FirestationWithoutAddressDTO firestationTransmitted) {
        logger.info("ctlr - received request - PUT Firestation: address = {} - {}", address, firestationTransmitted);

        Firestation fTransmitted = mapperDTO.firestationWithoutAdressDTOToFirestation(firestationTransmitted, address);
        Firestation f = firestationService.updateFirestation(address, fTransmitted);
        ResponseEntity<FirestationDTO> response = new ResponseEntity<>(mapperDTO.firestationToFirestationDTO(f), HttpStatus.OK);
        logger.info("ctlr - response request - PUT Firestation : {}", response);
        return response;
    }

    @DeleteMapping("/{address}")
    public  ResponseEntity<String> deleteFirestationbyAdress(@NotBlank @PathVariable("address") final String address) {
        logger.info("ctlr - received request - DELETE Firestation by adress = {}, ",  address);
        firestationService.deleteFirestationByAddress(address);
        ResponseEntity<String> response = new ResponseEntity<>("Firestation deleted",HttpStatus.OK);
        logger.info("ctlr - response request - DELETE Firestation by adress : {}", response);
        return response;
        }

    @DeleteMapping("")
    public  ResponseEntity<String> deleteFirestationbyStation(@NotBlank @RequestParam("station") final String station) {
        logger.info("ctlr - received request - DELETE Firestation by station = {}, ",  station);
        firestationService.deleteFirestationByStation(station);
        ResponseEntity<String> response = new ResponseEntity<>("Firestation deleted",HttpStatus.OK);
        logger.info("ctlr - response request - DELETE Firestation by station : {}", response);
        return response;
    }

}

