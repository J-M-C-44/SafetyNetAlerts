package com.safetynet.alerts.controller.request;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public FirestationDTO firestationToFirestationDTO(Firestation firestation) {
        return new FirestationDTO(firestation.getAddress(),
                                  firestation.getStation());
    }

    public Firestation firestationDTOToFirestation(FirestationDTO firestationDTO) {
        return new Firestation(firestationDTO.getAddress(),
                               firestationDTO.getStation());
    }

    public Firestation firestationWithoutAdressDTOToFirestation(FirestationWithoutAddressDTO firestationWithoutAddressDTO, String address) {
        return new Firestation(address,
                               firestationWithoutAddressDTO.getStation());
    }

    public MedicalRecord medicalRecordDTOToMedicalRecord(MedicalRecordDTO medicalRecordTransmitted) {
        return new MedicalRecord(medicalRecordTransmitted.getFirstName(),
                                 medicalRecordTransmitted.getLastName(),
                                 medicalRecordTransmitted.getBirthdate(),
                                 medicalRecordTransmitted.getMedications(),
                                 medicalRecordTransmitted.getAllergies());
    }

    public MedicalRecordDTO medicalRecordToMedicalRecordDTO(MedicalRecord medicalRecord) {
        return new MedicalRecordDTO(medicalRecord.getFirstName(),
                                    medicalRecord.getLastName(),
                                    medicalRecord.getBirthdate(),
                                    medicalRecord.getMedications(),
                                    medicalRecord.getAllergies());
    }

    public MedicalRecord medicalRecordWithoutNameDTOToMedicalRecord(MedicalRecordWithoutNameDTO medicalRecordWithoutNameDTO, String firstName, String lastName) {
        return new MedicalRecord(firstName,
                                 lastName,
                                 medicalRecordWithoutNameDTO.getBirthdate(),
                                 medicalRecordWithoutNameDTO.getMedications(),
                                 medicalRecordWithoutNameDTO.getAllergies());
    }

    public CommunityEmailsDTO emailsToCommunityEmailsDTO(List<String> emails) {
        return new CommunityEmailsDTO(emails);
    }


    public PersonsCoveredByStationDTO personsCoveredByStationToPersonsCoveredByStationDTO(PersonsCoveredByStation personsCoveredByStation) {
        List<Person> persons = personsCoveredByStation.getPersons();
        List<PersonCoveredByStationDTO> personsCovered = persons.stream()
                .map(p -> new PersonCoveredByStationDTO(p.getFirstName(),p.getLastName(),p.getAddress(),p.getPhone()))
                .toList();
        return new PersonsCoveredByStationDTO(personsCovered,
                personsCoveredByStation.getAdultsCount(),
                personsCoveredByStation.getChildrenCount());
    }

    public List<ChildAlertDTO> childrenAndHomeMembersToChildAlertDTO(List<ChildAndHomeMembers> childrenAndHomeMembers) {

        return childrenAndHomeMembers.stream()
                .map(c -> new ChildAlertDTO(c.getMedicalRecord().getFirstName(),
                                            c.getMedicalRecord().getLastName(),
                                            c.getChildAge(),
                                            c.getOtherHomeMembers()))
                .toList();

    }
}
