package com.safetynet.alerts.service;

import com.safetynet.alerts.exception.AlreadyExistsException;
import com.safetynet.alerts.exception.NotFoundException;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.FirestationRepositoryImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FirestationServiceImplTest {

    private final Firestation firestation1 = new Firestation("5, road to Nantes",  "99");
    private final Firestation firestation2 = new Firestation("5, road to Nantes",  "88");
    private final Firestation firestation3 = new Firestation("6, street of Brest",  "99");
    private static final String ADDRESS = "5, road to Nantes";
    private static final String STATION_NUMBER = "99";

    /** Service to Mock */
//    @Mock
//    IFirestationRepository firestationRepositoryMock;
//    FirestationRepositoryImpl firestationRepositoryMock;
    FirestationRepositoryImpl firestationRepositoryMock = mock(FirestationRepositoryImpl.class);

    /** Class to test */
    FirestationServiceImpl firestationServiceImpl = new FirestationServiceImpl(firestationRepositoryMock);


    @Test
    void addFirestation_shouldBeOKandReturnFirestation() {
        //arrange
        Firestation firestationToAdd    = firestation1;
        //TODO: voir avec mentor pourquoi ce lint ?
        Firestation firestationExpected = firestation1;
        when(firestationRepositoryMock.add(firestationToAdd)).thenReturn(firestationToAdd);

        //act
        Firestation result = firestationServiceImpl.addFirestation(firestationToAdd);

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(firestationExpected);
    }

    @Test
    void addFirestation_shouldReturnAlreadyExistsException() {
        //arrange
        Firestation firestationToAdd    = firestation1;
        Firestation firestationExpected = firestation1;
        when(firestationRepositoryMock.findByAddressAndStation(firestationToAdd.getAddress(), firestationToAdd.getStation())).thenReturn(Optional.of(firestationExpected));
        when(firestationRepositoryMock.add(firestationToAdd)).thenReturn(firestationToAdd);

        //act
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class,
                () -> firestationServiceImpl.addFirestation(firestationToAdd));

        //assert
        assertThat(exception.getMessage()).isEqualTo("firestation with these address and station already exist !");
    }

    @Test
    void updateFirestation_shouldBeOKandReturnFirestation() {
        //arrange
        Firestation currentFirestation  = firestation1;
        Firestation firestationToUpdate = firestation2;
        Firestation firestationExpected = firestation2;
        when(firestationRepositoryMock.findByAddress(firestationToUpdate.getAddress())).thenReturn(Optional.of(currentFirestation));
        when(firestationRepositoryMock.update(currentFirestation, firestationToUpdate)).thenReturn(firestationToUpdate);

        //act
        Firestation result = firestationServiceImpl.updateFirestation(firestationToUpdate);

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(firestationExpected);
    }


    @Test
    void updateFirestation_shouldReturnNotFoundException() {
        //arrange
        Firestation firestationToUpdate = firestation2;
        when(firestationRepositoryMock.findByAddress(firestationToUpdate.getAddress())).thenReturn(Optional.empty());

        //act
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> firestationServiceImpl.updateFirestation(firestationToUpdate));

        //assert
        assertThat(exception.getMessage()).isEqualTo("firestation not found");
    }

    @Test
    void deleteFirestationByAddress_shouldBeOK() {
        //arrange
        Firestation firestationExpected  = firestation1;
        when(firestationRepositoryMock.findByAddress(ADDRESS)).thenReturn(Optional.of(firestationExpected));

        //act
        firestationServiceImpl.deleteFirestationByAddress(ADDRESS);

        //assert
        verify(firestationRepositoryMock,times(1)).delete(firestationExpected);
    }

    @Test
    void deleteFirestationByAddress_shouldReturnNotFoundException() {
        //arrange
        when(firestationRepositoryMock.findByAddress(ADDRESS)).thenReturn(Optional.empty());

        //act
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> firestationServiceImpl.deleteFirestationByStation(ADDRESS));

        //assert
        assertThat(exception.getMessage()).isEqualTo("firestation not found");
    }

    @Test
    void deleteFirestationByStation_shouldBeOK() {
        //arrange
        List<Firestation> firestationsExpected  = new ArrayList<>();
        firestationsExpected.add(firestation1);
        firestationsExpected.add(firestation3);
        when(firestationRepositoryMock.findByStation(STATION_NUMBER)).thenReturn(firestationsExpected);

        //act
        firestationServiceImpl.deleteFirestationByStation(STATION_NUMBER);

        firestationsExpected.forEach(verify(firestationRepositoryMock,times(1))::delete);
    }

    @Test
    void deleteFirestationByStation_shouldReturnNotFoundException() {
        //arrange
        when(firestationRepositoryMock.findByStation(STATION_NUMBER)).thenReturn(Collections.emptyList());

        //act
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> firestationServiceImpl.deleteFirestationByStation(STATION_NUMBER));

        //assert
        assertThat(exception.getMessage()).isEqualTo("firestation not found");
    }


}