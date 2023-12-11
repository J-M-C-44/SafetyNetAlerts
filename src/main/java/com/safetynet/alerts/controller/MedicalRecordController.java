package com.safetynet.alerts.controller;

import com.safetynet.alerts.controller.request.MapperDTO;
import com.safetynet.alerts.controller.request.medicalrecord.MedicalRecordDTO;
import com.safetynet.alerts.controller.request.medicalrecord.MedicalRecordWithoutNameDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.IMedicalRecordService;
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
@RequestMapping("/medicalRecord")
public class MedicalRecordController {
    private static final Logger logger = LogManager.getLogger(com.safetynet.alerts.controller.MedicalRecordController.class);
    private final IMedicalRecordService medicalRecordService;
    private final MapperDTO mapperDTO;

    public MedicalRecordController(IMedicalRecordService medicalRecordService, MapperDTO mapperDTO) {
        this.medicalRecordService = medicalRecordService;
        this.mapperDTO = mapperDTO;
    }

    @GetMapping("")
    public ResponseEntity<MedicalRecordDTO> getMedicalRecord(@NotBlank @RequestParam final String firstName,
                                                             @NotBlank @RequestParam final String lastName ) {

        logger.info("ctlr - received request - GET MedicalRecord: firstName = {}, lastName = {}",  firstName, lastName);
        Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecord(firstName, lastName);
        ResponseEntity<MedicalRecordDTO> response = medicalRecord.isPresent()
                ? new ResponseEntity<>(mapperDTO.medicalRecordToMedicalRecordDTO(medicalRecord.get()), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        logger.info("ctlr - response request - GET MedicalRecord : {}", response);
        return response;

    }

    @PostMapping("")
    public  ResponseEntity<MedicalRecordDTO> createMedicalRecord(@Valid @RequestBody MedicalRecordDTO medicalRecordTransmitted) {
        logger.info("ctlr - received request - POST MedicalRecord : {}", medicalRecordTransmitted);

        MedicalRecord mTransmitted = mapperDTO.medicalRecordDTOToMedicalRecord(medicalRecordTransmitted);
        MedicalRecord m = medicalRecordService.addMedicalRecord(mTransmitted);
        ResponseEntity<MedicalRecordDTO> response = new ResponseEntity<>(mapperDTO.medicalRecordToMedicalRecordDTO(m), HttpStatus.CREATED);
        logger.info("ctlr - response request - POST MedicalRecord : {}", response);
        return response;

    }

    @PutMapping("/{firstName}/{lastName}")
    public  ResponseEntity<MedicalRecordDTO> updateMedicalRecord(@NotBlank @PathVariable("firstName") final String firstName,
                                                                 @NotBlank @PathVariable("lastName") final String lastName,
                                                                 @Valid @RequestBody MedicalRecordWithoutNameDTO medicalRecordTransmitted) {
        logger.info("ctlr - received request - PUT MedicalRecord: firstName = {}, lastName = {} - {}",
                firstName, lastName, medicalRecordTransmitted);

        MedicalRecord mTransmitted = mapperDTO.medicalRecordWithoutNameDTOToMedicalRecord(medicalRecordTransmitted, firstName, lastName);
        MedicalRecord m = medicalRecordService.updateMedicalRecord(mTransmitted);
        ResponseEntity<MedicalRecordDTO> response = new ResponseEntity<>(mapperDTO.medicalRecordToMedicalRecordDTO(m), HttpStatus.OK);
        logger.info("ctlr - response request - PUT MedicalRecord : {}", response);
        return response;
    }

    @DeleteMapping("/{firstName}/{lastName}")
    public  ResponseEntity<String> deleteMedicalRecord(@NotBlank @PathVariable("firstName") final String firstName,
                                                       @NotBlank @PathVariable("lastName") final String lastName) {
        logger.info("ctlr - received request - DELETE MedicalRecord: firstName = {}, lastName = {}",  firstName, lastName);

        medicalRecordService.deleteMedicalRecord(firstName, lastName);
        ResponseEntity<String> response = new ResponseEntity<>("medicalRecord deleted",HttpStatus.OK);
        logger.info("ctlr - response request - DELETE MedicalRecord : {}", response);
        return response;
    }

}

