package com.safetynet.alerts.service;

import com.safetynet.alerts.controller.request.ChildAndHomeMembers;
import com.safetynet.alerts.controller.request.PersonsCoveredByStation;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.utils.AgeUtil;
import com.safetynet.alerts.repository.IFirestationRepository;
import com.safetynet.alerts.repository.IMedicalRecordRepository;
import com.safetynet.alerts.repository.IPersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransverseServiceImpl implements ITransverseService {
    private static final Logger logger = LogManager.getLogger(TransverseServiceImpl.class);
    private final IFirestationRepository firestationRepository;
    private final IPersonRepository personRepository;
    private final IMedicalRecordRepository medicalRecordRepository;

    public TransverseServiceImpl(IFirestationRepository firestationRepository, IPersonRepository personRepository, IMedicalRecordRepository medicalRecordRepository) {
        this.firestationRepository = firestationRepository;
        this.personRepository = personRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @Override
    public PersonsCoveredByStation getPersonsCoveredByStation(String stationNumber) {

        PersonsCoveredByStation personsCoveredByStation = new PersonsCoveredByStation();

        logger.debug("  serv - getPersonsCoveredByStation -1- going to find Addresses covered by station = {}", stationNumber);
        List<Firestation> firestations = firestationRepository.findByStation(stationNumber);

        if (firestations.isEmpty()) {
            logger.debug("  serv - getPersonsCoveredByStation KO - firestation not found: station = {}", stationNumber);
//            throw new NotFoundException("firestation not found");
        } else {
            logger.debug("  serv - getPersonsCoveredByStation -2- going to found persons with matching addresses for station = {}", stationNumber);

            for (Firestation firestation : firestations) {
                List<Person> persons = personRepository.findByAddress(firestation.getAddress());
                logger.debug("  serv - getPersonsCoveredByStation -3- going to found corresponding medical records for persons= {}", persons);

                for (Person person : persons) {
                    personsCoveredByStation.addPerson(person);
                    Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                    if (medicalRecord.isPresent() && AgeUtil.isChild(medicalRecord.get().getBirthdate()) ) {
                        personsCoveredByStation.incrementChildren() ;
                    } else {
                        personsCoveredByStation.incrementAdults();
                    }
                }
            }
        }
        return personsCoveredByStation;
    }

    @Override
    public List<ChildAndHomeMembers> getChildrenAndHomeMembersByAddress(String address) {
        List<ChildAndHomeMembers> childrenAndHomeMembers  = new ArrayList<>();

        logger.debug("  serv - getChildrenAndHomeMembersByAddress -1- going to found persons for address = {}", address);
        List<Person> persons = personRepository.findByAddress(address);
        if (persons.isEmpty()) {
            logger.debug("  serv - getChildrenAndHomeMembersByAddress -1- KO - person not found: for address = {}", address);

        } else {
            logger.debug("  serv - getChildrenAndHomeMembersByAddress -2- going to found corresponding medical records for persons= {}", persons);
            for (Person person : persons) {
                Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                if (medicalRecord.isPresent() && AgeUtil.isChild(medicalRecord.get().getBirthdate()) ) {
                    MedicalRecord foundedMedicalRecord = medicalRecord.get();
                    Integer childrenAge = AgeUtil.calculateAgeFromDate(foundedMedicalRecord.getBirthdate());
                    List<Person> otherHomeMembers = persons.stream()
                                                            .filter(p -> !(p.getFirstName().equals(person.getFirstName()) &&
                                                                          p.getLastName().equals(person.getLastName())))
                                                            .toList();
                    childrenAndHomeMembers.add(new ChildAndHomeMembers(foundedMedicalRecord, childrenAge, otherHomeMembers));
                } else {
                    logger.debug("  serv - getChildrenAndHomeMembersByAddress -2- KO - no children found in medicalRecords for = {}", persons);
                }
            }
        }
        return childrenAndHomeMembers;
    }

    @Override
    public List<Person> getPersonsByStation(String stationNumber) {
        List<Person> personsByStation = new ArrayList<>();

        logger.debug("  serv - getPersonsByStation -1- going to find Addresses covered by station = {}", stationNumber);
        List<Firestation> firestations = firestationRepository.findByStation(stationNumber);

        if (firestations.isEmpty()) {
            logger.debug("  serv - getPersonsByStation KO - firestation not found: station = {}", stationNumber);
//            throw new NotFoundException("firestation not found");
        } else {
            logger.debug("  serv - getPersonsByStation -2- going to found persons with matching addresses for station = {}", stationNumber);
            for (Firestation firestation : firestations) {
                personsByStation.addAll(personRepository.findByAddress(firestation.getAddress()));
            }
        }
        return personsByStation;
    }

}
