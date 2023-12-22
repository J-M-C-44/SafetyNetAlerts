package com.safetynet.alerts.service;

import com.safetynet.alerts.exception.AlreadyExistsException;
import com.safetynet.alerts.exception.NotFoundException;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.IFirestationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    /** Service à mocker */
    @Mock
    IFirestationRepository firestationRepositoryMock;
    /** Class à tester (avec injection des mocks)*/
    @InjectMocks
    FirestationServiceImpl firestationServiceImpl;
    // une autre façon de faire
    // FirestationRepositoryImpl firestationRepositoryMock = mock(FirestationRepositoryImpl.class);
    // FirestationServiceImpl firestationServiceImpl = new FirestationServiceImpl(firestationRepositoryMock);

    @Test
    void addFirestation_shouldBeOKandReturnFirestation() {
        //arrange
        Firestation firestationToAdd    = firestation1;
        when(firestationRepositoryMock.findByAddressAndStation(anyString(),anyString())).thenReturn(Optional.empty());
        when(firestationRepositoryMock.add(any(Firestation.class))).thenReturn(firestationToAdd);

        //act
        Firestation result = firestationServiceImpl.addFirestation(firestationToAdd);
        Firestation firestationExpected = firestation1;

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(firestationExpected);
        verify(firestationRepositoryMock,times(1)).findByAddressAndStation("5, road to Nantes", "99");
        verify(firestationRepositoryMock,times(1)).add(firestationToAdd);
    }

    @Test
    void addFirestation_shouldReturnAlreadyExistsException() {
        //arrange
        Firestation firestationToAdd    = firestation1;
        when(firestationRepositoryMock.findByAddressAndStation(anyString(),anyString())).thenReturn(Optional.of(firestationToAdd));

        //act
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class,
                () -> firestationServiceImpl.addFirestation(firestationToAdd));

        //assert
        assertThat(exception.getMessage()).isEqualTo("firestation with these address and station already exist !");
        verify(firestationRepositoryMock,times(1)).findByAddressAndStation("5, road to Nantes", "99");
        verifyNoMoreInteractions(firestationRepositoryMock);
    }

    @Test
    void updateFirestation_shouldBeOKandReturnFirestation() {
        //arrange
        Firestation currentFirestation  = firestation1;
        Firestation firestationToUpdate = firestation2;
        when(firestationRepositoryMock.findByAddress(anyString())).thenReturn(Optional.of(currentFirestation));
        when(firestationRepositoryMock.update(any(Firestation.class), any(Firestation.class))).thenReturn(firestationToUpdate);

        //act
        Firestation result = firestationServiceImpl.updateFirestation(firestationToUpdate);
        Firestation firestationExpected = firestation2;

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(firestationExpected);
        verify(firestationRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verify(firestationRepositoryMock,times(1)).update(currentFirestation,firestationToUpdate);
    }


    @Test
    void updateFirestation_shouldReturnNotFoundException() {
        //arrange
        Firestation firestationToUpdate = firestation2;
        when(firestationRepositoryMock.findByAddress(anyString())).thenReturn(Optional.empty());

        //act
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> firestationServiceImpl.updateFirestation(firestationToUpdate));

        //assert
        assertThat(exception.getMessage()).isEqualTo("firestation not found");
        verify(firestationRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verifyNoMoreInteractions(firestationRepositoryMock);
    }

    @Test
    void deleteFirestationByAddress_shouldBeOK() {
        //arrange
        Firestation firestationToDelete = firestation1;
        when(firestationRepositoryMock.findByAddress(anyString())).thenReturn(Optional.of(firestationToDelete));

        //act
        firestationServiceImpl.deleteFirestationByAddress(ADDRESS);
        Firestation firestationExpected  = firestation1;

        //assert
        verify(firestationRepositoryMock,times(1)).delete(firestationExpected);
        verify(firestationRepositoryMock,times(1)).findByAddress("5, road to Nantes");
    }

    @Test
    void deleteFirestationByAddress_shouldReturnNotFoundException() {
        //arrange
        when(firestationRepositoryMock.findByAddress(anyString())).thenReturn(Optional.empty());

        //act
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> firestationServiceImpl.deleteFirestationByAddress(ADDRESS));

        //assert
        assertThat(exception.getMessage()).isEqualTo("firestation not found");
        verify(firestationRepositoryMock,times(1)).findByAddress("5, road to Nantes");
        verifyNoMoreInteractions(firestationRepositoryMock);
    }

    @Test
    void deleteFirestationByStation_shouldBeOK() {
        //arrange
        List<Firestation> firestations  = new ArrayList<>();
        firestations.add(firestation1);
        firestations.add(firestation3);
        when(firestationRepositoryMock.findByStation(anyString())).thenReturn(firestations);

        //act
        firestationServiceImpl.deleteFirestationByStation(STATION_NUMBER);

        //assert
        firestations.forEach(verify(firestationRepositoryMock,times(1))::delete);
        verify(firestationRepositoryMock,times(1)).findByStation("99");
    }

    @Test
    void deleteFirestationByStation_shouldReturnNotFoundException() {
        //arrange
        when(firestationRepositoryMock.findByStation(anyString())).thenReturn(Collections.emptyList());

        //act
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> firestationServiceImpl.deleteFirestationByStation(STATION_NUMBER));

        //assert
        assertThat(exception.getMessage()).isEqualTo("firestation not found");
        verify(firestationRepositoryMock,times(1)).findByStation("99");
        verifyNoMoreInteractions(firestationRepositoryMock);
    }


}