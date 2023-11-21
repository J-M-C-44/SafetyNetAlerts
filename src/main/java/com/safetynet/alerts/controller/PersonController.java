package com.safetynet.alerts.controller;

import com.safetynet.alerts.controller.request.MapperDTO;
import com.safetynet.alerts.controller.request.PersonDTO;
import com.safetynet.alerts.exception.AlreadyExistsException;
import com.safetynet.alerts.exception.GivenDataMismatchException;
import com.safetynet.alerts.exception.NoContainException;
import com.safetynet.alerts.exception.NotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.IPersonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/person")
public class PersonController {
    private static final Logger logger = LogManager.getLogger(PersonController.class);
    private final IPersonService personService;
    private final MapperDTO mapperDTO;

    public PersonController(IPersonService personService, MapperDTO mapperDTO) {
        this.personService = personService;
        this.mapperDTO = mapperDTO;
    }

    @GetMapping("")
    public ResponseEntity<PersonDTO> getPerson(@NotBlank @RequestParam final String firstName,
                                               @NotBlank @RequestParam final String lastName )
            throws IOException, NoContainException, NotFoundException {

        logger.info("ctlr - received request - GET Person: firstName = {}, lastName = {}",  firstName, lastName);
        Person person = personService.getPerson(firstName, lastName);
        ResponseEntity<PersonDTO> response = new ResponseEntity<>(mapperDTO.personToPersonDTO(person), HttpStatus.OK);
        logger.info("ctlr - response request - GET Person : {}", response);
        return response;

    }

    @PostMapping("")
    public  ResponseEntity<PersonDTO> createPerson(@Valid @RequestBody PersonDTO personTransmitted)
                throws IOException, AlreadyExistsException {
        logger.info("ctlr - received request - POST Person : {}", personTransmitted);

        Person pTransmitted = mapperDTO.personDTOToPerson(personTransmitted);
        Person p = personService.addPerson(pTransmitted);
        ResponseEntity<PersonDTO> response = new ResponseEntity<>(mapperDTO.personToPersonDTO(p), HttpStatus.CREATED);
        logger.info("ctlr - response request - POST Person : {}", response);
        return response;

    }


    @PutMapping("")
    public  ResponseEntity<PersonDTO> updatePerson(@NotBlank @RequestParam final String firstName,
                                                   @NotBlank @RequestParam final String lastName,
                                                   @Valid @RequestBody PersonDTO personTransmitted)
                throws IOException, NotFoundException, GivenDataMismatchException {
        logger.info("ctlr - received request - PUT Person: firstName = {}, lastName = {} - {}",
                    firstName, lastName, personTransmitted);
        if ( firstName.equals(personTransmitted.getFirstName()) && lastName.equals(personTransmitted.getLastName()) ) {
            Person pTransmitted = mapperDTO.personDTOToPerson(personTransmitted);
            Person p = personService.updatePerson(firstName, lastName, pTransmitted);
            ResponseEntity<PersonDTO> response = new ResponseEntity<>(mapperDTO.personToPersonDTO(p), HttpStatus.OK);
            logger.info("ctlr - response request - PUT Person : {}", response);
            return response;
        } else {
            throw new GivenDataMismatchException("requestparams firstname and lastname mismatch with body person data");
        }
    }

    @DeleteMapping("")
    public  ResponseEntity<String> deletePerson(@NotBlank @RequestParam final String firstName,
                                                @NotBlank @RequestParam final String lastName )
                throws IOException, NotFoundException {
        logger.info("ctlr - received request - DELETE Person: firstName = {}, lastName = {}",  firstName, lastName);
        personService.deletePerson(firstName, lastName);
        ResponseEntity<String> response = new ResponseEntity<>("person deleted",HttpStatus.OK);
        logger.info("ctlr - response request - DELETE Person : {}", response);
        return response;
    }

}
