package com.safetynet.alerts.service;

import com.safetynet.alerts.controller.request.ChildAndHomeMembers;
import com.safetynet.alerts.controller.request.PersonAndMedicalRecordwithAge;
import com.safetynet.alerts.controller.request.PersonAndMedicalRecordwithAgeAndStation;
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

import java.util.*;

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
                    MedicalRecord foundMedicalRecord = medicalRecord.get();
                    Integer childrenAge = AgeUtil.calculateAgeFromDate(foundMedicalRecord.getBirthdate());
                    List<Person> otherHomeMembers = persons.stream()
                                                            .filter(p -> !(p.getFirstName().equals(person.getFirstName()) &&
                                                                          p.getLastName().equals(person.getLastName())))
                                                            .toList();
                    childrenAndHomeMembers.add(new ChildAndHomeMembers(foundMedicalRecord, childrenAge, otherHomeMembers));
                } else {
                    logger.debug("  serv - getChildrenAndHomeMembersByAddress -2- KO - no children found in medicalRecords for = {}", person);
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

//    @Override
//    public List<PersonAndMedicalRecordwithAgeAndStation> getPersonsForFirebyAddress(String address) {
//
//        List<PersonAndMedicalRecordwithAgeAndStation> personsAndMedicalRecordwithAge = new ArrayList<>();
//        logger.debug("  serv - getPersonsForFirebyAddress -1- going to found persons for address = {}", address);
//        //TODO: voir avec mentor pour la redéclaration ou pas
//        List<Person> persons = personRepository.findByAddress(address);
//        if (persons.isEmpty()) {
//            logger.debug("  serv - getPersonsForFirebyAddress -1- KO - person not found: for address = {}", address);
//        } else {
//            logger.debug("  serv - getPersonsForFirebyAddress -2- going to found corresponding medical records and station for persons= {}", persons);
//            for (Person person : persons) {
//                //TODO: vérifier les cas d'exception (non trouvés)
//                PersonAndMedicalRecordwithAgeAndStation personAndMedicalRecordwithAge = new PersonAndMedicalRecordwithAgeAndStation(person);
//
//                Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
//                if (medicalRecord.isPresent()) {
//                    personAndMedicalRecordwithAge.setMedicalRecord(medicalRecord.get());
//                    personAndMedicalRecordwithAge.setAge(AgeUtil.calculateAgeFromDate(medicalRecord.get().getBirthdate()));
//                } else {
//                    logger.debug("  serv - getPersonsForFirebyAddress -2- KO - no medicalRecords for = {}", person);
//                }
//
//                Optional<Firestation> firestation = firestationRepository.findByAddress(address);
//                if (firestation.isPresent()) {
//                    personAndMedicalRecordwithAge.setStation(firestation.get().getStation());
//                } else {
//                    logger.debug("  serv - getPersonsForFirebyAddress -2- KO - firestation not found for address = {}", address);
//                }
//                personsAndMedicalRecordwithAge.add(personAndMedicalRecordwithAge);
//            }
//        }
//        return personsAndMedicalRecordwithAge;
//    }

    @Override
    public List<PersonAndMedicalRecordwithAgeAndStation> getPersonsForFirebyAddress(String address) {

        List<PersonAndMedicalRecordwithAgeAndStation> personsAndMedicalRecordwithAge = new ArrayList<>();
        logger.debug("  serv - getPersonsForFirebyAddress -1- going to found persons for address = {}", address);
        //TODO: voir avec mentor pour la redéclaration ou pas
        List<Person> persons = personRepository.findByAddress(address);
        if (persons.isEmpty()) {
            logger.debug("  serv - getPersonsForFirebyAddress -1- KO - person not found: for address = {}", address);
        } else {
            logger.debug("  serv - getPersonsForFirebyAddress -2- going to found corresponding medical records and station for persons= {}", persons);
            Optional<Firestation> firestation = firestationRepository.findByAddress(address);
            String station = firestation.isPresent() ? (firestation.get().getStation()) : "";
            logger.debug("  serv - getPersonsForFirebyAddress -3- going to found corresponding medical records for persons= {}", persons);
            for (Person person : persons) {
                PersonAndMedicalRecordwithAgeAndStation personAndMedicalRecordwithAgeAndStation = new PersonAndMedicalRecordwithAgeAndStation(person,station);
                Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                if (medicalRecord.isPresent()) {
                    personAndMedicalRecordwithAgeAndStation.setMedicalRecord(medicalRecord.get());
                    personAndMedicalRecordwithAgeAndStation.setAge(AgeUtil.calculateAgeFromDate(medicalRecord.get().getBirthdate()));
                } else {
                    logger.debug("  serv - getPersonsForFirebyAddress -3- KO - no medicalRecords for = {}", person);
                }
                personsAndMedicalRecordwithAge.add(personAndMedicalRecordwithAgeAndStation);
            }
        }
        return personsAndMedicalRecordwithAge;
    }

    @Override
    public Map<String, List<PersonAndMedicalRecordwithAge>> getPersonsForFloodByStations(List<String> stationNumbers) {
        //TODO voir avec mentor si on peut appeler une méthode d'un service dans un même service
        //stationNumbers.forEach(station -> getPersonsByStation(station));
        logger.debug("  serv - getPersonsForFloodByStations -1- going to found firestation addresses for stations = {}", stationNumbers);
        Map<String, List<PersonAndMedicalRecordwithAge>> personFloodMap = new HashMap<>();

        List<String> addresses = new ArrayList<>();
        stationNumbers.forEach((stationNumber -> addresses.addAll(firestationRepository.findByStation(stationNumber).stream()
                .map(Firestation::getAddress)
                .toList())));

        if (addresses.isEmpty()) {
            logger.debug("  serv - getPersonsForFloodByStations -1- KO - firestation addresses not found for stations = {}", stationNumbers);
        } else {
            logger.debug("  serv - getPersonsForFloodByStations -2- going to found persons with matching addresses for stations = {}", stationNumbers);
            for (String address : addresses) {
                List<PersonAndMedicalRecordwithAge> personsAndMedicalRecordwithAge = new ArrayList<>();
                List<Person> persons = personRepository.findByAddress(address);
                logger.debug("  serv - getPersonsForFloodByStations -3- going to found corresponding medical records for persons= {}", persons);
                for (Person person : persons) {
                    PersonAndMedicalRecordwithAge personAndMedicalRecordwithAge = new PersonAndMedicalRecordwithAge(person);
                    Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                    if (medicalRecord.isPresent()) {
                        personAndMedicalRecordwithAge.setMedicalRecord(medicalRecord.get());
                        personAndMedicalRecordwithAge.setAge(AgeUtil.calculateAgeFromDate(medicalRecord.get().getBirthdate()));
                    } else {
                        logger.debug("  serv - getPersonsForFloodByStations -3- KO - no medicalRecords for = {}", person);
                    }
                    personsAndMedicalRecordwithAge.add(personAndMedicalRecordwithAge);
                }
                personFloodMap.put(address,personsAndMedicalRecordwithAge);
                }
            }

        return personFloodMap;
    }

    @Override
    public List<PersonAndMedicalRecordwithAge> getPersonInfobyName(String firstName, String lastName) {

        List<PersonAndMedicalRecordwithAge> personsAndMedicalRecordwithAge = new ArrayList<>();
        logger.debug("  serv - getPersonInfobyName - going to find persons with firstname = {} and lastname = {}", firstName, lastName);
        List<Person> persons = personRepository.findAllByFirstNameAndLastName(firstName, lastName);
        for (Person person : persons) {
            PersonAndMedicalRecordwithAge personAndMedicalRecordwithAge = new PersonAndMedicalRecordwithAge(person);
            Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
            if (medicalRecord.isPresent()) {
                personAndMedicalRecordwithAge.setMedicalRecord(medicalRecord.get());
                personAndMedicalRecordwithAge.setAge(AgeUtil.calculateAgeFromDate(medicalRecord.get().getBirthdate()));
            } else {
                logger.debug("  serv - getPersonsForFloodByStations -3- KO - no medicalRecords for = {}", person);
            }
            personsAndMedicalRecordwithAge.add(personAndMedicalRecordwithAge);
        }
        return personsAndMedicalRecordwithAge;
    }

}
