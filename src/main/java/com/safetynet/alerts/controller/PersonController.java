package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private IPersonService personService;

    // TODO faire transco DTO
    @GetMapping("")
    public Person getPerson(@RequestParam final String firstName,
                            @RequestParam final String lastName ) throws IOException {
        Optional<Person> person = personService.getPerson(firstName, lastName);
        // return person.orElse(null);
        if (person.isPresent()) {
            return person.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "person not found");
        }
    }

    @PostMapping("")
    public Person createPerson(@RequestBody Person personTransmitted) throws IOException {
        if (personTransmitted != null) {
            if (    personTransmitted.getFirstName() != null &&
                    personTransmitted.getLastName() != null &&
                    personTransmitted.getAddress() != null &&
                    personTransmitted.getCity() !=null &&
                    personTransmitted.getZip() !=null &&
                    personTransmitted.getPhone() !=null &&
                    personTransmitted.getEmail() !=null ) {
                    // TODO appeler méthode nonNull(t) && nonNull(t.getFirstname()....

                Optional<Person> person = personService.getPerson(personTransmitted.getFirstName(), personTransmitted.getLastName());
                if (person.isPresent()) {
                    throw new ResponseStatusException(
                            HttpStatus.FORBIDDEN, "person with these firstname and lastname already exist");
                } else {
                    // TODO voir pour forcer code retour 201
                    return personService.addPerson(personTransmitted);
                }
            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "at least a person field is missing");
            }

        } else
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "person not transmitted in body request");
    }

    @PutMapping("")
    public Person updatePerson(@RequestParam final String firstName,
                               @RequestParam final String lastName,
                               @RequestBody Person personTransmitted) throws IOException {
        Optional<Person> person = personService.getPerson(firstName, lastName);
        if (person.isPresent()) {
            Person currentPerson = person.get();
            Person personToSave = person.get();

            String newAdress = personTransmitted.getAddress();
            if (newAdress != null) {
                personToSave.setAddress(newAdress);
            }
            String newCity = personTransmitted.getCity();
            if (newCity != null) {
                personToSave.setCity(newCity);
            }
            Integer newZip = personTransmitted.getZip();
            if (newZip != null) {
                personToSave.setZip(newZip);
            }
            String newPhone = personTransmitted.getPhone();
            if (newPhone!= null) {
                personToSave.setPhone(newPhone);
            }
            String newEmail = personTransmitted.getEmail();
            if (newEmail!= null) {
                personToSave.setEmail(newEmail);
            }

            return personService.updatePerson(currentPerson, personToSave);

        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "person not found");
        }
    }

    @DeleteMapping("")
    public void deletePerson(@RequestParam final String firstName,
                             @RequestParam final String lastName ) throws IOException {
        // à revoir pour faire des vrais retours: non trouvé vs ok
        System.out.println("entrée endpoint-delete: "+firstName+lastName);
        Optional<Person> person = personService.getPerson(firstName, lastName);
        if (person.isPresent()) {
            System.out.println("isPresent ok -entree delete: "+firstName+lastName);
            personService.deletePerson(person.get());
        } else {
            System.out.println("isPresent ko -HTTpStatus not found: "+firstName+lastName);
            // TODO libellé non pris en compte - à revoir
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "person not found");
        }
    }

}
