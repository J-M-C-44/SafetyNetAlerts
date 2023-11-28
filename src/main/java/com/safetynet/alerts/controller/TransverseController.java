package com.safetynet.alerts.controller;

import com.safetynet.alerts.controller.request.CommunityEmailsDTO;
import com.safetynet.alerts.controller.request.MapperDTO;
import com.safetynet.alerts.service.IPersonService;
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
    private final MapperDTO mapperDTO;

    public TransverseController(IPersonService personService, MapperDTO mapperDTO) {
        this.personService = personService;
        this.mapperDTO = mapperDTO;
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
