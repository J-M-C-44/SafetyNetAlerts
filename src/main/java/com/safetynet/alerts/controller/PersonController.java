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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/person")
public class PersonController {
//    @Autowired
    private final IPersonService personService;
//    @Autowired
    private final MapperDTO mapperDTO;

    public PersonController(IPersonService personService, MapperDTO mapperDTO) {
        this.personService = personService;
        this.mapperDTO = mapperDTO;
    }

    @GetMapping("")
    public ResponseEntity<PersonDTO> getPerson(@RequestParam final String firstName,
                                               @RequestParam final String lastName ) throws IOException, NotFoundException, NoContainException {
            Person person = personService.getPerson(firstName, lastName);
            return new ResponseEntity<>(mapperDTO.personToPersonDTO(person), HttpStatus.OK);

    }

    @PostMapping("")
    public  ResponseEntity<PersonDTO> createPerson(@Valid @RequestBody PersonDTO personTransmitted) throws IOException, AlreadyExistsException {

        Person pTransmitted = mapperDTO.personDTOToPerson(personTransmitted);
        Person p = personService.addPerson(pTransmitted);
        return new ResponseEntity<>(mapperDTO.personToPersonDTO(p), HttpStatus.CREATED);

    }


    @PutMapping("")
    public  ResponseEntity<PersonDTO> updatePerson(@RequestParam final String firstName,
                                        @RequestParam final String lastName,
                                        @Valid @RequestBody PersonDTO personTransmitted) throws IOException, NotFoundException, GivenDataMismatchException {
        if ( firstName.equals(personTransmitted.getFirstName() ) && lastName.equals(personTransmitted.getLastName()) ) {
            Person pTransmitted = mapperDTO.personDTOToPerson(personTransmitted);
            Person p = personService.updatePerson(firstName, lastName, pTransmitted);
            return new ResponseEntity<>(mapperDTO.personToPersonDTO(p), HttpStatus.OK);
        } else {
            throw new GivenDataMismatchException("requestparams firstname and lastname mismatch with body person data");
        }
    }

    @DeleteMapping("")
    public  ResponseEntity<String> deletePerson(@RequestParam final String firstName,
                                        @RequestParam final String lastName ) throws IOException, NotFoundException {

        personService.deletePerson(firstName, lastName);
        return new ResponseEntity<>("person deleted",HttpStatus.OK);

    }

}
