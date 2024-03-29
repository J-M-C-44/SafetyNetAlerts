package com.safetynet.alerts.controller.request;

import com.safetynet.alerts.controller.request.firestation.FirestationDTO;
import com.safetynet.alerts.controller.request.firestation.FirestationWithoutAddressDTO;
import com.safetynet.alerts.controller.request.medicalrecord.MedicalRecordDTO;
import com.safetynet.alerts.controller.request.medicalrecord.MedicalRecordWithoutNameDTO;
import com.safetynet.alerts.controller.request.person.PersonDTO;
import com.safetynet.alerts.controller.request.person.PersonWithoutNameDTO;
import com.safetynet.alerts.controller.request.tranverse.*;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

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

    public PhonesAlertDTO personsToPhonesAlertDTO(List<Person> personsByStation) {
        return new PhonesAlertDTO(personsByStation.stream()
                .map(Person::getPhone)
                .distinct()
                .toList());
    }

    public FireDTO stationAndCoveredPersonAndMedicalRecordWithAgeToFireDTO(StationAndCoveredPersonsAndMedicalRecordWithAge personsForFire) {
        return new FireDTO(
                personsForFire.getStation(),
                personsForFire.getPersonsAndMedicalRecordWithAge().stream()
                        .map(p -> new PersonForFireDTO(p.getPerson().getFirstName(),
                                                       p.getPerson().getLastName(),
                                                       p.getPerson().getPhone(),
                                                       p.getAge(),
                                                       p.getMedicalRecord().getMedications(),
                                                       p.getMedicalRecord().getAllergies()))
                        .toList()
                );
    }

    public Map<String, List<PersonFloodDTO>> personAndMedicalRecordWithAgeToFloodDTO(Map<String, List<PersonAndMedicalRecordWithAge>> personsForFlood) {

        Map<String, List<PersonFloodDTO>> floodDTO = new HashMap<>();
        for (Map.Entry<String, List<PersonAndMedicalRecordWithAge>> entry : personsForFlood.entrySet()) {
            List<PersonFloodDTO> personFloodDTOList = entry.getValue().stream()
                    .map(p -> new PersonFloodDTO(p.getPerson().getFirstName(),
                            p.getPerson().getLastName(),
                            p.getPerson().getPhone(),
                            p.getAge(),
                            p.getMedicalRecord().getMedications(),
                            p.getMedicalRecord().getAllergies()))
                    .toList();
            floodDTO.put(entry.getKey(),personFloodDTOList);
        }
        return floodDTO;
    }

    public List<PersonInfoDTO> personAndMedicalRecordWithAgeToPersonsInfoDTO(List<PersonAndMedicalRecordWithAge> personsInfo) {
        return personsInfo.stream()
                .map(p -> new PersonInfoDTO(p.getPerson().getFirstName(),
                        p.getPerson().getLastName(),
                        p.getPerson().getEmail(),
                        p.getAge(),
                        p.getMedicalRecord().getMedications(),
                        p.getMedicalRecord().getAllergies()))
                .toList();
    }
}
