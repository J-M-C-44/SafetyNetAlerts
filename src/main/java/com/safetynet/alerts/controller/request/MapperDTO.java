package com.safetynet.alerts.controller.request;

import com.safetynet.alerts.model.Person;
import org.springframework.stereotype.Service;

@Service
public class MapperDTO {
    public PersonDTO personToPersonDTO(Person person) {
        return new PersonDTO(person.getFirstName(),
                             person.getLastName(),
                             person.getAddress(),
                             person.getCity(),
                             person.getZip(),
                             person.getPhone(),
                             person.getEmail() );
    }

    public Person personDTOToPerson(PersonDTO personDTO) {
        return new Person(personDTO.getFirstName(),
                personDTO.getLastName(),
                personDTO.getAddress(),
                personDTO.getCity(),
                personDTO.getZip(),
                personDTO.getPhone(),
                personDTO.getEmail() );
    }

    public Person personWithoutNameDTOToPerson(PersonWithoutNameDTO personWithoutNameDTO, String firstName, String lastName) {
        return new Person(firstName,
                lastName,
                personWithoutNameDTO.getAddress(),
                personWithoutNameDTO.getCity(),
                personWithoutNameDTO.getZip(),
                personWithoutNameDTO.getPhone(),
                personWithoutNameDTO.getEmail() );
    }
}
