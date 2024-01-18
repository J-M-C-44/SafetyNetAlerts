package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.JsonDataBaseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FirestationRepositoryImplTest {

    /** Service à mocker */
    @Mock
    JsonDataBaseService jsonDataBaseServiceMock;
    /** Class à tester (avec injection des mocks)*/
    @InjectMocks
    FirestationRepositoryImpl firestationRepositoryImpl;

    private final Firestation firestation1 = new Firestation("5, road to Nantes",  "99");
    private final Firestation firestation2 = new Firestation("7, road to Rennes",  "88");
    private final Firestation firestation3 = new Firestation("6, street of Brest",  "99");
    private final List<Firestation> firestations = List.of(firestation1, firestation2,firestation3);
    private static final String ADDRESS = "5, road to Nantes";
    private static final String STATION_NUMBER = "99";

    @Test
    void findByAddressAndStation_ShouldBeOKAndReturnFirestation() {
        //arrange
        when(jsonDataBaseServiceMock.getFirestations()).thenReturn(firestations);

        //act
        Optional<Firestation> result = firestationRepositoryImpl.findByAddressAndStation(ADDRESS, STATION_NUMBER);
        Optional<Firestation> expected = Optional.of(firestation1);

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(jsonDataBaseServiceMock,times(1)).getFirestations();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
    }

    @Test
    void findByAddressAndStation_ShouldReturnEmptyWhenFirestationNotFound() {
        //arrange
        when(jsonDataBaseServiceMock.getFirestations()).thenReturn(firestations);

        //act
        Optional<Firestation> result = firestationRepositoryImpl.findByAddressAndStation("unknow address", "77");
        Optional<Firestation> expected = Optional.empty();

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(jsonDataBaseServiceMock,times(1)).getFirestations();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
    }

    @Test
    void findByAddress_ShouldBeOKAndReturnFirestation() {
        //arrange
        when(jsonDataBaseServiceMock.getFirestations()).thenReturn(firestations);

        //act
        Optional<Firestation> result = firestationRepositoryImpl.findByAddress(ADDRESS);
        Optional<Firestation> expected = Optional.of(firestation1);

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(jsonDataBaseServiceMock,times(1)).getFirestations();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
    }

    @Test
    void findByAddress_ShouldReturnEmptyWhenFirestationNotFound() {
        //arrange
        when(jsonDataBaseServiceMock.getFirestations()).thenReturn(firestations);

        //act
        Optional<Firestation> result = firestationRepositoryImpl.findByAddress("unknow address");
        Optional<Firestation> expected = Optional.empty();

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(jsonDataBaseServiceMock,times(1)).getFirestations();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
    }

    @Test
    void findByStation_ShouldBeOKAndReturnFirestation() {
        //arrange
        when(jsonDataBaseServiceMock.getFirestations()).thenReturn(firestations);

        //act
        List<Firestation> result = firestationRepositoryImpl.findByStation(STATION_NUMBER);
        List<Firestation> expected = List.of(firestation1, firestation3);

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(jsonDataBaseServiceMock,times(1)).getFirestations();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
    }

    @Test
    void findByStation_ShouldReturnEmptyWhenFirestationNotFound() {
        //arrange
        when(jsonDataBaseServiceMock.getFirestations()).thenReturn(firestations);

        //act
        List<Firestation> result = firestationRepositoryImpl.findByStation("77");
        List<Firestation> expected = Collections.emptyList();

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(jsonDataBaseServiceMock,times(1)).getFirestations();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
    }

    @Test
    void delete_ShouldBeOK() {
        //arrange
        List<Firestation> firestationsMock = mock(List.class);
        when(jsonDataBaseServiceMock.getFirestations()).thenReturn(firestationsMock);

        //act
        firestationRepositoryImpl.delete(firestation1);

        //assert
        verify(jsonDataBaseServiceMock,times(1)).getFirestations();
        verify(firestationsMock,times(1)).remove(firestation1);
        verify(jsonDataBaseServiceMock,times(1)).saveDataBaseInFile();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
        verifyNoMoreInteractions(firestationsMock);
    }

    @Test
    void add_ShouldBeOK() {
        //arrange
        List<Firestation> firestationsMock = mock(List.class);
        when(jsonDataBaseServiceMock.getFirestations()).thenReturn(firestationsMock);

        //act
        Firestation result = firestationRepositoryImpl.add(firestation1);
        Firestation expected = firestation1;

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(jsonDataBaseServiceMock,times(1)).getFirestations();
        verify(firestationsMock,times(1)).add(firestation1);
        verify(jsonDataBaseServiceMock,times(1)).saveDataBaseInFile();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
        verifyNoMoreInteractions(firestationsMock);
    }

    @Test
    void update_ShouldBeOK() {
        //arrange
        List<Firestation> firestationsMock = mock(List.class);
        when(jsonDataBaseServiceMock.getFirestations()).thenReturn(firestationsMock);

        //act
        Firestation result = firestationRepositoryImpl.update(firestation1, firestation2);
        Firestation expected = firestation2;

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(jsonDataBaseServiceMock,times(1)).getFirestations();
        verify(firestationsMock,times(1)).indexOf(firestation1);
        verify(firestationsMock,times(1)).set(0,firestation2);
        verify(jsonDataBaseServiceMock,times(1)).saveDataBaseInFile();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
        verifyNoMoreInteractions(firestationsMock);
    }

}
