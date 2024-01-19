package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.JsonDataBaseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class MedicalRecordRepositoryImplTest {
    @Mock
    JsonDataBaseService jsonDataBaseServiceMock;
    @Mock
    List<MedicalRecord> medicalRecordsMock;
    /** Class à tester (avec injection des mocks)*/
    @InjectMocks
    MedicalRecordRepositoryImpl medicalRecordRepositoryImpl;

    private final MedicalRecord medicalRecord1 = new MedicalRecord("Harry","Covert","12/31/2000", List.of("suppo:1u", "paracetamol:500mg"), List.of("bouleau"));
    private final MedicalRecord medicalRecord2 = new MedicalRecord("Justine","ILLUSION","11/30/2001", List.of("suppo:2u", "paracetamol:1000mg"), List.of(""));
    private final List<MedicalRecord> medicalRecords = List.of(medicalRecord1, medicalRecord2);
    private static final String FIRST_NAME = "Harry";
    private static final String LAST_NAME = "Covert";

    @Test
    void findByFirstNameAndLastName_ShouldBeOKAndReturnMedicalRecord() {
        //arrange
        when(jsonDataBaseServiceMock.getMedicalRecords()).thenReturn(medicalRecords);

        //act
        Optional<MedicalRecord> result = medicalRecordRepositoryImpl.findByFirstNameAndLastName(FIRST_NAME, LAST_NAME);
        Optional<MedicalRecord> expected = Optional.of(medicalRecord1);

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(jsonDataBaseServiceMock,times(1)).getMedicalRecords();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
    }

    @Test
    void findByFirstNameAndLastName_ShouldReturnEmptyWhenMedicalRecordNotFound() {
        //arrange
        when(jsonDataBaseServiceMock.getMedicalRecords()).thenReturn(medicalRecords);

        //act
        Optional<MedicalRecord> result = medicalRecordRepositoryImpl.findByFirstNameAndLastName("unknown", "unknown");
        Optional<MedicalRecord> expected = Optional.empty();

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(jsonDataBaseServiceMock,times(1)).getMedicalRecords();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
    }

    @Test
    void delete_ShouldBeOK() {
        //arrange
        when(jsonDataBaseServiceMock.getMedicalRecords()).thenReturn(medicalRecordsMock);

        //act
        medicalRecordRepositoryImpl.delete(medicalRecord1);

        //assert
        verify(jsonDataBaseServiceMock,times(1)).getMedicalRecords();
        verify(medicalRecordsMock,times(1)).remove(medicalRecord1);
        verify(jsonDataBaseServiceMock,times(1)).saveDataBaseInFile();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
        verifyNoMoreInteractions(medicalRecordsMock);
    }

    @Test
    void add_ShouldBeOK() {
        //arrange
        when(jsonDataBaseServiceMock.getMedicalRecords()).thenReturn(medicalRecordsMock);

        //act
        MedicalRecord result = medicalRecordRepositoryImpl.add(medicalRecord1);
        MedicalRecord expected = medicalRecord1;

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(jsonDataBaseServiceMock,times(1)).getMedicalRecords();
        verify(medicalRecordsMock,times(1)).add(medicalRecord1);
        verify(jsonDataBaseServiceMock,times(1)).saveDataBaseInFile();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
        verifyNoMoreInteractions(medicalRecordsMock);
    }

    @Test
    void update_ShouldBeOK() {
        //arrange
        when(jsonDataBaseServiceMock.getMedicalRecords()).thenReturn(medicalRecordsMock);

        //act
        MedicalRecord result = medicalRecordRepositoryImpl.update(medicalRecord1, medicalRecord2);
        MedicalRecord expected = medicalRecord2;

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(jsonDataBaseServiceMock,times(1)).getMedicalRecords();
        verify(medicalRecordsMock,times(1)).indexOf(medicalRecord1);
        verify(medicalRecordsMock,times(1)).set(0,medicalRecord2);
        verify(jsonDataBaseServiceMock,times(1)).saveDataBaseInFile();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
        verifyNoMoreInteractions(medicalRecordsMock);
    }

}

