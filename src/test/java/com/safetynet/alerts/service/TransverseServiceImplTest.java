package com.safetynet.alerts.service;

import com.safetynet.alerts.controller.request.tranverse.PersonsCoveredByStation;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
    private final MedicalRecord medicalRecord1 = new MedicalRecord("Harry","Covert","12/31/2000", List.of("suppo:1u", "paracetamol:500mg"), List.of("bouleau"));
    private final MedicalRecord medicalRecord2 = new MedicalRecord("Justine","Illusion","11/30/1989", List.of("suppo:2u", "paracetamol:1000mg"), List.of(""));
    private final MedicalRecord medicalRecord3 = new MedicalRecord("Paul","Emploi","10/30/2015", List.of("suppo:3u"), List.of("aspirine","arachide"));


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
    void getPersonsCoveredByStation_shoudReturnEmptyListAnd0CountsWhenFirestationNotFound() {
        //arrange
        List<Firestation> firestations = new ArrayList<>();
        when(firestationRepositoryMock.findByStation(anyString())).thenReturn(firestations);

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
        List<Person> persons = new ArrayList<>();
        when(firestationRepositoryMock.findByStation(anyString())).thenReturn(firestations);
        when(personRepositoryMock.findByAddress(anyString())).thenReturn(persons);

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
    void getPersonsCoveredByStation_shoudBeOKAndReturnPersonsCoveredByStationEvenIfMedicalRecordsNotFound() {
        //arrange
        List<Firestation> firestations = List.of(firestation1, firestation3);
        List<Person> personsWithAddress1 = List.of(person1, person2);
        List<Person> personsWithAddress2 = List.of(person3);
        when(firestationRepositoryMock.findByStation(anyString())).thenReturn(firestations);
        when(personRepositoryMock.findByAddress("5, road to Nantes")).thenReturn(personsWithAddress1);
        when(personRepositoryMock.findByAddress("6, street of Brest")).thenReturn(personsWithAddress2);

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
    // ICIJCO : continuer les TU
    void getChildrenAndHomeMembersByAddress() {
    }

    @Test
    void getPersonsByStation() {
    }

    @Test
    void getPersonsForFirebyAddress() {
    }

    @Test
    void getPersonsForFloodByStations() {
    }

    @Test
    void getPersonInfobyName() {
    }
}