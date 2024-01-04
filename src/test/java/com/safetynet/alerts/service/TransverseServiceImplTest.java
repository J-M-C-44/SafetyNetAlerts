package com.safetynet.alerts.service;

import com.safetynet.alerts.controller.request.tranverse.ChildAndHomeMembers;
import com.safetynet.alerts.controller.request.tranverse.PersonAndMedicalRecordWithAge;
import com.safetynet.alerts.controller.request.tranverse.PersonsCoveredByStation;
import com.safetynet.alerts.controller.request.tranverse.StationAndCoveredPersonsAndMedicalRecordWithAge;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransverseServiceImplTest {

    private final Firestation firestation1 = new Firestation("5, road to Nantes",  "99");
    private final Firestation firestation2 = new Firestation("5, road to Rennes",  "88");
    private final Firestation firestation3 = new Firestation("6, street of Brest",  "99");
    private static final String ADDRESS = "5, road to Nantes";
    private static final String STATION_NUMBER = "99";
    private final Person person1 = new Person("Harry","Covert","5, road to Nantes","Treillieres","12345","800-800-1234","harry.covert@gmail.com");
    private final Person person2 = new Person("Justine","Illusion","5, road to Nantes","Treillieres","12345","800-800-1234","justine.illusion@gmail.com");
    private final Person person3 = new Person("Paul","Emploi","6, street of Brest","Treillieres","12345","800-800-2345","paul.emploi@gmail.com");
    private final Person person4 = new Person("Thomas","Tserize","5, road to Rennes","Treillieres","12345","800-800-3456","saveol@gmail.com");
    private final MedicalRecord medicalRecord1 = new MedicalRecord("Harry","Covert","12/31/2000", List.of("suppo:1u", "paracetamol:500mg"), List.of("bouleau"));
    private final MedicalRecord medicalRecord2 = new MedicalRecord("Justine","Illusion","11/30/1989", List.of("suppo:2u", "paracetamol:1000mg"), List.of(""));
    private final MedicalRecord medicalRecord2b = new MedicalRecord("Justine","Illusion","11/30/2016", List.of("suppo:2u", "paracetamol:1000mg"), List.of(""));
    private final MedicalRecord medicalRecord3 = new MedicalRecord("Paul","Emploi","10/30/2015", List.of("suppo:3u"), List.of("aspirine","arachide"));
    private final MedicalRecord medicalRecord4 = new MedicalRecord("Thomas","Tserize","06/28/2000", List.of("suppo:4u"), List.of("exces de suppo"));
    private final PersonAndMedicalRecordWithAge personAndMedicalRecordWithAge1 = new PersonAndMedicalRecordWithAge(person1,medicalRecord1,23);
    private final PersonAndMedicalRecordWithAge personAndMedicalRecordWithAge1b = new PersonAndMedicalRecordWithAge(person1);
    private final PersonAndMedicalRecordWithAge personAndMedicalRecordWithAge2 = new PersonAndMedicalRecordWithAge(person2,medicalRecord2,34);
    private final PersonAndMedicalRecordWithAge personAndMedicalRecordWithAge2b = new PersonAndMedicalRecordWithAge(person2);
    private final PersonAndMedicalRecordWithAge personAndMedicalRecordWithAge3 = new PersonAndMedicalRecordWithAge(person3,medicalRecord3,8);
    private final PersonAndMedicalRecordWithAge personAndMedicalRecordWithAge4 = new PersonAndMedicalRecordWithAge(person4,medicalRecord4,23);
    private final StationAndCoveredPersonsAndMedicalRecordWithAge stationAndCoveredPersonsAndMedicalRecordWithAge1 = new StationAndCoveredPersonsAndMedicalRecordWithAge(STATION_NUMBER,List.of(personAndMedicalRecordWithAge1, personAndMedicalRecordWithAge2));
    private final StationAndCoveredPersonsAndMedicalRecordWithAge stationAndCoveredPersonsAndMedicalRecordWithAge2 = new StationAndCoveredPersonsAndMedicalRecordWithAge(STATION_NUMBER,List.of(personAndMedicalRecordWithAge1b, personAndMedicalRecordWithAge2b));
    private final StationAndCoveredPersonsAndMedicalRecordWithAge stationAndCoveredPersonsAndMedicalRecordWithAge3 = new StationAndCoveredPersonsAndMedicalRecordWithAge(null,List.of(personAndMedicalRecordWithAge1, personAndMedicalRecordWithAge2));
    private final StationAndCoveredPersonsAndMedicalRecordWithAge stationAndCoveredPersonsAndMedicalRecordWithAge4 = new StationAndCoveredPersonsAndMedicalRecordWithAge(STATION_NUMBER,Collections.emptyList());

    /** Service à mocker */
    @Mock
    IFirestationRepository firestationRepositoryMock;
    @Mock
    IPersonRepository personRepositoryMock;
    @Mock
    IMedicalRecordRepository medicalRecordRepositoryMock;

    /** Class à tester (avec injection des mocks)*/
    @InjectMocks
    TransverseServiceImpl transverseServiceImpl;

    @Test
    void getPersonsCoveredByStation_shoudBeOKAndReturnPersonsCoveredByStation() {
        //arrange
        List<Firestation> firestations = List.of(firestation1, firestation3);
        List<Person> personsWithAddress1 = List.of(person1, person2);
        List<Person> personsWithAddress2 = List.of(person3);
        when(firestationRepositoryMock.findByStation(anyString())).thenReturn(firestations);
        when(personRepositoryMock.findByAddress("5, road to Nantes")).thenReturn(personsWithAddress1);
        when(personRepositoryMock.findByAddress("6, street of Brest")).thenReturn(personsWithAddress2);
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName("Harry","Covert")).thenReturn(Optional.of(medicalRecord1));
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName("Justine","Illusion")).thenReturn(Optional.of(medicalRecord2));
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName("Paul","Emploi")).thenReturn(Optional.of(medicalRecord3));


        //act
        PersonsCoveredByStation result = transverseServiceImpl.getPersonsCoveredByStation(STATION_NUMBER);
        PersonsCoveredByStation expected = new PersonsCoveredByStation();
        personsWithAddress1.forEach(person -> { expected.addPerson(person);
            expected.incrementAdults();  });
        personsWithAddress2.forEach(person -> { expected.addPerson(person);
            expected.incrementChildren();  });


        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(firestationRepositoryMock,times(1)).findByStation("99");
        verifyNoMoreInteractions(firestationRepositoryMock);
        verify(personRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verify(personRepositoryMock,times(1)).findByAddress("6, street of Brest");
        verifyNoMoreInteractions(personRepositoryMock);
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Harry","Covert");
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Justine","Illusion");
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Paul","Emploi");
        verifyNoMoreInteractions(medicalRecordRepositoryMock);
    }

    @Test
    void getPersonsCoveredByStation_shoudBeOKAndReturnPersonsCoveredByStationEvenIfMedicalRecordsNotFound() {
        //arrange
        List<Firestation> firestations = List.of(firestation1, firestation3);
        List<Person> personsWithAddress1 = List.of(person1, person2);
        List<Person> personsWithAddress2 = List.of(person3);
        when(firestationRepositoryMock.findByStation(anyString())).thenReturn(firestations);
        when(personRepositoryMock.findByAddress("5, road to Nantes")).thenReturn(personsWithAddress1);
        when(personRepositoryMock.findByAddress("6, street of Brest")).thenReturn(personsWithAddress2);
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(Optional.empty());

        //act
        PersonsCoveredByStation result = transverseServiceImpl.getPersonsCoveredByStation(STATION_NUMBER);
        PersonsCoveredByStation expected = new PersonsCoveredByStation();
        personsWithAddress1.forEach(person -> { expected.addPerson(person);
            expected.incrementAdults();  });
        personsWithAddress2.forEach(person -> { expected.addPerson(person);
            expected.incrementAdults();  });

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(firestationRepositoryMock,times(1)).findByStation("99");
        verifyNoMoreInteractions(firestationRepositoryMock);
        verify(personRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verify(personRepositoryMock,times(1)).findByAddress("6, street of Brest");
        verifyNoMoreInteractions(personRepositoryMock);
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Harry","Covert");
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Justine","Illusion");
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Paul","Emploi");
        verifyNoMoreInteractions(medicalRecordRepositoryMock);
    }

    @Test
    void getPersonsCoveredByStation_shoudReturnEmptyListAnd0CountsWhenFirestationNotFound() {
        //arrange
        when(firestationRepositoryMock.findByStation(anyString())).thenReturn(Collections.emptyList());

        //act
        PersonsCoveredByStation result = transverseServiceImpl.getPersonsCoveredByStation(STATION_NUMBER);
        PersonsCoveredByStation expected = new PersonsCoveredByStation();

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(firestationRepositoryMock,times(1)).findByStation("99");
        verifyNoInteractions(personRepositoryMock, medicalRecordRepositoryMock);
    }

    @Test
    void getPersonsCoveredByStation_shoudReturnEmptyListAnd0CountsWhenPersonsNotFound() {
        //arrange
        List<Firestation> firestations = List.of(firestation1, firestation3);
        when(firestationRepositoryMock.findByStation(anyString())).thenReturn(firestations);
        when(personRepositoryMock.findByAddress(anyString())).thenReturn(Collections.emptyList());

        //act
        PersonsCoveredByStation result = transverseServiceImpl.getPersonsCoveredByStation(STATION_NUMBER);
        PersonsCoveredByStation expected = new PersonsCoveredByStation();

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(firestationRepositoryMock,times(1)).findByStation("99");
        verifyNoMoreInteractions(firestationRepositoryMock);
        verify(personRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verify(personRepositoryMock,times(1)).findByAddress("6, street of Brest");
        verifyNoMoreInteractions(personRepositoryMock);
        verifyNoInteractions(medicalRecordRepositoryMock);
    }

    @Test
    void getChildrenAndHomeMembersByAddress_shoudBeOKAndReturnChildrenAndHomeMembers() {
        //arrange
        ChildAndHomeMembers childAndHomeMembers = new ChildAndHomeMembers(medicalRecord2b,7,List.of(person1));
        List<Person> personsWithAddress1 = List.of(person1, person2);
        when(personRepositoryMock.findByAddress(ADDRESS)).thenReturn(personsWithAddress1);
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName("Harry","Covert")).thenReturn(Optional.of(medicalRecord1));
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName("Justine","Illusion")).thenReturn(Optional.of(medicalRecord2b));

        //act
        List<ChildAndHomeMembers> result = transverseServiceImpl.getChildrenAndHomeMembersByAddress(ADDRESS);
        List<ChildAndHomeMembers> expected = List.of(childAndHomeMembers) ;

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(personRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verifyNoMoreInteractions(personRepositoryMock);
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Harry","Covert");
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Justine","Illusion");
        verifyNoMoreInteractions(medicalRecordRepositoryMock);
    }

    @Test
    void getChildrenAndHomeMembersByAddress_shoudReturnEmptyListWhenPersonNotFound() {
        //arrange
        when(personRepositoryMock.findByAddress(anyString())).thenReturn(Collections.emptyList());

        //act
        List<ChildAndHomeMembers> result = transverseServiceImpl.getChildrenAndHomeMembersByAddress(ADDRESS);
        List<ChildAndHomeMembers> expected = Collections.emptyList();

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(personRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verifyNoMoreInteractions(personRepositoryMock);
        verifyNoInteractions(medicalRecordRepositoryMock);
    }
    @Test
    void getChildrenAndHomeMembersByAddress_shoudReturnEmptyListWhenMedicalRecordNotFound() {
        //arrange
        List<Person> personsWithAddress1 = List.of(person1, person2);
        when(personRepositoryMock.findByAddress(anyString())).thenReturn(personsWithAddress1);
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(Optional.empty());

        //act
        List<ChildAndHomeMembers> result = transverseServiceImpl.getChildrenAndHomeMembersByAddress(ADDRESS);
        List<ChildAndHomeMembers> expected = Collections.emptyList();

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(personRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verifyNoMoreInteractions(personRepositoryMock);
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Harry","Covert");
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Justine","Illusion");
        verifyNoMoreInteractions(medicalRecordRepositoryMock);
    }

    @Test
    void getChildrenAndHomeMembersByAddress_shoudReturnEmptyListWhenOnlyAdults() {
        //arrange
        List<Person> personsWithAddress1 = List.of(person1, person2);
        when(personRepositoryMock.findByAddress(anyString())).thenReturn(personsWithAddress1);
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName("Harry","Covert")).thenReturn(Optional.of(medicalRecord1));
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName("Justine","Illusion")).thenReturn(Optional.of(medicalRecord2));

        //act
        List<ChildAndHomeMembers> result = transverseServiceImpl.getChildrenAndHomeMembersByAddress(ADDRESS);
        List<ChildAndHomeMembers> expected = Collections.emptyList();

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(personRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verifyNoMoreInteractions(personRepositoryMock);
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Harry","Covert");
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Justine","Illusion");
        verifyNoMoreInteractions(medicalRecordRepositoryMock);
    }


    @Test
    void getPersonsByStation_shoudBeOKAndReturnPersons() {
        //arrange
        List<Firestation> firestations = List.of(firestation1, firestation3);
        List<Person> personsWithAddress1 = List.of(person1, person2);
        List<Person> personsWithAddress2 = List.of(person3);
        when(firestationRepositoryMock.findByStation(anyString())).thenReturn(firestations);
        when(personRepositoryMock.findByAddress("5, road to Nantes")).thenReturn(personsWithAddress1);
        when(personRepositoryMock.findByAddress("6, street of Brest")).thenReturn(personsWithAddress2);

        //act
        List<Person> result = transverseServiceImpl.getPersonsByStation(STATION_NUMBER);
        List<Person> expected = List.of(person1, person2,person3);

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(firestationRepositoryMock,times(1)).findByStation("99");
        verifyNoMoreInteractions(firestationRepositoryMock);
        verify(personRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verify(personRepositoryMock,times(1)).findByAddress("6, street of Brest");
        verifyNoMoreInteractions(personRepositoryMock);
    }
    @Test
    void getPersonsByStation_shoudReturnEmptyListWhenFirestationNotFound() {
        //arrange
        when(firestationRepositoryMock.findByStation(anyString())).thenReturn(Collections.emptyList());

        //act
        List<Person> result = transverseServiceImpl.getPersonsByStation(STATION_NUMBER);
        List<Person> expected = Collections.emptyList();

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(firestationRepositoryMock,times(1)).findByStation("99");
        verifyNoMoreInteractions(firestationRepositoryMock);
        verifyNoInteractions(personRepositoryMock);
    }

    @Test
    void getPersonsByStation_shoudReturnEmptyListWhenPersonNotFound() {
        //arrange
        List<Firestation> firestations = List.of(firestation1, firestation3);
        when(firestationRepositoryMock.findByStation(anyString())).thenReturn(firestations);
        when(personRepositoryMock.findByAddress(anyString())).thenReturn(Collections.emptyList());

        //act
        List<Person> result = transverseServiceImpl.getPersonsByStation(STATION_NUMBER);
        List<Person> expected = Collections.emptyList();

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(firestationRepositoryMock,times(1)).findByStation("99");
        verifyNoMoreInteractions(firestationRepositoryMock);
        verify(personRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verify(personRepositoryMock,times(1)).findByAddress("6, street of Brest");
        verifyNoMoreInteractions(personRepositoryMock);
    }

    @Test
    void getPersonsForFirebyAddress_shoudBeOKAndReturnStationAndCoveredPersonsAndMedicalRecordWithAge() {
        //arrange
        List<Person> personsWithAddress1 = List.of(person1, person2);
        when(firestationRepositoryMock.findByAddress(anyString())).thenReturn(Optional.of(firestation1));
        when(personRepositoryMock.findByAddress(ADDRESS)).thenReturn(personsWithAddress1);
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName("Harry","Covert")).thenReturn(Optional.of(medicalRecord1));
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName("Justine","Illusion")).thenReturn(Optional.of(medicalRecord2));

        //act
        StationAndCoveredPersonsAndMedicalRecordWithAge result = transverseServiceImpl.getPersonsForFirebyAddress(ADDRESS);
        StationAndCoveredPersonsAndMedicalRecordWithAge expected = stationAndCoveredPersonsAndMedicalRecordWithAge1;

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(firestationRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verifyNoMoreInteractions(firestationRepositoryMock);
        verify(personRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verifyNoMoreInteractions(personRepositoryMock);
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Harry","Covert");
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Justine","Illusion");
        verifyNoMoreInteractions(medicalRecordRepositoryMock);
    }

    @Test
    void getPersonsForFirebyAddress_shouldBeOKAndReturnNullStationAndCoveredPersonsAndMedicalRecordWithAgeWhenFirestationNotFound() {
        //arrange
        List<Person> personsWithAddress1 = List.of(person1, person2);
        when(firestationRepositoryMock.findByAddress(anyString())).thenReturn(Optional.empty());
        when(personRepositoryMock.findByAddress(ADDRESS)).thenReturn(personsWithAddress1);
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName("Harry","Covert")).thenReturn(Optional.of(medicalRecord1));
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName("Justine","Illusion")).thenReturn(Optional.of(medicalRecord2));

        //act
        StationAndCoveredPersonsAndMedicalRecordWithAge result = transverseServiceImpl.getPersonsForFirebyAddress(ADDRESS);
        StationAndCoveredPersonsAndMedicalRecordWithAge expected = stationAndCoveredPersonsAndMedicalRecordWithAge3;

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(firestationRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verifyNoMoreInteractions(firestationRepositoryMock);
        verify(personRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verifyNoMoreInteractions(personRepositoryMock);
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Harry","Covert");
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Justine","Illusion");
        verifyNoMoreInteractions(medicalRecordRepositoryMock);
    }

    @Test
    void getPersonsForFirebyAddress_shouldBeOKAndReturnStationAndEmptyPersonsWhenPersonNotFound() {
        //arrange
        when(firestationRepositoryMock.findByAddress(anyString())).thenReturn(Optional.of(firestation1));
        when(personRepositoryMock.findByAddress(ADDRESS)).thenReturn(Collections.emptyList());

        //act
        StationAndCoveredPersonsAndMedicalRecordWithAge result = transverseServiceImpl.getPersonsForFirebyAddress(ADDRESS);
        StationAndCoveredPersonsAndMedicalRecordWithAge expected = stationAndCoveredPersonsAndMedicalRecordWithAge4;

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(firestationRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verifyNoMoreInteractions(firestationRepositoryMock);
        verify(personRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verifyNoMoreInteractions(personRepositoryMock);
        verifyNoInteractions(medicalRecordRepositoryMock);
    }

    @Test
    //refactor ByAddress
    void getPersonsForFirebyAddress_shoudBeOKAndReturnStationAndCoveredPersonsEvenIfMedicalRecordsNotFound() {
        //arrange
        List<Person> personsWithAddress1 = List.of(person1, person2);
        when(firestationRepositoryMock.findByAddress(anyString())).thenReturn(Optional.of(firestation1));
        when(personRepositoryMock.findByAddress(ADDRESS)).thenReturn(personsWithAddress1);
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(Optional.empty());

        //act
        StationAndCoveredPersonsAndMedicalRecordWithAge result = transverseServiceImpl.getPersonsForFirebyAddress(ADDRESS);
        StationAndCoveredPersonsAndMedicalRecordWithAge expected = stationAndCoveredPersonsAndMedicalRecordWithAge2;

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(firestationRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verifyNoMoreInteractions(firestationRepositoryMock);
        verify(personRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verifyNoMoreInteractions(personRepositoryMock);
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Harry","Covert");
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Justine","Illusion");
        verifyNoMoreInteractions(medicalRecordRepositoryMock);
    }

    @Test
    void getPersonsForFloodByStations_shoudBeOKAndReturnAMapStationAddressAndCorrespondingPersonsAndMedicalRecordWithAge() {
        //arrange
        List<Firestation> firestations1 = List.of(firestation1, firestation3);
        List<Firestation> firestations2 = List.of(firestation2);
        List<Person> personsWithAddress1 = List.of(person1, person2);
        List<Person> personsWithAddress2 = List.of(person3);
        List<Person> personsWithAddress3 = List.of(person4);

        when(firestationRepositoryMock.findByStation("99")).thenReturn(firestations1);
        when(firestationRepositoryMock.findByStation("88")).thenReturn(firestations2);
        when(personRepositoryMock.findByAddress("5, road to Nantes")).thenReturn(personsWithAddress1);
        when(personRepositoryMock.findByAddress("6, street of Brest")).thenReturn(personsWithAddress2);
        when(personRepositoryMock.findByAddress("5, road to Rennes")).thenReturn(personsWithAddress3);
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName("Harry","Covert")).thenReturn(Optional.of(medicalRecord1));
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName("Justine","Illusion")).thenReturn(Optional.of(medicalRecord2));
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName("Paul","Emploi")).thenReturn(Optional.of(medicalRecord3));
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName("Thomas","Tserize")).thenReturn(Optional.of(medicalRecord4));

        //act
        Map<String, List<PersonAndMedicalRecordWithAge>> result = transverseServiceImpl.getPersonsForFloodByStations(List.of("99","88"));
        Map<String, List<PersonAndMedicalRecordWithAge>> expected = new HashMap<>();
        expected.put("5, road to Nantes", List.of(personAndMedicalRecordWithAge1,personAndMedicalRecordWithAge2));
        expected.put("6, street of Brest", List.of(personAndMedicalRecordWithAge3));
        expected.put("5, road to Rennes", List.of(personAndMedicalRecordWithAge4));

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(firestationRepositoryMock,times(1)).findByStation("99");
        verify(firestationRepositoryMock,times(1)).findByStation("88");
        verifyNoMoreInteractions(firestationRepositoryMock);
        verify(personRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verify(personRepositoryMock,times(1)).findByAddress("6, street of Brest");
        verify(personRepositoryMock,times(1)).findByAddress("5, road to Rennes");
        verifyNoMoreInteractions(personRepositoryMock);
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Harry","Covert");
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Justine","Illusion");
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Paul","Emploi");
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Thomas","Tserize");
        verifyNoMoreInteractions(medicalRecordRepositoryMock);
    }

    @Test
    void getPersonsForFloodByStations_ShoudReturnAnEmptyMapWhenFirestationNotFound() {
        //arrange
        when(firestationRepositoryMock.findByStation(anyString())).thenReturn(Collections.emptyList());

        //act
        Map<String, List<PersonAndMedicalRecordWithAge>> result = transverseServiceImpl.getPersonsForFloodByStations(List.of(STATION_NUMBER));
//        Map<String, List<PersonAndMedicalRecordWithAge>> expected = new HashMap<>();
        Map<String, List<PersonAndMedicalRecordWithAge>> expected = Collections.emptyMap();

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(firestationRepositoryMock,times(1)).findByStation("99");
        verifyNoMoreInteractions(firestationRepositoryMock);
        verifyNoInteractions(personRepositoryMock);
        verifyNoInteractions(medicalRecordRepositoryMock);

    }

    @Test
    void getPersonsForFloodByStations_shoudReturnAMapStationAddressAndEmptyPersons() {
        //arrange
        when(firestationRepositoryMock.findByStation("99")).thenReturn(List.of(firestation1));
        when(personRepositoryMock.findByAddress(ADDRESS)).thenReturn(Collections.emptyList());

        //act
        Map<String, List<PersonAndMedicalRecordWithAge>> result = transverseServiceImpl.getPersonsForFloodByStations(List.of(STATION_NUMBER));
        Map<String, List<PersonAndMedicalRecordWithAge>> expected = Map.of("5, road to Nantes", Collections.emptyList());

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(firestationRepositoryMock,times(1)).findByStation("99");
        verifyNoMoreInteractions(firestationRepositoryMock);
        verify(personRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verifyNoMoreInteractions(personRepositoryMock);
        verifyNoInteractions(medicalRecordRepositoryMock);
    }

    @Test
    void getPersonsForFloodByStations_ShouldReturnAMapStationAddressAndPersonsWithoutMedicalRecordWhenMedicalRecordNotFound() {
        //arrange
        when(firestationRepositoryMock.findByStation(STATION_NUMBER)).thenReturn(List.of(firestation1));
        when(personRepositoryMock.findByAddress(ADDRESS)).thenReturn(List.of(person1, person2));
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(Optional.empty());

        //act
        Map<String, List<PersonAndMedicalRecordWithAge>> result = transverseServiceImpl.getPersonsForFloodByStations(List.of(STATION_NUMBER));
        Map<String, List<PersonAndMedicalRecordWithAge>> expected = Map.of("5, road to Nantes", List.of(personAndMedicalRecordWithAge1b,personAndMedicalRecordWithAge2b));

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(firestationRepositoryMock,times(1)).findByStation("99");
        verifyNoMoreInteractions(firestationRepositoryMock);
        verify(personRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verifyNoMoreInteractions(personRepositoryMock);
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Harry","Covert");
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Justine","Illusion");
        verifyNoMoreInteractions(medicalRecordRepositoryMock);
    }

    @Test
    void getPersonInfobyName_ShouldBeOKAndReturnPersonsAndMedicalRecordWithAge() {
        //arrange
        when(personRepositoryMock.findAllByFirstNameAndLastName(anyString(),anyString())).thenReturn(List.of(person1, person1));
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(Optional.of(medicalRecord1));

        //act
        List<PersonAndMedicalRecordWithAge> result = transverseServiceImpl.getPersonInfobyName("Harry", "Covert");
        List<PersonAndMedicalRecordWithAge> expected = List.of(personAndMedicalRecordWithAge1,personAndMedicalRecordWithAge1);

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(personRepositoryMock,times(1)).findAllByFirstNameAndLastName("Harry", "Covert");
        verifyNoMoreInteractions(personRepositoryMock);
        verify(medicalRecordRepositoryMock,times(2)).findByFirstNameAndLastName("Harry","Covert");
        verifyNoMoreInteractions(medicalRecordRepositoryMock);
    }

    @Test
    void getPersonInfobyName_ShouldReturnEmptyPersonsWhenPersonNotFound() {
        //arrange
        when(personRepositoryMock.findAllByFirstNameAndLastName(anyString(),anyString())).thenReturn(Collections.emptyList());

        //act
        List<PersonAndMedicalRecordWithAge> result = transverseServiceImpl.getPersonInfobyName("Harry", "Covert");
        List<PersonAndMedicalRecordWithAge> expected = Collections.emptyList();

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(personRepositoryMock,times(1)).findAllByFirstNameAndLastName("Harry", "Covert");
        verifyNoMoreInteractions(personRepositoryMock);
        verifyNoInteractions(medicalRecordRepositoryMock);
    }

    @Test
    void getPersonInfobyName_ShouldReturnPersonsWithoutMedicalRecordWithAgeWhenMedicalRecordNotFound() {
        //arrange
        when(personRepositoryMock.findAllByFirstNameAndLastName(anyString(),anyString())).thenReturn(List.of(person1));
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(Optional.empty());

        //act
        List<PersonAndMedicalRecordWithAge> result = transverseServiceImpl.getPersonInfobyName("Harry", "Covert");
        List<PersonAndMedicalRecordWithAge> expected = List.of(personAndMedicalRecordWithAge1b);

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(personRepositoryMock,times(1)).findAllByFirstNameAndLastName("Harry", "Covert");
        verifyNoMoreInteractions(personRepositoryMock);
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Harry","Covert");
        verifyNoMoreInteractions(medicalRecordRepositoryMock);
    }

}